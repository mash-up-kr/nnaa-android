package com.mashup.nnaa.main.mylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mashup.nnaa.R;

public class MainMyListFragment extends Fragment {
    public static MainMyListFragment newInstance() {
        MainMyListFragment fragment = new MainMyListFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_mylist, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View parent) {
        if (getActivity() == null) {
            return;
        }

        // ViewPager
        ViewPager2 vpMyList = parent.findViewById(R.id.vp_mylist);
        vpMyList.setAdapter(new MainMyListPagerAdapter(
                getActivity().getSupportFragmentManager(),
                getLifecycle())
        );
        vpMyList.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        // TabLayout
        TabLayout tabLayout = parent.findViewById(R.id.tab_mylist);
        new TabLayoutMediator(tabLayout, vpMyList, (tab, position) -> {
            MainMyListPagerAdapter adapter = (MainMyListPagerAdapter) vpMyList.getAdapter();
            if (adapter != null) {
                tab.setText(adapter.getTypeSparseArray().get(position).getText());
            }
        }).attach();
    }
}
