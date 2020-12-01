package com.gkalapis.scorer.services.bet;

import com.gkalapis.scorer.domain.entities.Bet;

import java.util.List;
import java.util.Map;

public interface BetService {

    List<Bet> listBets(String userName);

    Map<String, Boolean> persistBets(String name, List<Long> matchIdList, List<Integer> homeTeamGoalsList, List<Integer> awayTeamGoalsList);
}