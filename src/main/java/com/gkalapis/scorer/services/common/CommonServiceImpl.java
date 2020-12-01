package com.gkalapis.scorer.services.common;

import com.gkalapis.scorer.services.football.data.api.FootballDataService;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class CommonServiceImpl implements CommonService{

    private FootballDataService footballDataService = createFootballDataService();

    @Override
    public FootballDataService getFootballDataService() {
        return footballDataService;
    }

    private FootballDataService createFootballDataService() {
        retrofit2.Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.football-data.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(FootballDataService.class);
    }
}