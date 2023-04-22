package com.comp7506.customplus.UI.transportation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comp7506.customplus.R;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TransportationFragment extends Fragment {

    private static final String TAB_1_NAME = "Subway";
    private static final String TAB_2_NAME = "Shuttle Bus";
    private static final String TAB_3_NAME = "High-speed Railway";

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager2)
    ViewPager2 mViewPager2;

    TransportationAdapter mTransportationAdapter;

    public TransportationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transportation, container, false);
        ButterKnife.bind(this, view);

        FragmentManager fm = getChildFragmentManager();
        mTransportationAdapter = new TransportationAdapter(fm, getLifecycle());
        mViewPager2.setUserInputEnabled(false);
        mViewPager2.setAdapter(mTransportationAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText(TAB_1_NAME));
        mTabLayout.addTab(mTabLayout.newTab().setText(TAB_2_NAME));
        mTabLayout.addTab(mTabLayout.newTab().setText(TAB_3_NAME));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
        return view;
    };
}
