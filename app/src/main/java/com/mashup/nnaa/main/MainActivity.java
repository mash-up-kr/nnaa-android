package com.mashup.nnaa.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mashup.nnaa.R;
import com.mashup.nnaa.select.SetTypeOfFriendActivity;
import com.mashup.nnaa.main.home.MainHomeFragment;
import com.mashup.nnaa.main.mylist.MainMyListFragment;
import com.mashup.nnaa.main.notifications.MainNotificationsFragment;
import com.mashup.nnaa.main.setting.MainSettingFragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    enum Page {HOME, ALARM, MY_LIST, SETTINGS}

    HashMap<Page, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setOnMainTabBtnsClicked();
        setOnMakeQuestionBtnClicked();

        // Load HOME fragment page
        onMainTabClicked(Page.HOME);
    }

    private void setOnMainTabBtnsClicked() {
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

        transaction.replace(R.id.layout_main, getFragment(page));
        transaction.commitNowAllowingStateLoss();
    }

    private void setOnMakeQuestionBtnClicked() {
        FloatingActionButton btn = findViewById(R.id.btn_make_question);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SetTypeOfFriendActivity.class);
            startActivity(intent);
        });
    }

    private Fragment getFragment(Page page) {
        Fragment returnFragment = fragmentMap.get(page);

        if (returnFragment == null
                || getSupportFragmentManager().findFragmentById(returnFragment.getId()) == null) {
            switch (page) {
                case HOME:
                    fragmentMap.put(Page.HOME, MainHomeFragment.newInstance());
                        break;

                case ALARM:
                    fragmentMap.put(Page.ALARM, MainNotificationsFragment.newInstance());
                    break;

                case MY_LIST:
                    fragmentMap.put(Page.MY_LIST, MainMyListFragment.newInstance());
                    break;

                case SETTINGS:
                    fragmentMap.put(Page.SETTINGS, MainSettingFragment.newInstance());
                    break;
            }

            returnFragment = fragmentMap.get(page);

        }

        return returnFragment;
    }
}
