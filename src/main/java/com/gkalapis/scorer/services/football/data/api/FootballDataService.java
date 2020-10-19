package com.gkalapis.scorer.services.football.data.api;

import com.gkalapis.scorer.football.data.domain.matches.MatchList;
import com.gkalapis.scorer.football.data.domain.matches.MatchWrapper;
import com.gkalapis.scorer.football.data.domain.teams.TeamWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface FootballDataService {

	public static final String API_VERSION = "v2";
	public static final String LEAGUE_ID = "2021";
	public static final String API_TOKEN = "2e27dd61b71b49cda5ad18b1c07d7b05";
	
    @GET(API_VERSION + "/competitions/" + LEAGUE_ID + "/teams")
    Call<TeamWrapper> listTeams(@Header("X-Auth-Token") String token);

    @GET(API_VERSION + "/competitions/" + LEAGUE_ID + "/matches")
    Call<MatchList> listMatches(@Header("X-Auth-Token") String token);

    @GET(API_VERSION + "/matches/{id}")
    Call<MatchWrapper> getMatchById(@Path("id") Integer id, @Header("X-Auth-Token") String token);
}