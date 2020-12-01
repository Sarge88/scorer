package com.gkalapis.scorer.services.team;

import com.gkalapis.scorer.domain.entities.Team;
import com.gkalapis.scorer.football.data.domain.teams.ApiTeam;
import com.gkalapis.scorer.football.data.domain.teams.TeamWrapper;
import com.gkalapis.scorer.repositories.TeamRepository;
import com.gkalapis.scorer.services.common.CommonService;
import com.gkalapis.scorer.services.football.data.api.FootballDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private CommonService commonService;

    @PostConstruct
    private void persistTeamsIfNecessaryOnStartup() throws IOException {
        if (teamRepository.count() != 20) {
            FootballDataService footballDataService = commonService.getFootballDataService();
            Call<TeamWrapper> call = footballDataService.listTeams(FootballDataService.API_TOKEN);
            TeamWrapper teamList = call.execute().body();

            if (teamList != null) {
                persistTeams(teamList);
            }
        }
    }

    private void persistTeams(TeamWrapper teamList) {
        for (ApiTeam apiTeam : teamList.getTeams()) {
            Long id = apiTeam.getId();
            Team team = new Team(id, apiTeam.getName());
            //TODO: check if id exists before save
            teamRepository.save(team);
        }
    }
}