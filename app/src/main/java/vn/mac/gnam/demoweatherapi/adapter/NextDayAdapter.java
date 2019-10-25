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
import vn.mac.gnam.demoweatherapi.model.NextDay.NextDay;

public class NextDayAdapter extends RecyclerView.Adapter<NextDayHolder> {
    private Context context;
    private List<NextDay> nextDays;

    public NextDayAdapter(Context context, List<NextDay> nextDays) {
        this.context = context;
        this.nextDays = nextDays;
    }

    @NonNull
    @Override
    public NextDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_next_day, parent, false);
        return new NextDayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NextDayHolder holder, int position) {
        NextDay nextDay = nextDays.get(position);
        //Time
        long l = Long.valueOf(nextDay.getDailyForecasts().get(0).getEpochDate());
        Date date = new Date(l * 1000);
        //EEEE - yyyy-MM-dd HH-mm-ss
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String ngay = simpleDateFormat.format(date);
        holder.tvTime.setText(ngay);

        // Nhietdo
        int doCMin = (int) ((nextDay.getDailyForecasts().get(0).getTemperature().getMinimum().getValue() - 32) / 1.80000);
        int doCMax = (int) ((nextDay.getDailyForecasts().get(0).getTemperature().getMaximum().getValue() - 32) / 1.80000);
        holder.tvNhietDo.setText(doCMin + "°C" + " - " + doCMax + "°C");

        //icon
        Glide.with(context)
                .load("https://www.accuweather.com/images/weathericons/" + nextDay.getDailyForecasts().get(0).getDay().getIcon() + ".svg")
                .into(holder.imgIcon);

    }

    @Override
    public int getItemCount() {
        return nextDays.size();
    }
}
