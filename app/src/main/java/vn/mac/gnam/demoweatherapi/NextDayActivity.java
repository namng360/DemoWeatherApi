package vn.mac.gnam.demoweatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import vn.mac.gnam.demoweatherapi.adapter.NextDayAdapter;
import vn.mac.gnam.demoweatherapi.adapter.ThoiTietAdapter;
import vn.mac.gnam.demoweatherapi.model.NextDay.DailyForecast;
import vn.mac.gnam.demoweatherapi.model.NextDay.NextDay;
import vn.mac.gnam.demoweatherapi.model.PerHour.PerHour;
import vn.mac.gnam.demoweatherapi.retrofit.Retrofit;

public class NextDayActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tvDuongLichTop;
    private ImageView imageView2;
    private TextView tvNhietDo;
    private TextView tvNgay;
    private ImageView imgNgay;
    private TextView tvDem;
    private ImageView imgDem;
    private TextView tvTime;
    private RecyclerView rvPerHour;
    private RecyclerView rvNextDay;
    private List<PerHour> perHours;
    private List<NextDay> nextDays;
    private NextDayAdapter nextDayAdapter;
    private ThoiTietAdapter thoiTietAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_day);
        init();
    }

    private void init() {

        imageView = (ImageView) findViewById(R.id.imageView);
        tvDuongLichTop = (TextView) findViewById(R.id.tvDuongLichTop);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        tvNhietDo = (TextView) findViewById(R.id.tvNhietDo);
        tvNgay = (TextView) findViewById(R.id.tvNgay);
        imgNgay = (ImageView) findViewById(R.id.imgNgay);
        tvDem = (TextView) findViewById(R.id.tvDem);
        imgDem = (ImageView) findViewById(R.id.imgDem);
        tvTime = (TextView) findViewById(R.id.tvTime);
        rvPerHour = (RecyclerView) findViewById(R.id.rvPerHour);
        rvNextDay = (RecyclerView) findViewById(R.id.rvNextDay);

        rvPerHour = findViewById(R.id.rvPerHour);
        perHours = new ArrayList<>();
        thoiTietAdapter = new ThoiTietAdapter(this, perHours);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPerHour.setHasFixedSize(true);
        rvPerHour.setLayoutManager(linearLayoutManager);
        rvPerHour.setAdapter(thoiTietAdapter);

        rvNextDay = findViewById(R.id.rvNextDay);
        nextDays = new ArrayList<>();
        nextDayAdapter = new NextDayAdapter(this, nextDays);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvNextDay.setHasFixedSize(true);
        rvNextDay.setLayoutManager(linearLayoutManager);
        rvNextDay.setAdapter(nextDayAdapter);
        
        GetCurrentData();
        GetReatrofitPerHourData();
        GetReatrofitNextDayData();
    }

    private void GetReatrofitNextDayData() {
        Retrofit.getInstance().getNextDayWeatherData().enqueue(new Callback<List<NextDay>>() {
            @Override
            public void onResponse(Call<List<NextDay>> call, retrofit2.Response<List<NextDay>> response) {
                if (response.code() == 200) {
                    nextDays.addAll(response.body());
                    nextDayAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(NextDayActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NextDay>> call, Throwable t) {
                Log.e("onFailureNextDay", t.getMessage());

            }
        });

    }

    private void GetReatrofitPerHourData() {
        Retrofit.getInstance().getPerHourWeatherData().enqueue(new Callback<List<PerHour>>() {
            @Override
            public void onResponse(Call<List<PerHour>> call, retrofit2.Response<List<PerHour>> response) {
                if (response.code() == 200) {
                    perHours.addAll(response.body());
                    thoiTietAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(NextDayActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PerHour>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void GetCurrentData() {
        RequestQueue requestQueue = Volley.newRequestQueue(NextDayActivity.this);
        String url = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/353412?" +
                "apikey=pOSruJBxzbxVkFigGWS4JpG9ayGE3Nzp&language=vi";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArrayDailyForecasts = jsonObject.getJSONArray("DailyForecasts");
                            JSONObject jsonObjectWeather = jsonArrayDailyForecasts.getJSONObject(0);

                            String time = jsonObjectWeather.getString("EpochDate");
                            long l = Long.valueOf(time);
                            Date date = new Date(l * 1000);
                            //EEEE - yyyy-MM-dd HH-mm-ss
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MM-yyyy");
                            String ngay = simpleDateFormat.format(date);
                            tvTime.setText(ngay);

                            Date today = new Date(System.currentTimeMillis());
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
                            String s = timeFormat.format(today.getTime());

                            Log.e("Time", s + "");

                            //Nhiet do
                            JSONObject jsonObjectTemperature = jsonObjectWeather.getJSONObject("Temperature");
                            JSONObject jsonObjectMin = jsonObjectTemperature.getJSONObject("Minimum");
                            int nhietDoMin = jsonObjectMin.getInt("Value");

                            JSONObject jsonObjectMax = jsonObjectTemperature.getJSONObject("Maximum");
                            int nhietDoMax = jsonObjectMax.getInt("Value");

                            int doCMin = (int) ((nhietDoMin - 32) / 1.80000);
                            int doCMax = (int) ((nhietDoMax - 32) / 1.80000);
                            int doC = (doCMin + doCMax)/2;

                            tvNhietDo.setText(doC + "°C");

                            //Icon
                            JSONObject jsonObjectDay = jsonObjectWeather.getJSONObject("Day");
                            String day = jsonObjectDay.getString("IconPhrase");
                            tvNgay.setText(day);
                            String iconDay = jsonObjectDay.getString("Icon");
                            Glide.with(NextDayActivity.this).load("https://www.accuweather.com/images/weathericons/" + iconDay + ".svg").into(imgNgay);

                            JSONObject jsonObjectNight = jsonObjectWeather.getJSONObject("Night");
                            String night = jsonObjectNight.getString("IconPhrase");
                            tvDem.setText(night);
                            Log.e("Dem", night);
                            String iconNight = jsonObjectNight.getString("Icon");
                            Glide.with(NextDayActivity.this).load("https://www.accuweather.com/images/weathericons/" + iconNight + ".svg").into(imgDem);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
