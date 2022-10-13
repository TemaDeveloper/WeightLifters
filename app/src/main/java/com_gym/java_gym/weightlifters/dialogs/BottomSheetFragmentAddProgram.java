package com_gym.java_gym.weightlifters.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import com_gym.java_gym.weightlifters.R;

public class BottomSheetFragmentAddProgram extends BottomSheetDialogFragment {

    private static final String TAG = "MyCustomDialog";
    public BottomSheetFragmentAddProgram.OnInputSelected mOnInputSelected;
    //widgets
    private TextInputLayout textInputEditTextProgramTitle;
    private MaterialButton btnAddToRecycler, btnCancelDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_add_program, container, false);
        init(view);

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        btnAddToRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String program = textInputEditTextProgramTitle.getEditText().getText().toString().trim();

                Log.d("ADD_DIALOG", "program --- " + program);

                if (!program.equals("")) {
                    mOnInputSelected.sendInput(program);
                }

                getDialog().dismiss();
            }
        });
        return view;
    }

    private void init(View view) {

        textInputEditTextProgramTitle = view.findViewById(R.id.text_input_edit_text_program_title);
        btnAddToRecycler = view.findViewById(R.id.btn_add_to_recycler);
        btnCancelDialog = view.findViewById(R.id.btn_cancel_dialog);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (BottomSheetFragmentAddProgram.OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public interface OnInputSelected {
        void sendInput(String program);
    }
}
