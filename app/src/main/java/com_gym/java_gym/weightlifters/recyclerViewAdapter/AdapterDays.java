package com_gym.java_gym.weightlifters.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.models.Day;
import com_gym.java_gym.weightlifters.R;

public class AdapterDays extends RecyclerView.Adapter<AdapterDays.ViewHolderDays> {

    private List<Day> days;
    private Context context;

    public AdapterDays(List<Day> days, Context context) {
        this.days = days;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterDays.ViewHolderDays onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new AdapterDays.ViewHolderDays(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDays.ViewHolderDays holder, int position) {
        holder.dayProgram.setText(days.get(position).getTitleProgram());
        DatabaseHelper db = new DatabaseHelper(context);

        AdapterExWeek adapterExWeek = new AdapterExWeek(db.getEveryExercise(days.get(position).getIdProgram()), context);
        holder.recyclerViewExercises.setAdapter(adapterExWeek);
    }

    @Override
    public int getItemCount() {
        return days.size();
    }


    public class ViewHolderDays extends RecyclerView.ViewHolder {

        private TextView dayProgram;
        private RecyclerView recyclerViewExercises;

        public ViewHolderDays(@NonNull View itemView) {
            super(itemView);

            dayProgram = itemView.findViewById(R.id.text_day_program);
            recyclerViewExercises = itemView.findViewById(R.id.recycler_view_exercises);

            recyclerViewExercises.setHasFixedSize(true);
            recyclerViewExercises.setLayoutManager(new LinearLayoutManager(context));



        }
    }
}

