package com.gkalapis.scorer.football.data.domain.matches;

public class MatchWrapper {
    private ApiMatch match;

    public MatchWrapper(ApiMatch match) {
        this.match = match;
    }

    public ApiMatch getMatch() {
        return match;
    }

    public void setMatch(ApiMatch match) {
        this.match = match;
    }
}
