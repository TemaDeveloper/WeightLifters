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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.databinding.FragmentStatsBinding;
import com_gym.java_gym.weightlifters.models.Progress;
import com_gym.java_gym.weightlifters.recyclerViewAdapter.AdapterWeeksProgress;


public class StatsFragment extends Fragment {

    private FragmentStatsBinding binding;
    private BarChart chart;
    private ArrayList<BarEntry> entries;
    private ArrayList<String> labelsNames;
    private TextView textZoom;
    private RelativeLayout relativeNothing;

    private RecyclerView recyclerViewWeeks;
    private AdapterWeeksProgress adapterWeeks;

    private DatabaseHelper db;

    private List<Progress> progressList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);

        db = new DatabaseHelper(getContext());

        adapterWeeks = new AdapterWeeksProgress(db.getEveryWeek(), getContext());
        recyclerViewWeeks.setAdapter(adapterWeeks);

        entries = new ArrayList<>();
        labelsNames = new ArrayList<>();

        fillWeekProgress();

        if (progressList == null) {
            relativeNothing.setVisibility(View.VISIBLE);
            chart.setVisibility(View.GONE);
            textZoom.setVisibility(View.GONE);
            recyclerViewWeeks.setVisibility(View.GONE);
        } else {
            relativeNothing.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
            textZoom.setVisibility(View.VISIBLE);
            recyclerViewWeeks.setVisibility(View.VISIBLE);
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

    private void init(View view) {
        recyclerViewWeeks = view.findViewById(R.id.recycler_view_weeks);
        chart = view.findViewById(R.id.bar_progress_week);
        relativeNothing = view.findViewById(R.id.relative_layout_nothing);
        textZoom = view.findViewById(R.id.text_zoom);

        recyclerViewWeeks.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewWeeks.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewWeeks.getContext(), linearLayoutManager.getOrientation());
        recyclerViewWeeks.addItemDecoration(dividerItemDecoration);

    }

    private void fillWeekProgress() {
        float progress;
        for (int i = 0; i < db.getEveryWeek().size(); i++) {
            progressList = db.getProgressFromDB(db.getEveryWeek().get(i).getNumOfWeek());
            String week = db.getEveryWeek().get(i).getNumOfWeek() + " " + getResources().getString(R.string.w);

            for(int j = 0; j < progressList.size(); j++){
                progress = progressList.get(j).getProgress();
                Log.d("PROGRESS", "Progress --- " + progress);
                entries.add(new BarEntry(i, progress));
            }
            labelsNames.add(week);
        }
    }
}