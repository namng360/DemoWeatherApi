package vn.mac.gnam.demoweatherapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import vn.mac.gnam.demoweatherapi.adapter.ThoiTietAdapter;
import vn.mac.gnam.demoweatherapi.model.PerHour.PerHour;
import vn.mac.gnam.demoweatherapi.retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {

    private ImageView imgIcon;
    private TextView tvNhietDo;
    private TextView tvCloud;
    private TextView tvNgay;
    private ImageView imgNgay;
    private TextView tvDem;
    private ImageView imgDem;
    private RecyclerView rvThoiTiet;
    private List<PerHour> perHours;
    private ThoiTietAdapter thoiTietAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        tvNhietDo = (TextView) findViewById(R.id.tvNhietDo);
        tvCloud = (TextView) findViewById(R.id.tvCloud);
        tvNgay = (TextView) findViewById(R.id.tvNgay);
        imgNgay = (ImageView) findViewById(R.id.imgNgay);
        tvDem = (TextView) findViewById(R.id.tvDem);
        imgDem = (ImageView) findViewById(R.id.imgDem);

        rvThoiTiet = findViewById(R.id.rvThoiTiet);
        perHours = new ArrayList<>();
        thoiTietAdapter = new ThoiTietAdapter(this, perHours);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvThoiTiet.setHasFixedSize(true);
        rvThoiTiet.setLayoutManager(linearLayoutManager);
        rvThoiTiet.setAdapter(thoiTietAdapter);

        GetReatrofitPerHourData();

        GetCurrentData();


    }

    private void GetReatrofitPerHourData() {
        Retrofit.getInstance().getPerHourWeatherData().enqueue(new Callback<List<PerHour>>() {
            @Override
            public void onResponse(Call<List<PerHour>> call, retrofit2.Response<List<PerHour>> response) {
                if (response.code() == 200) {
                    perHours.addAll(response.body());
                    thoiTietAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PerHour>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }


    public void GetCurrentData() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
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
                            tvCloud.setText(ngay);

                            Date today = new Date(System.currentTimeMillis());
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
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

                            tvNhietDo.setText(doCMin + "°C" + " - " + doCMax + "°C");

                            //Icon
                            JSONObject jsonObjectDay = jsonObjectWeather.getJSONObject("Day");
                            String day = jsonObjectDay.getString("IconPhrase");
                            tvNgay.setText(day);
                            String iconDay = jsonObjectDay.getString("Icon");
                            Glide.with(MainActivity.this).load("https://www.accuweather.com/images/weathericons/" + iconDay + ".svg").into(imgNgay);

                            JSONObject jsonObjectNight = jsonObjectWeather.getJSONObject("Night");
                            String night = jsonObjectNight.getString("IconPhrase");
                            tvDem.setText(night);
                            Log.e("Dem", night);
                            String iconNight = jsonObjectNight.getString("Icon");
                            Glide.with(MainActivity.this).load("https://www.accuweather.com/images/weathericons/" + iconNight + ".svg").into(imgDem);
                            LocalTime closed = LocalTime.of(17, 0);
                            LocalTime currentTime = LocalTime.now();
                            if (currentTime.isAfter(closed)) {
                                // Closeds
                                Glide.with(MainActivity.this).load("https://www.accuweather.com/images/weathericons/" + iconDay + ".svg").into(imgIcon);
                            }else {
                                Glide.with(MainActivity.this).load("https://www.accuweather.com/images/weathericons/" + iconNight + ".svg").into(imgIcon);
                            }
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

    public void nextDay(View view) {
        Intent intent = new Intent(MainActivity.this, NextDayActivity.class);
        startActivity(intent);
    }
}
