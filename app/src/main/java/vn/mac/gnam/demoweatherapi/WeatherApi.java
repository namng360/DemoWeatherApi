package vn.mac.gnam.demoweatherapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.mac.gnam.demoweatherapi.model.CurDay.DailyForecast;

public interface WeatherApi {
    @GET("/forecasts/v1/daily/1day/353412?apikey=8wbdY3EPG3oPXjzxHzSk2V3kHjuuQ0lK&language=vi")
    Call<DailyForecast> getDataOfCurWeather();
}
