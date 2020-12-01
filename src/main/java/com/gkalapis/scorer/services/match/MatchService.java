package com.gkalapis.scorer.services.match;

import com.gkalapis.scorer.domain.MatchResult;
import com.gkalapis.scorer.domain.entities.Match;
import com.gkalapis.scorer.domain.entities.Team;
import com.gkalapis.scorer.football.data.domain.matches.ApiMatch;
import com.gkalapis.scorer.football.data.domain.matches.MatchList;
import com.gkalapis.scorer.football.data.domain.matches.MatchWrapper;
import com.gkalapis.scorer.football.data.domain.teams.ApiTeam;
import com.gkalapis.scorer.repositories.MatchRepository;
import com.gkalapis.scorer.repositories.TeamRepository;
import com.gkalapis.scorer.services.common.CommonService;
import com.gkalapis.scorer.services.football.data.api.FootballDataService;
import com.gkalapis.scorer.services.match.pointcalculator.PointCalculatorAndPersisterService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PointCalculatorAndPersisterService pointCalculatorAndPersisterService;

    private final int oneMinInMs = 60000;
    
    // csak a lokal db-t nézi és ahhoz hasonlít (lehetne több kéréssel lekérni állandóan)
    @Scheduled(fixedDelay = oneMinInMs)
    private void updateScheduledMatchStatus() throws IOException {
        List<Match> scheduledMatchList = matchRepository.findByStatus("SCHEDULED"); //db-ből szedi
        for (Match match : scheduledMatchList) {
            if (new DateTime(match.getDateTime()).isBefore(new DateTime())) {
                match.setStatus("IN_PLAY"); //db-be teszi in playre
                matchRepository.save(match);
            }
        }
    }

    @Scheduled(fixedDelay = oneMinInMs)
    private void updateInPlayMatchStatus() throws IOException {
        List<Match> matchesInPlay = matchRepository.findByStatus("IN_PLAY");
        System.out.println("In play matches count in DB: " + matchesInPlay.size());
        for (Match match : matchesInPlay) {
            if (elapsed105To130MinsFromStart(match)) {
            	ApiMatch matchFromAPI = getMatchById(match.getId().intValue());
            	System.out.println("In play match id from API: " + matchFromAPI.getId() + ", status: " + matchFromAPI.getStatus());
                updateMatchBetAndUserIfNecessary(match, matchFromAPI);
            }
        }
    }

    private boolean elapsed105To130MinsFromStart(Match match) {
        DateTime matchDate = new DateTime(match.getDateTime());        
        
        // csak bajnokságra jó, mert nincs extra time (+2 félidő)

        return matchDate.plusMinutes(110).isBeforeNow() && matchDate.plusMinutes(150).isAfterNow();
    }

    private void updateMatchBetAndUserIfNecessary(Match match, ApiMatch matchFromAPI) {
        if (matchFromAPI != null && matchFromAPI.getScore().getFullTime().getHomeTeam() != null) {
        	System.out.println("updating match in DB, id: " + match.getId());
            updateMatchFields(match, matchFromAPI);
            matchRepository.save(match);
            pointCalculatorAndPersisterService.calculateAndSavePoints(match);
        }
    }

    private void updateMatchFields(Match match, ApiMatch matchFromAPI) {
        Integer homeTeamGoals = matchFromAPI.getScore().getFullTime().getHomeTeam();
        Integer awayTeamGoals = matchFromAPI.getScore().getFullTime().getAwayTeam();

        match.setStatus("FINISHED");
        match.setHomeTeamGoals(homeTeamGoals);
        match.setAwayTeamGoals(awayTeamGoals);
        match.setMatchResult(MatchResult.calculateMatchResult(homeTeamGoals, awayTeamGoals));
    }

    @Scheduled(fixedDelay = oneMinInMs*60*24)
    private void persistMatchesIfNecessary() throws IOException {
        MatchList matchList = getMatchList();

        if (matchList != null) {
            for (ApiMatch matchFromApi : matchList.getMatches()) {
                Long id = matchFromApi.getId();
                if (!matchRepository.existsById(id)) {
                	Match match = createMatchFromFixture(matchFromApi, id);
                    matchRepository.save(match);
                }
            }
        }
    }

    private Match createMatchFromFixture(ApiMatch matchFromAPI, Long id) {
        Team homeTeam = loadTeamFromDB(matchFromAPI.getHomeTeam());
        Team awayTeam = loadTeamFromDB(matchFromAPI.getAwayTeam());
        String homeTeamName = homeTeam.getName();
        String awayTeamName = awayTeam.getName();
        Integer homeTeamGoals = matchFromAPI.getScore().getFullTime().getHomeTeam();
        Integer awayTeamGoals = matchFromAPI.getScore().getFullTime().getAwayTeam();
        String status = matchFromAPI.getStatus();
        int round = matchFromAPI.getMatchday();
        Date date = matchFromAPI.getUtcDate();

        return new Match(id, homeTeam, awayTeam, homeTeamName, awayTeamName, homeTeamGoals, awayTeamGoals, status, round, date);
    }

    private Team loadTeamFromDB(ApiTeam apiTeam) {
        Long teamId = apiTeam.getId();

        return teamRepository.findById(teamId).get();
    }

    private MatchList getMatchList() throws IOException {
        FootballDataService footballDataService = commonService.getFootballDataService();
        Call<MatchList> call = footballDataService.listMatches(FootballDataService.API_TOKEN);
        return call.execute().body();
    }

    private ApiMatch getMatchById(Integer id) throws IOException {
        FootballDataService footballDataService = commonService.getFootballDataService();
        Call<MatchWrapper> call = footballDataService.getMatchById(id, FootballDataService.API_TOKEN);
        return call.execute().body().getMatch();
    }
}