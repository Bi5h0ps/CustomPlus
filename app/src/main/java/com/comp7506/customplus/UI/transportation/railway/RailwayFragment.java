package com.comp7506.customplus.UI.transportation.railway;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.datamodel.RailwaySchedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RailwayFragment extends Fragment {
    @BindView(R.id.from_station)
    TextView mFromStation;

    @BindView(R.id.to_station)
    TextView mToStation;

    @BindView(R.id.start_time)
    TextView mStartTime;

    @BindView(R.id.arrive_time)
    TextView mArriveTime;

    @BindView(R.id.train_code)
    TextView mTrainCode;


    @BindView(R.id.duration_time)
    TextView mDurationTime;

    @BindView(R.id.spinner_railway)
    Spinner mSpinner;

    @BindView(R.id.refresh_button)
    Button mButton;


    @BindView(R.id.card_content)
    ConstraintLayout mCardContent;

    @BindView(R.id.no_data_block)
    ConstraintLayout mNoDataBlock;

    RailwayViewModel mRailwayViewModel;
    ProgressDialog mPdialog;

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
        mPdialog = new ProgressDialog(requireContext());
        ArrayAdapter<String> adapter = getArrayAdapterRailway();
        mSpinner.setAdapter(adapter);
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

        mRailwayViewModel.getSchedule().observe(getViewLifecycleOwner(), subwaySchedule -> {
            renderCard(subwaySchedule);
            mPdialog.hide();
        });

        mRailwayViewModel.getFailStatus().observe(getViewLifecycleOwner(), failed -> {
            Toast.makeText(requireContext(), "Request Timeout", Toast.LENGTH_SHORT).show();
            mPdialog.hide();
        });

        mButton.setOnClickListener(view1 -> {
            String toStationName = mSpinner.getSelectedItem().toString(); // Get the selected item as a String
            try {
                getData(toStationName);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        return view;
    }


    private void renderCard(RailwaySchedule s) {
        if (s.data == null) {
            showNoData();
            return;
        } else {
            s.data.size();
        }
        hideNoData();
        mFromStation.setText(s.data.get(0).queryLeftNewDTO.fromStation);
        mToStation.setText(s.data.get(0).queryLeftNewDTO.toStation);
        mStartTime.setText(s.data.get(0).queryLeftNewDTO.startTime);
        mArriveTime.setText(s.data.get(0).queryLeftNewDTO.arriveTime);
        mTrainCode.setText(s.data.get(0).queryLeftNewDTO.trainCode);
        mDurationTime.setText(s.data.get(0).queryLeftNewDTO.durationTime);
    }

    private ArrayAdapter<String> getArrayAdapterRailway() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Futian");
        items.add("Shenzhen North");
        items.add("Dongguan South");
        items.add("Dongguan");
        items.add("Guangzhou East");
        items.add("Guangzhou South");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }


    private void showNoData() {
        mCardContent.setVisibility(View.GONE);
        mNoDataBlock.setVisibility(View.VISIBLE);
    }

    private void hideNoData() {
        mCardContent.setVisibility(View.VISIBLE);
        mNoDataBlock.setVisibility(View.GONE);
    }

    private void getData(String toStationName) throws JSONException {
        mPdialog.setCancelable(false);
        mPdialog.setMessage("Loading Data...");
        mPdialog.show();
        mRailwayViewModel.retrieveRailwaySchedule(toStationName);
    }
}
