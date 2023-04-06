package com.comp7506.customplus.UI.transportation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comp7506.customplus.R;

import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public class TransportationFragment extends Fragment {
    public TransportationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transportation, container, false);
        ButterKnife.bind(this, view);
        return view;
    };
}
