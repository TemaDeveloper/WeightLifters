package com_gym.java_gym.weightlifters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;

public class UpdateProgramActivity extends AppCompatActivity {

    //widgets
    private ImageView imgBack;
    private TextInputLayout editTextTitle, editTextWeight, editTextSets, editTextRepetition;
    private MaterialButton btnUpdate, btnCancel;
    //database
    private DatabaseHelper db;
    String idProgram, titleEx, weightEx, setsEx, repetitionsEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_program);

        db = new DatabaseHelper(UpdateProgramActivity.this);

        init();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                db = new DatabaseHelper(UpdateProgramActivity.this);

                boolean successUpdate = db.updateExercise(idProgram, editTextTitle.getEditText().getText().toString(),
                        editTextWeight.getEditText().getText().toString(),
                        editTextSets.getEditText().getText().toString(),
                        editTextRepetition.getEditText().getText().toString());

                if(successUpdate == true){
                    startActivity(new Intent(UpdateProgramActivity.this, MainActivity.class));
                }else{
                    Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.failed_to_update_ex), Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.white));
                    snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                    snackbar.show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void init(){
        editTextWeight = findViewById(R.id.text_input_layout_weight_ex);
        editTextTitle = findViewById(R.id.text_input_layout_naming_ex);
        editTextSets = findViewById(R.id.text_input_layout_sets_ex);
        editTextRepetition = findViewById(R.id.text_input_layout_repetition_ex);
        btnUpdate = findViewById(R.id.btn_update);
        btnCancel = findViewById(R.id.btn_cancel);
        imgBack = findViewById(R.id.img_back);

        idProgram = getIntent().getStringExtra("idIndependent");
        titleEx = getIntent().getStringExtra("titleEx");
        weightEx = getIntent().getStringExtra("weightEx");
        setsEx = getIntent().getStringExtra("setsEx");
        repetitionsEx = getIntent().getStringExtra("repetitionsEx");

        editTextTitle.getEditText().setText(titleEx);
        editTextWeight.getEditText().setText(weightEx);
        editTextSets.getEditText().setText(setsEx);
        editTextRepetition.getEditText().setText(repetitionsEx);
    }

}