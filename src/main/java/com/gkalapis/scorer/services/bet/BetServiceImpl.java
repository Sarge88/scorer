package com.gkalapis.scorer.services.bet;

import com.gkalapis.scorer.domain.MatchResult;
import com.gkalapis.scorer.domain.entities.Bet;
import com.gkalapis.scorer.domain.entities.Match;
import com.gkalapis.scorer.domain.entities.User;
import com.gkalapis.scorer.repositories.BetRepository;
import com.gkalapis.scorer.repositories.MatchRepository;
import com.gkalapis.scorer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BetServiceImpl implements BetService {

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Override
	public List<Bet> listBets(String userName) {
		return betRepository.findByUserName(userName);
	}


	//TODO userId delete, instead name
	@Override
	public Map<String, Boolean> persistBets(String name, List<Long> matchIdList, List<Integer> homeTeamGoalsList,
			List<Integer> awayTeamGoalsList) {
		Map<String, Boolean> success = new HashMap<>();
		try {
			User user = userRepository.findByName(name);
			for (int i = 0; i < matchIdList.size(); i++) {
				saveOrUpdateBet(matchIdList, homeTeamGoalsList, awayTeamGoalsList, user, i);
			}
			success.put("success", true);
		} catch (Exception ex) {
			success.put("success", false);
		}

		return success;
	}

	private void saveOrUpdateBet(List<Long> matchIdList, List<Integer> homeTeamGoalsList, List<Integer> awayTeamGoalsList, User user, int i) throws Exception {
		Match match = matchRepository.findById(matchIdList.get(i)).get();
		Bet existingBet = betRepository.findByUserAndMatch(user, match);

		Integer homeTeamGoals = homeTeamGoalsList.get(i);
		Integer awayTeamGoals = awayTeamGoalsList.get(i);
		
		String homeTeamName = match.getHomeTeamName();
		String awayTeamName = match.getAwayTeamName();
		
		String status = match.getStatus();

		if ( !status.equals("IN_PLAY") && !status.equals("FINISHED")) {
			if (existingBet != null) {
				setExistingBetFields(existingBet, homeTeamGoals, awayTeamGoals);
				betRepository.save(existingBet);
			} else {
				Bet bet = new Bet(user, match, homeTeamGoals, awayTeamGoals);
				betRepository.save(bet);
			}
		}
		else {
			System.out.println("The match between "+homeTeamName+" and "+awayTeamName+" is either already in play or already finished" );
			throw new Exception("The match is either in play or already finished.");
		}
	}

	private void setExistingBetFields(Bet existingBet, Integer homeTeamGoals, Integer awayTeamGoals) {
		existingBet.setHomeTeamGoals(homeTeamGoals);
		existingBet.setAwayTeamGoals(awayTeamGoals);
		existingBet.setMatchResult(MatchResult.calculateMatchResult(homeTeamGoals, awayTeamGoals));
	}
}