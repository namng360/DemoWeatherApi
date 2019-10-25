package vn.mac.gnam.demoweatherapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import vn.mac.gnam.demoweatherapi.R;
import vn.mac.gnam.demoweatherapi.model.PerHour.PerHour;

public class ThoiTietAdapter extends RecyclerView.Adapter<ThoiTietHolder> {
    private Context context;
    private List<PerHour> perHours;

    public ThoiTietAdapter(Context context, List<PerHour> perHours) {
        this.context = context;
        this.perHours = perHours;
    }

    @NonNull
    @Override
    public ThoiTietHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thoi_tiet, parent, false);
        return new ThoiTietHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThoiTietHolder holder, int position) {
        PerHour perHour = perHours.get(position);

        //Time
        long l = Long.valueOf(perHour.getEpochDateTime());
        Date date = new Date(l * 1000);
        //EEEE - yyyy-MM-dd HH-mm-ss
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String ngay = simpleDateFormat.format(date);
        holder.tvTime.setText(ngay);

        // Nhietdo
        int doC = (int) ((perHour.getTemperature().getValue() - 32) / 1.80000);
        holder.tvNhietDo.setText(doC + "°C");

        //icon
        Glide.with(context)
                .load("https://www.accuweather.com/images/weathericons/" + perHour.getWeatherIcon() + ".svg")
                .into(holder.imgIcon);

    }

    @Override
    public int getItemCount() {
        return perHours.size();
    }
}
