package com_gym.java_gym.weightlifters.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import com_gym.java_gym.weightlifters.R;

public class BottomSheetAddExToProgramActivityFragment extends BottomSheetDialogFragment {

    private static final String TAG = "MyCustomDialog";
    public OnInputSelected mOnInputSelected;
    //widgets
    private TextInputLayout textInputEditTextEx, textInputEditTextRep, textInputEditTextSets, textInputEditTextWeight;
    private MaterialButton btnAddToRecycler, btnCancelDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_add_ex, container, false);
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
                String ex = textInputEditTextEx.getEditText().getText().toString().trim();
                String weight = textInputEditTextWeight.getEditText().getText().toString().trim() + " " + getResources().getString(R.string.kg);
                String rep = textInputEditTextRep.getEditText().getText().toString().trim();
                String sets = textInputEditTextSets.getEditText().getText().toString().trim();

                Log.d("ADD_DIALOG", "ex --- " + ex);
                Log.d("ADD_DIALOG", "w --- " + weight);
                Log.d("ADD_DIALOG", "r --- " + rep);
                Log.d("ADD_DIALOG", "s --- " + sets);

                if (!ex.equals("") && !rep.equals("") && !sets.equals("") && !weight.equals("")) {
                    mOnInputSelected.sendInput(ex, rep, sets, weight);
                }else if(!ex.equals("") && !rep.equals("") && !sets.equals("") && weight.equals("")){
                    mOnInputSelected.sendInput(ex, rep, sets,   "0 " + getResources().getString(R.string.kg));
                }else{
                    Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.fill_all_boxes_except_weight), Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.white));
                    snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                    snackbar.show();
                }
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void init(View view) {

        textInputEditTextEx = view.findViewById(R.id.text_input_edit_text_ex);
        textInputEditTextWeight = view.findViewById(R.id.text_input_edit_text_weight);
        textInputEditTextRep = view.findViewById(R.id.text_input_edit_text_rep);
        textInputEditTextSets = view.findViewById(R.id.text_input_edit_text_sets);
        btnAddToRecycler = view.findViewById(R.id.btn_add_to_recycler);
        btnCancelDialog = view.findViewById(R.id.btn_cancel_dialog);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (BottomSheetAddExToProgramActivityFragment.OnInputSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public interface OnInputSelected {
        void sendInput(String ex, String rep, String sets, String weight);
    }
}