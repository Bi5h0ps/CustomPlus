package com.comp7506.customplus.UI.stats;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.comp7506.customplus.R;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.ButterKnife;

public class StatisticsFragment extends Fragment {

    StatisticsViewModel mViewModel;
    ProgressDialog mPdialog;
    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        mViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        mViewModel.init();
        mPdialog = new ProgressDialog(requireContext());


        mViewModel.getData().observe(getViewLifecycleOwner(), arrivalInfo -> {
            Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
            mPdialog.hide();
        });

        mPdialog.setCancelable(false);
        mPdialog.setMessage("Loading...");
        mPdialog.show();
        mViewModel.retrieveArrivalInfo();
        return view;
    };
}
