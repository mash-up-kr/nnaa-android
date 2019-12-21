package com.mashup.nnaa.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.ImageView;

import com.mashup.nnaa.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    enum Page {HOME, ALARM, MY_LIST, SETTINGS}

    HashMap<Page, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        setMainTabBtnsOnClick();

        onMainTabClicked(Page.HOME);
    }

    private void initFragments() {
        fragmentMap.put(Page.HOME, MainHomeFragment.newInstance());
        fragmentMap.put(Page.ALARM, MainAlarmFragment.newInstance());
        fragmentMap.put(Page.MY_LIST, MainMyListFragment.newInstance());
        fragmentMap.put(Page.SETTINGS, MainSettingFragment.newInstance());
    }

    private void setMainTabBtnsOnClick() {
        ImageView ivHome = findViewById(R.id.tv_tab_home);
        ImageView ivAlarm = findViewById(R.id.tv_tab_alarm);
        ImageView ivMyList = findViewById(R.id.tv_tab_myList);
        ImageView ivSettings = findViewById(R.id.tv_tab_setting);

        ivHome.setOnClickListener(v -> onMainTabClicked(Page.HOME));
        ivAlarm.setOnClickListener(v -> onMainTabClicked(Page.ALARM));
        ivMyList.setOnClickListener(v -> onMainTabClicked(Page.MY_LIST));
        ivSettings.setOnClickListener(v -> onMainTabClicked(Page.SETTINGS));
    }

    private void onMainTabClicked(Page page) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layout_main, fragmentMap.get(page));
        transaction.commitAllowingStateLoss();
    }
}
