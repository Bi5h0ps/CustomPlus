package com.comp7506.customplus.UI.stats;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.datamodel.ArrivalInfo;
import com.comp7506.customplus.UI.tools.tools;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.comp7506.customplus.UI.tools.tools.getTimeInMillis;

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

    @BindView(R.id.retry_block)
    LinearLayout mRetryBlock;

    @BindView(R.id.retry_button)
    Button mRetryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        mViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        mViewModel.init();
        mPdialog = new ProgressDialog(requireContext());

        mViewModel.getData().observe(getViewLifecycleOwner(), arrivalInfo -> {
            ArrayList<String> items = arrivalInfo.controlPointNames;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(adapter);
            hideRetryBlock();
            mPdialog.hide();
        });

        mViewModel.getFailStatus().observe(getViewLifecycleOwner(), requestFailed -> {
            showRetryBlock();
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
                            configureChart(arrivalInfo ,controlPoint);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        getData();
        return view;
    };

    private void configureChart(ArrivalInfo arrivalInfo, @NonNull ArrivalInfo.Datum controlPoint) {
        ArrayList<Entry> values = new ArrayList<>();
        int index = 0;
        for (Integer i : controlPoint.arrival_count) {
            values.add(new Entry(getTimeInMillis(arrivalInfo.dates.get(index)), i.intValue()));
            index++;
        }
        LineDataSet lineDataSet = new LineDataSet(values, "Arrival Count, Past 30 Days");
        mLineChart.getLegend().setTextColor(Color.WHITE);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.getDescription().setTextColor(Color.WHITE);
        mLineChart.getXAxis().setTextColor(Color.WHITE);
        mLineChart.getAxis(YAxis.AxisDependency.LEFT).setTextColor(Color.WHITE);
        mLineChart.getAxis(YAxis.AxisDependency.RIGHT).setTextColor(Color.WHITE);
        lineDataSet.setValueTextColor(Color.WHITE); // set value label color to white
        lineDataSet.setValueTextSize(5f);
        lineDataSet.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setValueFormatter(new tools.DateFormatter());
        mLineChart.setData(data);
        mLineChart.invalidate();
        mLineChart.animate();
    }

    private void showRetryBlock() {
        mSpinner.setVisibility(View.GONE);
        mLineChart.setVisibility(View.GONE);
        mRetryBlock.setVisibility(View.VISIBLE);
    }

    private void hideRetryBlock() {
        mSpinner.setVisibility(View.VISIBLE);
        mLineChart.setVisibility(View.VISIBLE);
        mRetryBlock.setVisibility(View.GONE);
    }

    private void getData() {
        mPdialog.setCancelable(false);
        mPdialog.setMessage("Loading Data...");
        mPdialog.show();
        mViewModel.retrieveArrivalInfo();
    }
}
