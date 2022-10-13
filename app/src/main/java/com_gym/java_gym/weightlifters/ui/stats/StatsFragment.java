package com_gym.java_gym.weightlifters.ui.stats;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.databinding.FragmentStatsBinding;
import com_gym.java_gym.weightlifters.models.WeekProgress;


public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;
    private BarChart chart;
    private ArrayList<BarEntry> entries;
    private ArrayList<String> labelsNames;
    private ArrayList<WeekProgress> weeksProgress = new ArrayList<>();
    private TextView textZoom;
    private RelativeLayout relativeNothing;

    private DatabaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chart = root.findViewById(R.id.bar_progress_week);
        relativeNothing = root.findViewById(R.id.relative_layout_nothing);
        textZoom = root.findViewById(R.id.text_zoom);

        db = new DatabaseHelper(getContext());

        entries = new ArrayList<>();
        labelsNames = new ArrayList<>();

        countProgress();


        for (int i = 0; i < weeksProgress.size(); i++) {
            String week = weeksProgress.get(i).getNumOfWeek() + " " + getResources().getString(R.string.w);
            int progress = weeksProgress.get(i).getProgress();
            entries.add(new BarEntry(i, progress));
            labelsNames.add(week);
        }

        if(weeksProgress.size() == 0){
            relativeNothing.setVisibility(View.VISIBLE);
            chart.setVisibility(View.GONE);
            textZoom.setVisibility(View.GONE);
        }else{
            relativeNothing.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
            textZoom.setVisibility(View.VISIBLE);
        }

        BarDataSet barDataSet = new BarDataSet(entries, getResources().getString(R.string.progress));
        barDataSet.setColor(getContext().getResources().getColor(R.color.red));
        chart.getDescription().setEnabled(false);

        BarData barData = new BarData(barDataSet);
        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsNames.size());
        xAxis.setLabelRotationAngle(270);

        chart.getXAxis().setPosition(XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);

        chart.invalidate();

        return root;
    }

    /*private void fillWeekProgress(){
        weeksProgress.clear();
        weeksProgress.add(new WeekProgress(1, 1));
        weeksProgress.add(new WeekProgress(2, 2));
        weeksProgress.add(new WeekProgress(3, 3));
        weeksProgress.add(new WeekProgress(4, 1));
        weeksProgress.add(new WeekProgress(5, 4));
    }*/
    private void countProgress() {
        db = new DatabaseHelper(getContext());
        for (int i = 0; i < db.getEveryWeek().size(); i++) {
            try {
                int programID = db.getEveryDay(db.getEveryWeek().get(i).getNumOfWeek()).get(i).getIdProgram();
                for(int j = 0; j < db.getEveryDay(db.getEveryWeek().get(i).getNumOfWeek()).size(); j++){
                    int s = Integer.parseInt(db.getEveryExercise(programID).get(j).getSets());
                    int r = Integer.parseInt(db.getEveryExercise(programID).get(j).getRepetition());
                    Log.d("SETS_REP ", s + " " + r);
                    int totalProgressOfWeek = (s + r) / db.getEveryDay(db.getEveryWeek().get(i).getNumOfWeek()).size();
                    weeksProgress.add(new WeekProgress(db.getEveryWeek().get(i).getNumOfWeek(), totalProgressOfWeek));
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }

    }
}