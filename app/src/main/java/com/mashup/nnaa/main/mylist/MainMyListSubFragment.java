package com.mashup.nnaa.main.mylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.Questionnaire;
import com.mashup.nnaa.network.model.QuestionnaireDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMyListSubFragment extends Fragment {
    private MainMyListPagerAdapter.MyListType type;
    private RecyclerView rvMyList;
    private TextView tvNoItem;

    public static MainMyListSubFragment newInstance(MainMyListPagerAdapter.MyListType type) {
        MainMyListSubFragment fragment = new MainMyListSubFragment();
        fragment.type = type;

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_mylist_sublist, container, false);
        tvNoItem = view.findViewById(R.id.tv_no_item_myList);
        rvMyList = view.findViewById(R.id.rv_myList);
        rvMyList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMyList.setAdapter(new MainMyListDataAdapter());

        switch (type)
        {
            case SENT:
                loadInbox();
                break;
            case RECEIVED:
                loadOutbox();
                break;
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadInbox() {
        RetrofitHelper.getInstance().getReceivedQuestionnaire(new Callback<List<QuestionnaireDto>>() {
            @Override
            public void onResponse(Call<List<QuestionnaireDto>> call, Response<List<QuestionnaireDto>> response) {
                onLoadDataSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<QuestionnaireDto>> call, Throwable t) {
                onLoadDataFailure();
            }
        });
    }

    private void loadOutbox() {
        RetrofitHelper.getInstance().getSendQuestionnaire(new Callback<List<QuestionnaireDto>>() {
            @Override
            public void onResponse(Call<List<QuestionnaireDto>> call, Response<List<QuestionnaireDto>> response) {
                onLoadDataSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<QuestionnaireDto>> call, Throwable t) {
                onLoadDataFailure();
            }
        });
    }

    private void onLoadDataSuccess(List<QuestionnaireDto> data) {
        MainMyListDataAdapter adapter = (MainMyListDataAdapter) rvMyList.getAdapter();
        if (adapter == null)
            return;

        if (data == null)
            data = new ArrayList<>();

        if (data.size() == 0) {
            rvMyList.setVisibility(View.GONE);
            tvNoItem.setVisibility(View.VISIBLE);
        } else {
            rvMyList.setVisibility(View.VISIBLE);
            tvNoItem.setVisibility(View.GONE);
        }

        adapter.setData((ArrayList<QuestionnaireDto>) data);
    }

    private void onLoadDataFailure() {
        Toast.makeText(getContext(), R.string.mylist_fail_to_load, Toast.LENGTH_SHORT).show();
    }
}
