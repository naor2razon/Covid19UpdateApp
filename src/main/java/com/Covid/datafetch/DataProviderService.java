package com.Covid.datafetch;

import com.Covid.datafetch.Model.CountryData;
import com.Covid.datafetch.Model.CovidDataModel;
import com.Covid.datafetch.Model.GlobalData;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.jvm.hotspot.tools.SysPropsDumper;

import java.util.concurrent.CompletableFuture;

public class DataProviderService {

    public CovidDataModel getData(String countryName){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://coronavirus-19-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         CovidAPI covidAPI = retrofit.create(CovidAPI.class);

         CompletableFuture<GlobalData> callBack1 = new CompletableFuture<>();
         covidAPI.getGlobalData().enqueue(new Callback<GlobalData>() {
             @Override
             public void onResponse(Call<GlobalData> call, Response<GlobalData> response) {
                 callBack1.complete(response.body());
             }

             @Override
             public void onFailure(Call<GlobalData> call, Throwable t) {
                callBack1.completeExceptionally(t);
             }
         });

         CompletableFuture<CountryData> callBack2 =new CompletableFuture<>();
         covidAPI.getCountryData(countryName).enqueue(new Callback<CountryData>() {
             @Override
             public void onResponse(Call<CountryData> call, Response<CountryData> response) {
                 callBack2.complete(response.body());
             }

             @Override
             public void onFailure(Call<CountryData> call, Throwable t) {
                callBack2.completeExceptionally(t);
             }
         });

         GlobalData globalData = callBack1.join();
         CountryData countryData = callBack2.join();
         return new CovidDataModel(globalData,countryData);
    }
}
