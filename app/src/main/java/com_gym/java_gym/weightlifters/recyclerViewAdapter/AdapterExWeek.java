package com_gym.java_gym.weightlifters.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com_gym.java_gym.weightlifters.models.Exercise;
import com_gym.java_gym.weightlifters.R;

public class AdapterExWeek extends RecyclerView.Adapter<AdapterExWeek.ViewHolderExercises> {

    private List<Exercise> exercises;
    private Context context;

    public AdapterExWeek(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderExercises onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_week, parent, false);
        return new ViewHolderExercises(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderExercises holder, int position) {
        holder.textSets.setText( exercises.get(position).getSets());
        holder.textEx.setText(exercises.get(position).getEx());
        holder.textRep.setText(exercises.get(position).getRepetition());
        holder.textWeight.setText(exercises.get(position).getWeight());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ViewHolderExercises extends RecyclerView.ViewHolder {

        private TextView textEx, textRep, textSets, textWeight;

        public ViewHolderExercises(@NonNull View itemView) {
            super(itemView);

            textEx = itemView.findViewById(R.id.text_ex);
            textWeight = itemView.findViewById(R.id.text_weight);
            textRep = itemView.findViewById(R.id.text_rep);
            textSets = itemView.findViewById(R.id.text_sets);

        }
    }
}
