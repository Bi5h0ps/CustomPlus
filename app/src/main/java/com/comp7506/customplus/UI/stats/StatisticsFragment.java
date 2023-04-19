package com.comp7506.customplus.UI.stats;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.datamodel.ArrivalInfo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsFragment extends Fragment {

    StatisticsViewModel mViewModel;
    ProgressDialog mPdialog;
    public StatisticsFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.spinner)
    Spinner mSpinner;

    @BindView(R.id.line_chart)
    LineChart mLineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        mViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        mViewModel.init();
        mPdialog = new ProgressDialog(requireContext());
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.getDescription().setTextColor(Color.WHITE);
        mLineChart.getXAxis().setTextColor(Color.WHITE);
        mLineChart.getAxis(YAxis.AxisDependency.LEFT).setTextColor(Color.WHITE);
        mLineChart.getAxis(YAxis.AxisDependency.RIGHT).setTextColor(Color.WHITE);


        mViewModel.getData().observe(getViewLifecycleOwner(), arrivalInfo -> {
            ArrayList<String> items = new ArrayList<>();
            for (ArrivalInfo.Datum controlPoint : arrivalInfo.data) {
                items.add(controlPoint.control_point_name);
            }
            Collections.sort(items);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            mPdialog.hide();
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection
                String selectedItem = parent.getItemAtPosition(position).toString();
                ArrivalInfo arrivalInfo = mViewModel.getData().getValue();
                if (arrivalInfo != null) {
                    for (ArrivalInfo.Datum controlPoint : arrivalInfo.data) {
                        if (Objects.equals(controlPoint.control_point_name, selectedItem)) {
                            ArrayList<Entry> values = new ArrayList<>();
                            int index = 0;
                            for (Integer i : controlPoint.arrival_count) {
                                values.add(new Entry(index, i.intValue()));
                                index++;
                            }
                            LineDataSet lineDataSet = new LineDataSet(values, "Data set 1");
                            lineDataSet.setFillAlpha(110);
                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(lineDataSet);
                            LineData data = new LineData(dataSets);
                            mLineChart.setData(data);
                            mLineChart.invalidate();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        mPdialog.setCancelable(false);
        mPdialog.setMessage("Loading...");
        mPdialog.show();
        mViewModel.retrieveArrivalInfo();
        return view;
    };
}
