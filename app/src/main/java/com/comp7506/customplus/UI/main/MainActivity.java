package com.comp7506.customplus.UI.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.comp7506.customplus.R;
import com.comp7506.customplus.UI.splash.SplashActivity;
import com.comp7506.customplus.UI.stats.StatisticsFragment;
import com.comp7506.customplus.UI.transportation.TransportationFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private static final long showTime = 2;

    StatisticsFragment mStatisticsFragment;
    TransportationFragment mTransportationFragment;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;

    @BindView(R.id.rb_1)
    RadioButton mTransportationButton;

    @BindView(R.id.rb_2)
    RadioButton mStatisticsButton;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        showFragment(1);

        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(R.id.rb_1);
    }

    private void showFragment(int page) {
        FragmentTransaction ft = fragmentManager.beginTransaction();

        hideFragments(ft);
        switch (page) {
            case 1:
                if (mTransportationFragment != null)
                    ft.show(mTransportationFragment);
                else {
                    mTransportationFragment = new TransportationFragment();
                    ft.add(R.id.fl_content_main, mTransportationFragment);
                }
                break;
            case 2:
                if (mStatisticsFragment != null)
                    ft.show(mStatisticsFragment);
                else {
                    mStatisticsFragment = new StatisticsFragment();
                    ft.add(R.id.fl_content_main, mStatisticsFragment);
                }
                break;
        }
        ft.commit();
    }

    public void hideFragments(FragmentTransaction ft) {
        if (mTransportationFragment != null)
            ft.hide(mTransportationFragment);
        if (mStatisticsFragment != null)
            ft.hide(mStatisticsFragment);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.rb_1:
                showFragment(1);
                mTransportationButton.setTextColor(getResources().getColor(R.color.purple_default));
                mStatisticsButton.setTextColor(getResources().getColor(R.color.text_default));
                break;
            case R.id.rb_2:
                showFragment(2);
                mStatisticsButton.setTextColor(getResources().getColor(R.color.purple_default));
                mTransportationButton.setTextColor(getResources().getColor(R.color.text_default));
                break;
        }
    }
}