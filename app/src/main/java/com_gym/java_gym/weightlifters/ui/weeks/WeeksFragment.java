package com_gym.java_gym.weightlifters.ui.weeks;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.models.Progress;
import com_gym.java_gym.weightlifters.models.Week;
import com_gym.java_gym.weightlifters.recyclerViewAdapter.AdapterWeeks;

public class WeeksFragment extends Fragment {

    //widgets
    private RelativeLayout relativeNothing;
    private RecyclerView recyclerViewWeeks;
    private FloatingActionButton fabAddWeek;
    private AdapterWeeks adapter;
    private TextInputLayout textInputLayoutFilter;

    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weeks, container, false);

        init(view);

        db = new DatabaseHelper(getContext());


        recyclerViewWeeks.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewWeeks.setHasFixedSize(true);


        fabAddWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addWeek(new Week(db.getEveryWeek().size() + 1, getResources().getString(R.string.week_not_done_text), null));
                Progress progress = new Progress(db.getEveryWeek().size(), 0f, 0, 0, 0);
                db.addProgress(progress);
                showWeeks();
                adapter.notifyDataSetChanged();
            }
        });

        showWeeks();


        textInputLayoutFilter.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        return view;
    }

    private void showWeeks() {
        db = new DatabaseHelper(getContext());
        adapter = new AdapterWeeks(db.getEveryWeek(), getContext());
        recyclerViewWeeks.setAdapter(adapter);

        if(db.getEveryWeek().size() == 0){
            relativeNothing.setVisibility(View.VISIBLE);
            recyclerViewWeeks.setVisibility(View.GONE);
        }else{
            relativeNothing.setVisibility(View.GONE);
            recyclerViewWeeks.setVisibility(View.VISIBLE);
        }

    }

    private void filter(String text) {
        db = new DatabaseHelper(getContext());
        ArrayList<Week> newList = new ArrayList<>();
        for (Week item : db.getEveryWeek()) {
            String n = String.valueOf(item.getNumOfWeek());
            if (n.contains(text)) {
                newList.add(item);
            }
        }
        adapter.filterList(newList);
    }


    private void init(View root) {
        recyclerViewWeeks = root.findViewById(R.id.recycler_view_weeks);
        textInputLayoutFilter = root.findViewById(R.id.text_input_layout_filter);
        fabAddWeek = root.findViewById(R.id.fab_add_week);
        relativeNothing = root.findViewById(R.id.relative_layout_nothing);
    }

}