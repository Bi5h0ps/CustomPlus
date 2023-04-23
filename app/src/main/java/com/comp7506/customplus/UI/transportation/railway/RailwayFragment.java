package com.comp7506.customplus.UI.transportation.railway;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.datamodel.RailwayData;
import com.comp7506.customplus.UI.datamodel.RailwaySchedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RailwayFragment extends Fragment {

    @BindView(R.id.spinner_railway)
    Spinner mSpinner;

    @BindView(R.id.railway_timelist)
    RecyclerView mRailwayTimeList;

    @BindView(R.id.swipe_refresh_railway)
    SwipeRefreshLayout mSwiper;

    @BindView(R.id.error_bock_railway)
    ConstraintLayout mErrorBlock;

    @BindView(R.id.button_retry_railway)
    Button mRetryButton;

    private RailwayAdapter railwayAdapter;
    RailwayViewModel mRailwayViewModel;

    public RailwayFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transportation_railway, container, false);
        ButterKnife.bind(this, view);
        mRailwayViewModel = new ViewModelProvider(this).get(RailwayViewModel.class);
        mRailwayViewModel.init();
        ArrayAdapter<String> adapter = getArrayAdapterRailway();
        mSpinner.setAdapter(adapter);
        mRailwayTimeList.setLayoutManager(new LinearLayoutManager(getContext()));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                try {
                    getData(selectedItem);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mRailwayViewModel.getSchedule().observe(getViewLifecycleOwner(), railwaySchedule -> {
            mErrorBlock.setVisibility(View.GONE);
            mSwiper.setRefreshing(false);
            if (railwaySchedule.data != null) {
                ArrayList<RailwayData> railwayData = new ArrayList<>();
                for (RailwaySchedule.RailwayInfo info : railwaySchedule.data) {
                    String toStation = codeToName(info.queryLeftNewDTO.toStation);
                    String selectToStation = mSpinner.getSelectedItem().toString();
                    if (!toStation.equals(selectToStation)) {
                        continue;
                    }
                    railwayData.add(new RailwayData(
                            codeToName(info.queryLeftNewDTO.fromStation),
                            codeToName(info.queryLeftNewDTO.toStation),
                            info.queryLeftNewDTO.startTime,
                            info.queryLeftNewDTO.arriveTime,
                            info.queryLeftNewDTO.trainCode,
                            info.queryLeftNewDTO.durationTime
                    ));
                }
                railwayAdapter = new RailwayAdapter(railwayData);
                mRailwayTimeList.setAdapter(railwayAdapter);
            } else {
                // System.out.println("data字段为空");
                // Toast.makeText(requireContext(), "Time out", Toast.LENGTH_SHORT).show();
                mErrorBlock.setVisibility(View.VISIBLE);
            }
            mSwiper.setRefreshing(false);
        });

        mRailwayViewModel.getFailStatus().observe(getViewLifecycleOwner(), failed -> {
            Toast.makeText(requireContext(), "Request Timeout", Toast.LENGTH_SHORT).show();
            mErrorBlock.setVisibility(View.VISIBLE);
            mSwiper.setRefreshing(false);
        });

         mSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 String toStation = mSpinner.getSelectedItem().toString(); // Get the selected item as a String
                 try {
                     getData(toStation);
                 } catch (JSONException e) {
                     throw new RuntimeException(e);
                 }
             }
         });

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getData(mSpinner.getSelectedItem().toString());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        return view;
    }


    private ArrayAdapter<String> getArrayAdapterRailway() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Futian");
        items.add("Shenzhen North");
        items.add("Guangzhou East");
        items.add("Guangzhou South");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void getData(String toStationName) throws JSONException {
        mSwiper.setRefreshing(true);
        mRailwayViewModel.retrieveRailwaySchedule(toStationName);
    }

    public static String codeToName(String code) {
        switch (code) {
            case "XJA":
                return "West Kowloon";
            case "NZQ":
                return "Futian";
            case "IOQ":
                return "Shenzhen North";
            case "DNA":
                return "Dongguan South";
            case "RTQ":
                return "Dongguan";
            case "GGQ":
                return "Guangzhou East";
            case "IZQ":
                return "Guangzhou South";
            default:
                return "Unknown";
        }
    }
}
