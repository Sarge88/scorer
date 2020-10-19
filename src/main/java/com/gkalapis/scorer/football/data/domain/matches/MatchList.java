package com.gkalapis.scorer.football.data.domain.matches;

import java.util.List;

public class MatchList {

    private List<ApiMatch> matches;

    private int count;

    public List<ApiMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<ApiMatch> matches) {
        this.matches = matches;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
