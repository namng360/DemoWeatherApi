package vn.mac.gnam.demoweatherapi.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.mac.gnam.demoweatherapi.R;

public class NextDayHolder extends RecyclerView.ViewHolder {
    public TextView tvTime;
    public ImageView imgIcon;
    public TextView tvNhietDo;

    public NextDayHolder(@NonNull View itemView) {
        super(itemView);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
        tvNhietDo = (TextView) itemView.findViewById(R.id.tvNhietDo);
    }
}
