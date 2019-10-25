package vn.mac.gnam.demoweatherapi.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.mac.gnam.demoweatherapi.model.NextDay.DailyForecast;
import vn.mac.gnam.demoweatherapi.model.NextDay.NextDay;
import vn.mac.gnam.demoweatherapi.model.PerHour.PerHour;

public interface WeatherApi {
    @GET("/forecasts/v1/hourly/12hour/353412?apikey=pOSruJBxzbxVkFigGWS4JpG9ayGE3Nzp&language=vi")
    Call<List<PerHour>> getPerHourWeatherData();

    @GET("/forecasts/v1/daily/5day/353412?apikey=pOSruJBxzbxVkFigGWS4JpG9ayGE3Nzp&language=vi")
    Call<List<NextDay>> getNextDayWeatherData();
}
