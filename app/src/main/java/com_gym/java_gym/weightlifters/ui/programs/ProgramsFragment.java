package com_gym.java_gym.weightlifters.ui.programs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;

import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.dialogs.BottomSheetFragmentAddProgram;
import com_gym.java_gym.weightlifters.models.Program;
import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.models.Week;
import com_gym.java_gym.weightlifters.recyclerViewAdapter.AdapterPrograms;

public class ProgramsFragment extends Fragment implements BottomSheetFragmentAddProgram.OnInputSelected {

    //widgets
    private RelativeLayout relativeNothing;
    private RecyclerView recyclerViewPrograms;
    private AdapterPrograms adapterPrograms;
    private MaterialButton btnAddProgram;
    private TextInputLayout textInputLayoutFilterProgram;
    //Database
    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programs, container, false);

        init(view);

        recyclerViewPrograms.setHasFixedSize(true);
        // Setting the layout as Staggered Grid for vertical orientation
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerViewPrograms.setLayoutManager(staggeredGridLayoutManager);


        btnAddProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragmentAddProgram bottomSheetDialogFragment = new BottomSheetFragmentAddProgram();
                bottomSheetDialogFragment.setTargetFragment(ProgramsFragment.this, 1);
                bottomSheetDialogFragment.show(getFragmentManager(), "MyCustomDialog");
            }
        });

        showPrograms();

        textInputLayoutFilterProgram.getEditText().addTextChangedListener(new TextWatcher() {
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

    private void init(View view){
        recyclerViewPrograms = view.findViewById(R.id.recycler_view_programs);
        btnAddProgram = view.findViewById(R.id.btn_add_program);
        textInputLayoutFilterProgram = view.findViewById(R.id.text_input_layout_filter_program);
        relativeNothing = view.findViewById(R.id.relative_layout_nothing);
    }

    private void filter(String text) {
        ArrayList<Program> newList = new ArrayList<>();
        db = new DatabaseHelper(getContext());
        for (Program item : db.getEveryProgram()) {
            if (item.getNamingOfProgram().toLowerCase().contains(text.toLowerCase())){
                newList.add(item);
            }
        }
        adapterPrograms.filterList(newList);
    }

    @Override
    public void sendInput(String programTitle) {
        db = new DatabaseHelper(getContext());
        Program program = new Program(0, programTitle);
        db.addProgram(program);
        showPrograms();
        adapterPrograms.notifyDataSetChanged();

    }

    private void showPrograms(){
        db = new DatabaseHelper(getContext());
        adapterPrograms = new AdapterPrograms(db.getEveryProgram(), getContext());
        recyclerViewPrograms.setAdapter(adapterPrograms);

        if(db.getEveryProgram().size() == 0){
            relativeNothing.setVisibility(View.VISIBLE);
            recyclerViewPrograms.setVisibility(View.GONE);
        }else{
            relativeNothing.setVisibility(View.GONE);
            recyclerViewPrograms.setVisibility(View.VISIBLE);
        }

    }

}