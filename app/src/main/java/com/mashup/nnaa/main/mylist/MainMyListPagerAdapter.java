package com.mashup.nnaa.main.mylist;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mashup.nnaa.NnaaApplication;
import com.mashup.nnaa.R;

public class MainMyListPagerAdapter extends FragmentStateAdapter {
    public MainMyListPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public enum MyListType {
        RECEIVED(R.string.mylist_tab_received),
        SENT(R.string.mylist_tab_sent);

        private int textResId;
        MyListType(int textResId) {
            this.textResId = textResId;
        }
        public String getText() { return NnaaApplication.getAppContext().getString(textResId); }
    }

    private SparseArray<MyListType> typeSparseArray = new SparseArray<MyListType>() {{
        put(0, MyListType.RECEIVED);
        put(1, MyListType.SENT);
    }};

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0: case 1:
                fragment = MainMyListSubFragment.newInstance(typeSparseArray.get(position));
                break;

            default:
                fragment = new Fragment();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return typeSparseArray.size();
    }

    public SparseArray<MyListType> getTypeSparseArray() {
        return typeSparseArray;
    }
}
