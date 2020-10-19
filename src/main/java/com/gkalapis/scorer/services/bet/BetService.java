package com.gkalapis.scorer.services.bet;

import java.util.List;
import java.util.Map;

import com.gkalapis.scorer.domain.entities.Bet;

public interface BetService {

    List<Bet> listBets(String userId);

    Map<String, Boolean> persistBets(String userId, List<Long> matchIdList, List<Integer> homeTeamGoalsList, List<Integer> awayTeamGoalsList);
}