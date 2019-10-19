package vn.mac.gnam.demoweatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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


public class MainActivity extends AppCompatActivity {

    private TextView tvCity;
    private ImageView imgIcon;
    private TextView tvNhietDo;
//    private TextView tvTrangThai;
    private TextView tvRain;
    private TextView tvCloud;
    private TextView tvWind;
    private TextView tvNgay;
    private ImageView imgNgay;
    private TextView tvDem;
    private ImageView imgDem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

//        edtTim = (EditText) findViewById(R.id.edtTim);
//        btnTim = (Button) findViewById(R.id.btnTim);
        tvCity = (TextView) findViewById(R.id.tvCity);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        tvNhietDo = (TextView) findViewById(R.id.tvNhietDo);
//        tvTrangThai = (TextView) findViewById(R.id.tvTrangThai);
//        tvRain = (TextView) findViewById(R.id.tvRain);
        tvCloud = (TextView) findViewById(R.id.tvCloud);
//        tvWind = (TextView) findViewById(R.id.tvWind);

        tvNgay = (TextView) findViewById(R.id.tvNgay);
        imgNgay = (ImageView) findViewById(R.id.imgNgay);
        tvDem = (TextView) findViewById(R.id.tvDem);
        imgDem = (ImageView) findViewById(R.id.imgDem);

        GetCurrentData();

//        btnNextDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NextDayActivity.class);
//                startActivity(intent);
//            }
//        });

    }


    public void GetCurrentData(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/353412?" +
                "apikey=8wbdY3EPG3oPXjzxHzSk2V3kHjuuQ0lK&language=vi";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArrayDailyForecasts = jsonObject.getJSONArray("DailyForecasts");
                            JSONObject jsonObjectWeather = jsonArrayDailyForecasts.getJSONObject(0);
                            String date = jsonObjectWeather.getString("Date");
                            tvCloud.setText(date);

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

//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String day = jsonObject.getString("dt");
//                            String name = jsonObject.getString("name");
//                            String newString = name.replace("Hanoi", "Hà Nội");
//                            tvCity.setText("Tên thành phố: " + name);
//
////                            long l = Long.valueOf(day);
////                            Date date = new Date(l * 1000);
////                            //EEEE - yyyy-MM-dd HH-mm-ss
////                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd");
////                            String ngay = simpleDateFormat.format(date);
////                            tvDay.setText(ngay);
//
//                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
//                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
////                            String status = jsonObjectWeather.getString("main");
//                            String icon = jsonObjectWeather.getString("icon");
//
//                            Picasso.get().load("http://openweathermap.org/img/wn/" + icon + "@2x.png").into(imgIcon);
////                            tvTrangThai.setText(status);
//
//                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
//                            String nhieuDo = jsonObjectMain.getString("temp");
//                            String doAm = jsonObjectMain.getString("humidity");
//
//                            Double a = Double.valueOf(nhieuDo);
//                            String NhietDo = String.valueOf(a.intValue());
//
//                            tvNhietDo.setText(NhietDo + " °C");
//                            tvRain.setText(doAm + "%");
//
//                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
//                            String gio = jsonObjectWind.getString("speed");
//                            tvWind.setText(gio + "m/s");
//
//                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
//                            String may = jsonObjectCloud.getString("all");
//                            tvCloud.setText(may + "%");
//
////                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
////                            String country = jsonObjectSys.getString("country");
////                            tvQg.setText("Ten quoc gia" + country);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        requestQueue.add(stringRequest);
    }
}
