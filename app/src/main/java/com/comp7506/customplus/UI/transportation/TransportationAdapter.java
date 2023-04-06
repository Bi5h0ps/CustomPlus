package com.comp7506.customplus.UI.transportation;

import com.comp7506.customplus.UI.transportation.bus.BusFragment;
import com.comp7506.customplus.UI.transportation.railway.RailwayFragment;
import com.comp7506.customplus.UI.transportation.subway.SubwayFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TransportationAdapter extends FragmentStateAdapter {

    public TransportationAdapter(@NonNull FragmentManager fragmentManager,
                        @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new BusFragment();
            case 2:
                return new RailwayFragment();
        }
        return new SubwayFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
