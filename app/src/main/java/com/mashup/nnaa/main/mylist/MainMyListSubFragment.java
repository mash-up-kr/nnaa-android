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
import com.mashup.nnaa.network.model.InboxQuestionnaireDto;
import com.mashup.nnaa.network.model.OutboxQuestionnaireDto;

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
        rvMyList.setAdapter(new MainMyListDataAdapter(type));

        switch (type)
        {
            case SENT:
                loadOutbox();
                break;
            case RECEIVED:
                loadInbox();
                break;
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadInbox() {
        RetrofitHelper.getInstance().getReceivedQuestionnaires(new Callback<List<InboxQuestionnaireDto>>() {
            @Override
            public void onResponse(Call<List<InboxQuestionnaireDto>> call, Response<List<InboxQuestionnaireDto>> response) {
                onLoadDataSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<InboxQuestionnaireDto>> call, Throwable t) {
                onLoadDataFailure();
            }
        });
    }

    private void loadOutbox() {
        RetrofitHelper.getInstance().getSendQuestionnaires(new Callback<List<OutboxQuestionnaireDto>>() {
            @Override
            public void onResponse(Call<List<OutboxQuestionnaireDto>> call, Response<List<OutboxQuestionnaireDto>> response) {
                onLoadDataSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<OutboxQuestionnaireDto>> call, Throwable t) {
                onLoadDataFailure();
            }
        });
    }

    private void onLoadDataSuccess(List data) {
        MainMyListDataAdapter adapter = (MainMyListDataAdapter) rvMyList.getAdapter();
        if (adapter == null)
            return;

        ArrayList<MainMyListDataAdapter.InOutBoxQuestionnaireItem> items = new ArrayList<>();

        if (data != null && !data.isEmpty()) {
            MainMyListDataAdapter.InOutBoxQuestionnaireItem newItem = null;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) instanceof InboxQuestionnaireDto) {
                    newItem = MainMyListDataAdapter.InOutBoxQuestionnaireItem
                            .createFromInboxDto((InboxQuestionnaireDto) data.get(i));
                } else if (data.get(i) instanceof OutboxQuestionnaireDto) {
                    newItem = MainMyListDataAdapter.InOutBoxQuestionnaireItem
                            .createFromOutboxDto((OutboxQuestionnaireDto) data.get(i));
                }
                if (newItem == null) continue;
                items.add(newItem);
            }
        }

        if (items.isEmpty()) {
            rvMyList.setVisibility(View.GONE);
            tvNoItem.setVisibility(View.VISIBLE);
        } else {
            adapter.setData(items, this);
            rvMyList.setVisibility(View.VISIBLE);
            tvNoItem.setVisibility(View.GONE);
        }

        adapter.setData(items, this);
    }

    private void onLoadDataFailure() {
        Toast.makeText(getContext(), R.string.mylist_fail_to_load, Toast.LENGTH_SHORT).show();
    }
}
