package com.geekbrains.backend.test.miniMarket;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniMarketService {

    private final MiniMarketApi api;


    public MiniMarketService() {
       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://minimarket1.herokuapp.com/market/api/v1/")
                .addCallAdapterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MiniMarketApi.class);
    }
}
