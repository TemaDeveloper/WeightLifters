package com_gym.java_gym.weightlifters.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.models.AutoCompleteWeekDay;
import com_gym.java_gym.weightlifters.models.Program;

public class BottomSheetAddDayToWeekFragment extends BottomSheetDialogFragment {

    private static final String TAG = "MyCustomDialog";
    public OnInputSelected mOnInputSelected;

    private AutoCompleteTextView autoCompleteTextView, autoCompleteTextViewProgram;
    private ArrayList<AutoCompleteWeekDay> weekDays;
    private List<Program> programs;
    private MaterialButton btnAddDay, btnCancelDialog;

    private DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_add_day_to_week, container, false);

        db = new DatabaseHelper(getContext());

        programs = db.getEveryProgram();

        weekDays = new ArrayList<>();
        weekDays.add(new AutoCompleteWeekDay(getResources().getString(R.string.m)));
        weekDays.add(new AutoCompleteWeekDay(getResources().getString(R.string.tu)));
        weekDays.add(new AutoCompleteWeekDay(getResources().getString(R.string.we)));
        weekDays.add(new AutoCompleteWeekDay(getResources().getString(R.string.th)));
        weekDays.add(new AutoCompleteWeekDay(getResources().getString(R.string.f)));
        weekDays.add(new AutoCompleteWeekDay(getResources().getString(R.string.s)));
        weekDays.add(new AutoCompleteWeekDay(getResources().getString(R.string.su)));

        autoCompleteTextView = view.findViewById(R.id.res_input_day);
        autoCompleteTextViewProgram = view.findViewById(R.id.res_input_program);
        btnAddDay = view.findViewById(R.id.btn_add_day);
        btnCancelDialog = view.findViewById(R.id.btn_cancel_dialog);

        AutoCompleteWeekDayAdapter autoCompleteWeekDayAdapter = new AutoCompleteWeekDayAdapter(getContext(), weekDays);
        AutoCompleteProgramAdapter autoCompleteProgramAdapter = new AutoCompleteProgramAdapter(getContext(), programs);

        autoCompleteTextViewProgram.setAdapter(autoCompleteProgramAdapter);
        autoCompleteTextView.setAdapter(autoCompleteWeekDayAdapter);

        btnCancelDialog.setOnClickListener(view1 -> {
            getDialog().dismiss();
        });

        btnAddDay.setOnClickListener(view12 -> {
            add();
        });

        return view;
    }


    private void add(){

        final Dialog dialog = new Dialog(getActivity(), R.style.ThemeDialog);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.fragment_alert_dialog_add_day);
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
                if(!autoCompleteTextView.getText().toString().equals(null) && !autoCompleteTextViewProgram.getText().toString().equals(null)){
                    mOnInputSelected.sendInput(autoCompleteTextView.getText().toString(), autoCompleteTextViewProgram.getText().toString());
                }else{
                    Snackbar snackbar = Snackbar.make(v, getResources().getString(R.string.fill_all_boxes), Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.white));
                    snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                    snackbar.show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (BottomSheetAddDayToWeekFragment.OnInputSelected) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }

    public interface OnInputSelected {
        void sendInput(String dayOfWeek, String titleProgram);
    }


    public class AutoCompleteWeekDayAdapter extends ArrayAdapter<AutoCompleteWeekDay> {
        private List<AutoCompleteWeekDay> weekListFull;

        public AutoCompleteWeekDayAdapter(@NonNull Context context, @NonNull List<AutoCompleteWeekDay> weekList) {
            super(context, 0, weekList);
            weekListFull = new ArrayList<>(weekList);
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return countryFilter;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.item_auto_complete, parent, false
                );
            }

            TextView textViewName = convertView.findViewById(R.id.text_auto_complete);

            AutoCompleteWeekDay countryItem = getItem(position);

            if (countryItem != null) {
                textViewName.setText(countryItem.getWeekDay());
            }

            return convertView;
        }

        private Filter countryFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<AutoCompleteWeekDay> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(weekListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (AutoCompleteWeekDay item : weekListFull) {
                        if (item.getWeekDay().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((AutoCompleteWeekDay) resultValue).getWeekDay();
            }
        };
    }

    public class AutoCompleteProgramAdapter extends ArrayAdapter<Program> {
        private List<Program> programListFull;

        public AutoCompleteProgramAdapter(@NonNull Context context, @NonNull List<Program> programList) {
            super(context, 0, programList);
            programListFull = new ArrayList<>(programList);
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return countryFilter;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.item_auto_complete, parent, false
                );
            }

            TextView textViewName = convertView.findViewById(R.id.text_auto_complete);

            Program programItem = getItem(position);

            if (programItem != null) {
                textViewName.setText(programItem.getNamingOfProgram());
            }

            return convertView;
        }

        private Filter countryFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Program> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(programListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Program item : programListFull) {
                        if (item.getNamingOfProgram().toLowerCase().contains(filterPattern)) {
                            suggestions.add(item);
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Program) resultValue).getNamingOfProgram();
            }
        };
    }

}