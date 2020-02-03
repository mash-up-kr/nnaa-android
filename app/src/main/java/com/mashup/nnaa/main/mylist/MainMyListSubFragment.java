package com.mashup.nnaa.main.mylist;

import androidx.fragment.app.Fragment;

public class MainMyListSubFragment extends Fragment {
    MainMyListPagerAdapter.MyListType type;

    public static MainMyListSubFragment newInstance(MainMyListPagerAdapter.MyListType type) {
        MainMyListSubFragment fragment = new MainMyListSubFragment();
        fragment.type = type;

        return fragment;
    }
}
