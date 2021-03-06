package com.gkalapis.scorer.services.match.pointcalculator;

import com.gkalapis.scorer.domain.entities.Bet;
import com.gkalapis.scorer.domain.entities.Match;
import com.gkalapis.scorer.domain.entities.User;
import com.gkalapis.scorer.repositories.BetRepository;
import com.gkalapis.scorer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointCalculatorAndPersisterServiceImpl implements PointCalculatorAndPersisterService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void calculateAndSavePoints(Match match) {
        List<Bet> betList = betRepository.findByMatch(match);

        for (Bet bet : betList) {
            int gainedPoints = calculateGainedPoints(match, bet);
            bet.setGainedPoints(gainedPoints);
            betRepository.save(bet);

            updateUserPointsIfNecessary(bet, gainedPoints);
        }
    }

    private void updateUserPointsIfNecessary(Bet bet, int gainedPoints) {
        if (gainedPoints > 0) {
            User user = bet.getUser();
            user.setPoints(user.getPoints() + gainedPoints);
            userRepository.save(user);
        }
    }

    private int calculateGainedPoints(Match match, Bet bet) {
        int gainedPoints = 0;
        if (match.getHomeTeamGoals() == bet.getHomeTeamGoals() && match.getAwayTeamGoals() == bet.getAwayTeamGoals()) {
            gainedPoints = 3;
        } else if (match.getMatchResult().equals(bet.getMatchResult())){
            gainedPoints = 1;
        }
        return gainedPoints;
    }
}