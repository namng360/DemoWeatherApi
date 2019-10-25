package vn.mac.gnam.demoweatherapi.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.mac.gnam.demoweatherapi.R;

public class ThoiTietHolder extends RecyclerView.ViewHolder {
    public TextView tvTime;
    public TextView tvNhietDo;
    public ImageView imgIcon;

    public ThoiTietHolder(@NonNull View itemView) {
        super(itemView);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        tvNhietDo = (TextView) itemView.findViewById(R.id.tvNhietDo);
        imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
    }
}
