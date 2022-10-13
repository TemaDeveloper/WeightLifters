package com_gym.java_gym.weightlifters.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import com_gym.java_gym.weightlifters.models.Week;
import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.activities.WeekDataActivity;

public class AdapterWeeks extends RecyclerView.Adapter<AdapterWeeks.ViewHolderWeeks> {

    private List<Week> weeks;
    private Context context;

    public AdapterWeeks(List<Week> weeks, Context context) {
        this.weeks = weeks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderWeeks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week, parent,false);
        return new ViewHolderWeeks(view);
    }

    public void filterList(ArrayList<Week> filterlist) {
        weeks = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWeeks holder, int position) {
        Week itemHolder = weeks.get(position);
        holder.textNumOfWeek.setText(itemHolder.getNumOfWeek() + ". " + context.getResources().getString(R.string.week));

        holder.textWeekDone.setText(itemHolder.getDone());

        if (itemHolder.getImg() == (null)) {
            holder.snapProgress.setVisibility(View.VISIBLE);
            holder.imgCamera.setVisibility(View.VISIBLE);
        } else {
            holder.snapProgress.setVisibility(View.VISIBLE);
            holder.imgCamera.setVisibility(View.GONE);

            Glide.with(context)
                    .load(itemHolder.getImg())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.noAnimation())
                    .thumbnail(0.3f)
                    .into(holder.snapProgress);

            Log.d("SNAP", itemHolder.getImg() + "");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeekDataActivity.class);
                intent.putExtra("numOfWeek", String.valueOf(itemHolder.getNumOfWeek()));
                intent.putExtra("weekDone", itemHolder.getDone());
                intent.putExtra("image", itemHolder.getImg());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }

    public class ViewHolderWeeks extends RecyclerView.ViewHolder {

        private TextView textNumOfWeek, textWeekDone;
        private ImageView snapProgress, imgCamera;

        public ViewHolderWeeks(@NonNull View itemView) {
            super(itemView);

            snapProgress = itemView.findViewById(R.id.img_snap_body_every_week);
            imgCamera = itemView.findViewById(R.id.img_camera);
            textWeekDone = itemView.findViewById(R.id.text_week_done);
            textNumOfWeek = itemView.findViewById(R.id.text_num_of_week);

        }
    }

}
