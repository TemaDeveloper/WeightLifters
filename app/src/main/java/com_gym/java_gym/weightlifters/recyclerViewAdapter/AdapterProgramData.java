package com_gym.java_gym.weightlifters.recyclerViewAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.activities.ProgramDataActivity;
import com_gym.java_gym.weightlifters.activities.UpdateProgramActivity;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.models.Exercise;

public class AdapterProgramData extends RecyclerView.Adapter<AdapterProgramData.ViewHolderProgram>{

    private List<Exercise> exercises;
    private Context context;
    private boolean activate;

    public AdapterProgramData(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterProgramData.ViewHolderProgram onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ex, parent, false);
        return new AdapterProgramData.ViewHolderProgram(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProgramData.ViewHolderProgram holder, int position) {
        Exercise exercise = exercises.get(position);

        holder.textSets.setText(exercise.getSets());
        holder.textEx.setText(exercise.getEx());
        holder.textRep.setText(exercise.getRepetition());
        holder.textWeight.setText(exercise.getWeight());

        holder.fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateProgramActivity.class);
                intent.putExtra("idIndependent", exercise.getIdIndependentEx() + "");
                Log.d("ID_PROGRAM", exercise.getIdProgram() + "");
                intent.putExtra("titleEx", exercise.getEx());
                intent.putExtra("weightEx", exercise.getWeight());
                intent.putExtra("setsEx", exercise.getSets());
                intent.putExtra("repetitionsEx", exercise.getRepetition());
                context.startActivity(intent);
            }
        });

        holder.fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(context, R.style.ThemeDialog);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.fragment_alert_dialog_delete_exercise);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                MaterialButton btnNo = dialog.findViewById(R.id.btn_no), btnYes = dialog.findViewById(R.id.btn_yes);

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        Exercise itemDelete = exercises.get(holder.getAdapterPosition());
                        db.deleteExercise(itemDelete);
                        exercises.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.fabEdit.setVisibility(View.GONE);
        holder.fabDelete.setVisibility(View.GONE);

        if (activate) {
            holder.fabEdit.setVisibility(View.VISIBLE);
            holder.fabDelete.setVisibility(View.VISIBLE);

            holder.fabDelete.show();
            holder.fabEdit.show();
        } else {
            holder.fabDelete.hide();
            holder.fabEdit.hide();
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void activateButtons(boolean activate) {
        this.activate = activate;
        notifyDataSetChanged(); //need to call it for the child views to be re-created with buttons.
    }

    public class ViewHolderProgram extends RecyclerView.ViewHolder {

        private TextView textEx, textRep, textSets, textWeight;
        private FloatingActionButton fabDelete, fabEdit;

        public ViewHolderProgram(@NonNull View itemView) {
            super(itemView);

            textEx = itemView.findViewById(R.id.text_ex);
            textWeight = itemView.findViewById(R.id.text_weight);
            textRep = itemView.findViewById(R.id.text_rep);
            textSets = itemView.findViewById(R.id.text_sets);
            fabDelete = itemView.findViewById(R.id.fab_delete);
            fabEdit = itemView.findViewById(R.id.fab_edit);



        }

    }

}