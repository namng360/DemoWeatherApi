package vn.mac.gnam.demoweatherapi.model.NextDay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseClass {
    @SerializedName("students")
    @Expose
    private List<DailyForecast> dailyForecasts = null;

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }
}
