package vn.mac.gnam.demoweatherapi.retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    public  static WeatherApi weatherApi;

    public  static WeatherApi getInstance(){
        if (weatherApi == null){
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("http://dataservice.accuweather.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;

    }
}
