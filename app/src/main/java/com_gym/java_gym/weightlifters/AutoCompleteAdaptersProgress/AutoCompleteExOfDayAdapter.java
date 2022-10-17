package com_gym.java_gym.weightlifters.AutoCompleteAdaptersProgress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.models.Day;
import com_gym.java_gym.weightlifters.models.Exercise;

public class AutoCompleteExOfDayAdapter extends ArrayAdapter<Exercise> {
    private List<Exercise> exerciseFullList;

    public AutoCompleteExOfDayAdapter(@NonNull Context context, @NonNull List<Exercise> exerciseList) {
        super(context, 0, exerciseList);
        exerciseFullList = new ArrayList<>(exerciseList);
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

        Exercise exerciseItem = getItem(position);

        if (exerciseItem != null) {
            textViewName.setText(exerciseItem.getEx());
        }

        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Exercise> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(exerciseFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Exercise item : exerciseFullList) {
                    if (item.getEx().toLowerCase().contains(filterPattern)) {
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
            return ((Exercise) resultValue).getEx();
        }
    };
}
