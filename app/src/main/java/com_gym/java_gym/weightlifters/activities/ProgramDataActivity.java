package com_gym.java_gym.weightlifters.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.dialogs.BottomSheetAddExToProgramActivityFragment;
import com_gym.java_gym.weightlifters.models.Exercise;
import com_gym.java_gym.weightlifters.recyclerViewAdapter.AdapterProgramData;

public class ProgramDataActivity extends AppCompatActivity implements BottomSheetAddExToProgramActivityFragment.OnInputSelected {

    //widgets
    private RelativeLayout relativeNothing;
    private RecyclerView recyclerViewProgramEx;
    private ImageView imgBack;
    private MaterialButton btnAddExercise;
    private AdapterProgramData adapterProgramData;
    private TextView textShowActions;
    private EditText textProgramTitle;
    private ExtendedFloatingActionButton fabEditProgramTitle;
    //Database
    private DatabaseHelper db;
    private String programTitle;
    private int programID;
    //var
    private boolean shown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_data);

        init();

        db = new DatabaseHelper(ProgramDataActivity.this);

        programID = getIntent().getIntExtra("programId", 0);
        programTitle = getIntent().getStringExtra("programTitle");
        textProgramTitle.setText(programTitle);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setUpRecyclerView();

        updateTitleOfProgram();

        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetAddExToProgramActivityFragment bottomSheetDialogFragment = new BottomSheetAddExToProgramActivityFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "MyCustomDialog");
            }
        });

        textShowActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shown) {
                    adapterProgramData.activateButtons(true);
                    textShowActions.setText(getResources().getString(R.string.done));
                    shown = false;
                } else {
                    adapterProgramData.activateButtons(false);
                    textShowActions.setText(getResources().getString(R.string.edit));
                    shown = true;
                }

            }
        });

        showData();

    }

    private void setUpRecyclerView(){
        recyclerViewProgramEx.setHasFixedSize(true);
        LinearLayoutManager linearManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewProgramEx.setLayoutManager(linearManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewProgramEx.getContext(), linearManager.getOrientation());
        recyclerViewProgramEx.addItemDecoration(dividerItemDecoration);
    }

    private void updateTitleOfProgram() {
        textProgramTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fabEditProgramTitle.setVisibility(View.VISIBLE);
                if (programTitle.equals(textProgramTitle.getText().toString())) {
                    fabEditProgramTitle.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fabEditProgramTitle.setVisibility(View.GONE);

        fabEditProgramTitle.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(ProgramDataActivity.this);
                boolean success = db.updateProgram(String.valueOf(programID), textProgramTitle.getText().toString().trim());
                if(success == true){
                    Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.success_edit_program), Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.red));
                    snackbar.setBackgroundTint(getResources().getColor(R.color.white));
                    snackbar.show();
                }else{
                    Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.failed_edit_program), Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.white));
                    snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                    snackbar.show();
                }

                fabEditProgramTitle.setVisibility(View.GONE);
            }
        });
    }

    private void init() {
        recyclerViewProgramEx = findViewById(R.id.recycler_view_program_ex);
        textShowActions = findViewById(R.id.text_show_actions);
        imgBack = findViewById(R.id.img_back);
        textProgramTitle = findViewById(R.id.text_program_title);
        btnAddExercise = findViewById(R.id.btn_add_exercise);
        relativeNothing = findViewById(R.id.relative_layout_nothing);
        fabEditProgramTitle = findViewById(R.id.fab_edit_program_title);
    }

    @Override
    public void sendInput(String ex, String rep, String sets, String weight) {
        db = new DatabaseHelper(ProgramDataActivity.this);
        Exercise exercise = new Exercise(-1, programID, programID, programTitle, ex, weight, sets, rep);
        db.addExercise(exercise);
        showData();
        adapterProgramData.notifyDataSetChanged();
    }

    private void showData() {
        db = new DatabaseHelper(ProgramDataActivity.this);
        adapterProgramData = new AdapterProgramData(db.getEveryExercise(programID), ProgramDataActivity.this);
        recyclerViewProgramEx.setAdapter(adapterProgramData);

        if (db.getEveryExercise(programID).size() == 0) {
            relativeNothing.setVisibility(View.VISIBLE);
            recyclerViewProgramEx.setVisibility(View.GONE);
        } else {
            relativeNothing.setVisibility(View.GONE);
            recyclerViewProgramEx.setVisibility(View.VISIBLE);
        }

    }

}