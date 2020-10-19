package com.gkalapis.scorer.football.data.domain.teams;

import java.util.List;

public class TeamWrapper {

    private List<ApiTeam> teams;

    public List<ApiTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<ApiTeam> teams) {
        this.teams = teams;
    }
}
