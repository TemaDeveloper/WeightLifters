package com_gym.java_gym.weightlifters.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.dialogs.BottomSheetAddProgressToWeekFragment;
import com_gym.java_gym.weightlifters.models.Week;

public class AdapterWeeksProgress extends RecyclerView.Adapter<AdapterWeeksProgress.ViewHolderWeeks> {

    private List<Week> weeks;
    private Context context;

    public AdapterWeeksProgress(List<Week> weeks, Context context) {
        this.weeks = weeks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderWeeks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_week_progress, parent, false);
        return new ViewHolderWeeks(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWeeks holder, int position) {
        Week itemHolder = weeks.get(position);
        DatabaseHelper db = new DatabaseHelper(context);
        holder.textNumOfWeek.setText(context.getResources().getString(R.string.week) + " " + itemHolder.getNumOfWeek());
        holder.textWeekDone.setText(itemHolder.getDone());
        holder.textNumOfDays.setText(context.getResources().getString(R.string.days) + " " + db.getEveryDay(itemHolder.getNumOfWeek()).size());

        for (int i = 0; i < db.getProgressFromDB(itemHolder.getNumOfWeek()).size(); i++) {
            float progress = db.getProgressFromDB(itemHolder.getNumOfWeek()).get(i).getProgress();
            if (progress > 1) {
                holder.imageProgressPositive.setVisibility(View.VISIBLE);
                holder.imageProgressNegative.setVisibility(View.GONE);
            } else if (progress == 1 || progress == 0) {
                holder.imageProgressNegative.setVisibility(View.GONE);
                holder.imageProgressPositive.setVisibility(View.GONE);
            } else {
                holder.imageProgressNegative.setVisibility(View.VISIBLE);
                holder.imageProgressPositive.setVisibility(View.GONE);
            }

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetAddProgressToWeekFragment bottomSheetDialogFragment = new BottomSheetAddProgressToWeekFragment(String.valueOf(itemHolder.getNumOfWeek()));
                bottomSheetDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "MyCustomDialog");
            }
        });


    }

    @Override
    public int getItemCount() {
        return weeks.size();
    }

    public class ViewHolderWeeks extends RecyclerView.ViewHolder {

        private TextView textNumOfWeek, textNumOfDays, textWeekDone;
        private ImageView imageProgressPositive, imageProgressNegative;

        public ViewHolderWeeks(@NonNull View itemView) {
            super(itemView);

            textNumOfWeek = itemView.findViewById(R.id.text_num_of_week);
            textNumOfDays = itemView.findViewById(R.id.text_num_of_days);
            textWeekDone = itemView.findViewById(R.id.text_week_done);
            imageProgressPositive = itemView.findViewById(R.id.img_increase);
            imageProgressNegative = itemView.findViewById(R.id.img_decrease);

        }
    }

}
