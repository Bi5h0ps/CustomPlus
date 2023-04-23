package com.comp7506.customplus.UI.transportation.bus;

import android.app.ProgressDialog;
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
import com.comp7506.customplus.UI.custom.CustomProgressDialog;
import com.comp7506.customplus.UI.transportation.subway.SubwayViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusFragment extends Fragment {

    @BindView(R.id.spinner_bus)
    Spinner mSpinner;

    @BindView(R.id.timelist)
    RecyclerView mRecyclerView;

    @BindView(R.id.button_retry)
    Button mRetryButton;

    @BindView(R.id.error_bock_bus)
    ConstraintLayout mErrorBlock;

    private TextAdapter textAdapter;
    BusViewModel mBusViewModel;
    CustomProgressDialog mPdialog;

    public BusFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transportation_bus, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBusViewModel = new ViewModelProvider(this).get(BusViewModel.class);
        mBusViewModel.init();
        mPdialog = new CustomProgressDialog(requireContext());

        mBusViewModel.getBusSchedule().observe(requireActivity(), busShedule -> {
            mErrorBlock.setVisibility(View.GONE);
            if (busShedule != null && busShedule.routes != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item, busShedule.routes);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
            } else {
                Toast.makeText(requireContext(), "Request out", Toast.LENGTH_SHORT).show();
                mErrorBlock.setVisibility(View.VISIBLE);
            }
            mPdialog.hide();
        });

        mBusViewModel.getFailStatus().observe(getViewLifecycleOwner(), requestFailed -> {
            Toast.makeText(requireContext(), "Request out", Toast.LENGTH_SHORT).show();
            mErrorBlock.setVisibility(View.VISIBLE);
            mPdialog.hide();
        });

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<String> items = new ArrayList<>();
                switch (i) {
                    case 0:
                        items = Objects.requireNonNull(mBusViewModel.getBusSchedule().getValue()).hKMacau;
                        break;
                    case 1:
                        items = Objects.requireNonNull(mBusViewModel.getBusSchedule().getValue()).macauHK;
                        break;
                    case 2:
                        items = Objects.requireNonNull(mBusViewModel.getBusSchedule().getValue()).hKZH;
                        break;
                    case 3:
                        items = Objects.requireNonNull(mBusViewModel.getBusSchedule().getValue()).zHHK;
                        break;
                }
                textAdapter = new TextAdapter(items);
                mRecyclerView.setAdapter(textAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getData();
        return view;
    }

    private void getData() {
        mPdialog.setCancelable(false);
        mPdialog.setMessage("Loading Data...");
        mPdialog.show();
        mBusViewModel.retrieveBusSchedule();
    }
}
