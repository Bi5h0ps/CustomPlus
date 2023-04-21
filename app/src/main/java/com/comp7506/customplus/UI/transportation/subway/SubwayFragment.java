package com.comp7506.customplus.UI.transportation.subway;

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
import com.comp7506.customplus.UI.datamodel.SubwaySchedule;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubwayFragment extends Fragment {

    @BindView(R.id.direction_1_start)
    TextView mTextDirectionStart1;

    @BindView(R.id.direction_1_destination)
    TextView mTextDirectionDestination1;

    @BindView(R.id.direction_1_time)
    TextView mTextDirectionTime1;

    @BindView(R.id.ontime_text1)
    TextView mTextOnTime1;

    @BindView(R.id.direction_2_start)
    TextView mTextDirectionStart2;

    @BindView(R.id.direction_2_destination)
    TextView mTextDirectionDestination2;

    @BindView(R.id.direction_2_time)
    TextView mTextDirectionTime2;

    @BindView(R.id.ontime_text2)
    TextView mTextOnTime2;

    @BindView(R.id.spinner_subway)
    Spinner mSpinner;

    @BindView(R.id.refresh_button)
    Button mButton;

    @BindView(R.id.direction_2)
    CardView mCardView;

    @BindView(R.id.card1_content)
    ConstraintLayout mCardContent;

    @BindView(R.id.no_data_block)
    ConstraintLayout mNoDataBlock;

    SubwayViewModel mSubwayViewModel;
    ProgressDialog mPdialog;

    public SubwayFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transportation_subway, container, false);
        ButterKnife.bind(this, view);
        mSubwayViewModel = new ViewModelProvider(this).get(SubwayViewModel.class);
        mSubwayViewModel.init();
        mPdialog = new ProgressDialog(requireContext());
        ArrayAdapter<String> adapter = getArrayAdapterSubway();
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                getData(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSubwayViewModel.getSchedule().observe(getViewLifecycleOwner(), subwaySchedule -> {
            renderCard(subwaySchedule);
            mPdialog.hide();
        });

        mSubwayViewModel.getFailStatus().observe(getViewLifecycleOwner(), failed -> {
            Toast.makeText(requireContext(), "Request Timeout", Toast.LENGTH_SHORT).show();
            mPdialog.hide();
        });

        mButton.setOnClickListener(view1 -> {
            String controlPoint = mSpinner.getSelectedItem().toString(); // Get the selected item as a String
            getData(controlPoint);
        });

        return view;
    }

    private void renderCard(SubwaySchedule s) {
        if (s.data.scheduleList == null || s.data.scheduleList.get(0).departureTimes == null ||
                s.data.scheduleList.get(0).departureTimes.size() == 0) {
           showNoData();
           return;
        }
        hideNoData();
        mTextDirectionStart1.setText(s.data.scheduleList.get(0).start);
        mTextDirectionDestination1.setText(s.data.scheduleList.get(0).destination);
        mTextDirectionTime1.setText(s.data.scheduleList.get(0).departureTimes.get(0));
        mCardView.setVisibility(View.GONE);

        if (s.data.scheduleList.size() > 1) {
            mCardView.setVisibility(View.VISIBLE);
            mTextDirectionStart2.setText(s.data.scheduleList.get(1).start);
            mTextDirectionDestination2.setText(s.data.scheduleList.get(1).destination);
            mTextDirectionTime2.setText(s.data.scheduleList.get(1).departureTimes.get(0));
        }
    }

    private ArrayAdapter<String> getArrayAdapterSubway() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Airport");
        items.add("West Kowloon");
        items.add("Man Kam To");
        items.add("Heung Yuen Wai");
        items.add("Shenzhen Bay");
        items.add("Lo Wu");
        items.add("Lok Ma Chau");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void showNoData() {
        mCardContent.setVisibility(View.GONE);
        mNoDataBlock.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.GONE);
    }

    private void hideNoData() {
        mCardContent.setVisibility(View.VISIBLE);
        mNoDataBlock.setVisibility(View.GONE);
        mCardView.setVisibility(View.VISIBLE);
    }

    private void getData(String controlPoint) {
        mPdialog.setCancelable(false);
        mPdialog.setMessage("Loading Data...");
        mPdialog.show();
        mSubwayViewModel.retrieveSubwaySchedule(controlPoint);
    }
}
