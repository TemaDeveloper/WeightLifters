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

public class AutoCompleteDayOfWeekAdapter extends ArrayAdapter<Day> {
    private List<Day> dayFullList;

    public AutoCompleteDayOfWeekAdapter(@NonNull Context context, @NonNull List<Day> dayList) {
        super(context, 0, dayList);
        dayFullList = new ArrayList<>(dayList);
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

        Day dayItem = getItem(position);

        if (dayItem != null) {
            textViewName.setText(dayItem.getDayOfWeek());
        }

        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Day> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(dayFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Day item : dayFullList) {
                    if (item.getDayOfWeek().toLowerCase().contains(filterPattern)) {
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
            return ((Day) resultValue).getDayOfWeek();
        }
    };
}
