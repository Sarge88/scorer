package com.gkalapis.scorer.football.data.domain.matches;

import java.util.Date;

import com.gkalapis.scorer.football.data.domain.teams.ApiTeam;

public class ApiMatch {
	
	private Long id;

    private Date utcDate;

    private String status;

    private int matchday;

    private ApiTeam homeTeam;
    
    private ApiTeam awayTeam;

    private Score score;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUtcDate() {
        return utcDate;
    }

    public void setUtcDate(Date utcDate) {
        this.utcDate = utcDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }
    
    public ApiTeam getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(ApiTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public ApiTeam getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(ApiTeam awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
