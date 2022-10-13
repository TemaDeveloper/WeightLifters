package com_gym.java_gym.weightlifters.recyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com_gym.java_gym.weightlifters.activities.ProgramDataActivity;
import com_gym.java_gym.weightlifters.models.Program;
import com_gym.java_gym.weightlifters.R;

public class AdapterPrograms extends RecyclerView.Adapter<AdapterPrograms.ViewHolderPrograms> {

    private List<Program> programs;
    private Context context;

    public AdapterPrograms(List<Program> weeks, Context context) {
        this.programs = weeks;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPrograms.ViewHolderPrograms onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent,false);
        return new ViewHolderPrograms(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPrograms.ViewHolderPrograms holder, int position) {
        Program itemHolder = programs.get(position);
        holder.namingOfProgram.setText(itemHolder.getNamingOfProgram());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProgramDataActivity.class);
                intent.putExtra("programTitle", holder.namingOfProgram.getText().toString());
                intent.putExtra("programId", itemHolder.getId());
                context.startActivity(intent);
            }
        });
    }

    public void filterList(ArrayList<Program> filterList) {
        programs = filterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    public class ViewHolderPrograms extends RecyclerView.ViewHolder {

        private TextView namingOfProgram;
        private ImageView imgBarbel;

        public ViewHolderPrograms(@NonNull View itemView) {
            super(itemView);

            namingOfProgram = itemView.findViewById(R.id.text_naming_of_program);
            int[] array = context.getResources().getIntArray(R.array.colors);
            int randomStr = array[new Random().nextInt(array.length)];
            imgBarbel = itemView.findViewById(R.id.img_barbel);
            imgBarbel.setImageTintList(ColorStateList.valueOf(randomStr));
            namingOfProgram.setTextColor(randomStr);

        }
    }
}
