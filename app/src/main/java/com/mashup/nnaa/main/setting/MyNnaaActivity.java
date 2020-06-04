package com.mashup.nnaa.main.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.home.MainHomeFragment;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.FriendDto;
import com.mashup.nnaa.network.model.InboxQuestionnaireDto;
import com.mashup.nnaa.util.AccountManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNnaaActivity extends AppCompatActivity {

    TextView myName, num1, num2, num3;
    ImageView nnaaClose;
    PieChart pieChart;
    Button btnDone;
    String name = AccountManager.getInstance().getUserAuthHeaderInfo().getName();
    ArrayList<String> sender = new ArrayList<>();
    Map<String, Integer> count = new HashMap<>();
    ArrayList<PieEntry> yValues = new ArrayList<>();
    ArrayList<String> top = new ArrayList<>();
    ArrayList<Integer> topval = new ArrayList<>();
    private MyNnaaAdapter myNnaaAdapter;
    private ArrayList<FriendDto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_nnaa);

        myName = findViewById(R.id.nnaa_name);
        nnaaClose = findViewById(R.id.img_nnaa_close);
        pieChart = findViewById(R.id.piechart);
        btnDone = findViewById(R.id.btn_nnaa_done);
        num1 = findViewById(R.id.txt_num_1);
        num2 = findViewById(R.id.txt_num_2);
        num3 = findViewById(R.id.txt_num_3);
        myName.setText(String.format("%s님의", name));
        nnaaClose.setOnClickListener(v -> finish());
        btnDone.setOnClickListener(v -> finish());


        RecyclerView recyclerView = findViewById(R.id.nofriend_recyclerview);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        myNnaaAdapter = new MyNnaaAdapter(this, list);
        recyclerView.setAdapter(myNnaaAdapter);

        this.showSender();
    }

    private void showSender() {
        RetrofitHelper.getInstance().getReceivedQuestionnaires(new Callback<List<InboxQuestionnaireDto>>() {
            @Override
            public void onResponse(Call<List<InboxQuestionnaireDto>> call, Response<List<InboxQuestionnaireDto>> response) {
                List<InboxQuestionnaireDto> questionnaires = response.body();
                for (int i = 0; i < questionnaires.size(); i++) {
                    sender.add(questionnaires.get(i).senderName);
                }

                // friend activity -> friend name except firend

                for (int i = 0; i < sender.size(); i++) {
                    if (count.containsKey(sender.get(i))) {
                        count.put(sender.get(i), count.get(sender.get(i)) + 1);

                    } else {
                        count.put(sender.get(i), 1);
                    }
                }

                for (String member : count.keySet()) {
                    int memcount = count.get(member);
                    yValues.add(new PieEntry(memcount, member));
                }

                List<Map.Entry<String, Integer>> list = new ArrayList<>(count.entrySet());
                Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

                for (Map.Entry<String, Integer> entry : list) {
                    top.add(entry.getKey());
                    topval.add(entry.getValue());
                }

                ArrayList<String> args = new ArrayList<>();
                args.add(top.get(0));
                args.add(top.get(1));
                args.add(top.get(2));

                HashMap<String, Integer> topmap = new HashMap<>();
                topmap.put(top.get(0), topval.get(0));
                topmap.put(top.get(1), topval.get(1));
                topmap.put(top.get(2), topval.get(2));

                num1.setText(String.format("%s님", top.get(0)));
                num2.setText(String.format("%s님", top.get(1)));
                num3.setText(String.format("%s님", top.get(2)));

                List<String> keyList = new ArrayList<>(topmap.keySet());
                Collections.sort(keyList, ((o1, o2) -> (topmap.get(o2).compareTo(topmap.get(o1)))));

                ArrayList<String> barKey = new ArrayList<>();
                ArrayList<Integer> barVal = new ArrayList<>();

                for (String key : keyList) {
                    barKey.add(key);
                    barVal.add(topmap.get(key));
                }

                pieChart.setExtraOffsets(5, 10, 5, 5);
                pieChart.setDragDecelerationFrictionCoef(0.95f);

                pieChart.setDrawHoleEnabled(false);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);

                Description description = new Description();
                description.setText("MY NNAA");
                description.setTextSize(15);
                pieChart.setDescription(description);

                pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet dataSet = new PieDataSet(yValues, "");
                dataSet.setSliceSpace(3);
                dataSet.setSelectionShift(count.size());
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(15);
                data.setValueTextColor(Color.BLUE);

                pieChart.setData(data);
            }

            @Override
            public void onFailure(Call<List<InboxQuestionnaireDto>> call, Throwable t) {

            }
        });
    }
}
