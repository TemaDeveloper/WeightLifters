package com_gym.java_gym.weightlifters.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import com_gym.java_gym.weightlifters.AutoCompleteAdaptersProgress.AutoCompleteExOfDayAdapter;
import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.models.Exercise;


public class BottomSheetAddProgressToWeekFragment extends BottomSheetDialogFragment {

    private TextView textWeekNum;
    private String weekNum;
    private TextInputLayout inputRep, inputWeight, inputSets;
    private AutoCompleteTextView inputEx;
    private MaterialButton btnCancel, btnAddProgress;

    private DatabaseHelper db;
    private List<Exercise> exercises;


    public BottomSheetAddProgressToWeekFragment(String numOfWeek) {
        this.weekNum = numOfWeek;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_add_progress_to_week, container, false);
        init(view);
        textWeekNum.setText(getContext().getResources().getString(R.string.week) + " " + weekNum);
        db = new DatabaseHelper(getContext());

        exercises = db.getEveryDayAndExercise(Integer.parseInt(weekNum));
        inputEx.setAdapter(new AutoCompleteExOfDayAdapter(getContext(), exercises));

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        btnAddProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int set;
                int weight;
                int repetition;
                float progress = 0;



                if (inputSets.getEditText().getText().toString().equals("")) {
                    weight = Integer.parseInt(inputWeight.getEditText().getText().toString());
                    repetition = Integer.parseInt(inputRep.getEditText().getText().toString());
                    progress = 1 + ((weight + repetition) / db.getEveryDay(Integer.parseInt(weekNum)).size());
                } else if (inputWeight.getEditText().getText().toString().equals("")) {
                    set = Integer.parseInt(inputSets.getEditText().getText().toString());
                    repetition = Integer.parseInt(inputRep.getEditText().getText().toString());
                    progress = 1 + ((set + repetition) / db.getEveryDay(Integer.parseInt(weekNum)).size());
                } else if (inputRep.getEditText().getText().toString().equals("")) {
                    weight = Integer.parseInt(inputWeight.getEditText().getText().toString());
                    set = Integer.parseInt(inputSets.getEditText().getText().toString());
                    progress = 1 + ((set + weight) / db.getEveryDay(Integer.parseInt(weekNum)).size());
                }else if (inputSets.getEditText().getText().toString().equals("") && inputWeight.getEditText().getText().toString().equals("")) {
                    repetition = Integer.parseInt(inputRep.getEditText().getText().toString());
                    progress = 1 + ((repetition) / db.getEveryDay(Integer.parseInt(weekNum)).size());
                } else if (inputWeight.getEditText().getText().toString().equals("") && inputRep.getEditText().getText().toString().equals("")) {
                    set = Integer.parseInt(inputSets.getEditText().getText().toString());
                    progress = 1 + ((set) / db.getEveryDay(Integer.parseInt(weekNum)).size());
                } else if (inputRep.getEditText().getText().toString().equals("") && inputSets.getEditText().getText().toString().equals("")) {
                    weight = Integer.parseInt(inputWeight.getEditText().getText().toString());
                    progress = 1 + ((weight) / db.getEveryDay(Integer.parseInt(weekNum)).size());
                } else if (inputRep.getEditText().getText().toString().equals("") && inputSets.getEditText().getText().toString().equals("") && inputWeight.getEditText().getText().toString().equals("")){
                    getDialog().dismiss();
                } else {
                    repetition = Integer.parseInt(inputRep.getEditText().getText().toString());
                    weight = Integer.parseInt(inputWeight.getEditText().getText().toString());
                    set = Integer.parseInt(inputSets.getEditText().getText().toString());
                    progress = 1 + ((set + weight + repetition) / db.getEveryDay(Integer.parseInt(weekNum)).size());
                }


                if (progress < 0)
                    progress = 0;

                db.updateProgress(weekNum, progress);
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void init(View view) {
        textWeekNum = view.findViewById(R.id.text_week_num);
        btnCancel = view.findViewById(R.id.btn_cancel_dialog);
        btnAddProgress = view.findViewById(R.id.btn_add_progress);
        inputWeight = view.findViewById(R.id.text_input_edit_text_weight);
        inputRep = view.findViewById(R.id.text_input_edit_text_rep);
        inputSets = view.findViewById(R.id.text_input_edit_text_sets);
        inputEx = view.findViewById(R.id.res_input_ex);
    }

}
