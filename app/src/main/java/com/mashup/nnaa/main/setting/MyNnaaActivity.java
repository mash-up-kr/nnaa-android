package com.mashup.nnaa.main.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.mashup.nnaa.network.model.OutboxQuestionnaireDto;
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
import java.util.Objects;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNnaaActivity extends AppCompatActivity {

    TextView myName, num1, num2, num3, firstPer, secondPer, thirdPer, rec1, rec2, rec3, firstRP, secondRP, thirdRP;
    ImageView nnaaClose;
    PieChart pieChart;
    Button btnDone;
    String name = AccountManager.getInstance().getUserAuthHeaderInfo().getName();
    ArrayList<String> sender = new ArrayList<>();       // 나에게 질문 보낸사람
    ArrayList<String> reciever = new ArrayList<>();     // 내가 질문 보낸사람
    Map<String, Integer> count = new HashMap<>();
    Map<String, Integer> sendcount = new HashMap<>();
    ArrayList<PieEntry> yValues = new ArrayList<>();
    ArrayList<String> top = new ArrayList<>();
    ArrayList<Integer> topval = new ArrayList<>();
    HashMap<String, Integer> topmap = new HashMap<>();
    ArrayList<String> topsend = new ArrayList<>();
    ArrayList<Integer> topsendval = new ArrayList<>();
    Map<String, Integer> totalcount = new HashMap<>();
    private MyNnaaAdapter myNnaaAdapter;
    private ArrayList<FriendDto> list;
    int maxValue = 10;
    ArrayList<String> maxSender = new ArrayList<>();
    ArrayList<String> maxReceiver = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();

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
        firstPer = findViewById(R.id.num_1_per);
        secondPer = findViewById(R.id.num_2_per);
        thirdPer = findViewById(R.id.num_3_per);
        rec1 = findViewById(R.id.rec_num_1);
        rec2 = findViewById(R.id.rec_num_2);
        rec3 = findViewById(R.id.rec_num_3);
        firstRP = findViewById(R.id.rec_1_per);
        secondRP = findViewById(R.id.rec_2_per);
        thirdRP = findViewById(R.id.rec_3_per);
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


        this.showMyNnaa();
    }

    private void showMyNnaa() {
        RetrofitHelper.getInstance().getSendQuestionnaires(new Callback<List<OutboxQuestionnaireDto>>() {
            @Override
            public void onResponse(Call<List<OutboxQuestionnaireDto>> call, Response<List<OutboxQuestionnaireDto>> response) {
                List<OutboxQuestionnaireDto> questionnaireDtos = response.body();
                // 보낸 질문
                for (int i = 0; i < Objects.requireNonNull(questionnaireDtos).size(); i++) {
                    reciever.add(questionnaireDtos.get(i).receiverName);
                }
                Log.v("@@@@@@",reciever.toString());

                for (int i = 0; i < reciever.size(); i++) {
                    if (sendcount.containsKey(reciever.get(i))) {
                        sendcount.put(reciever.get(i), sendcount.get(reciever.get(i)) + 1);
                    } else {
                        sendcount.put(reciever.get(i), 1);
                    }
                }
                Log.v("@@@@@@@",sendcount.toString());

                // 오름차순
                List<Map.Entry<String, Integer>> sendlist = new ArrayList<>(sendcount.entrySet());
                Collections.sort(sendlist, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

                for (Map.Entry<String, Integer> entry : sendlist) {
                    topsend.add(entry.getKey());
                    topsendval.add(entry.getValue());
                }
                Log.v("@@@@@",topsend.toString() +"," + topsendval.toString());

                RetrofitHelper.getInstance().getReceivedQuestionnaires(new Callback<List<InboxQuestionnaireDto>>() {
                    @Override
                    public void onResponse(Call<List<InboxQuestionnaireDto>> call, Response<List<InboxQuestionnaireDto>> response) {
                        List<InboxQuestionnaireDto> questionnaires = response.body();
                        for (int i = 0; i < questionnaires.size(); i++) {
                            sender.add(questionnaires.get(i).senderName);
                        }
                        // 받은 질문 목록

                        for (int i = 0; i < sender.size(); i++) {
                            if (count.containsKey(sender.get(i))) {
                                count.put(sender.get(i), count.get(sender.get(i)) + 1);
                            } else {
                                count.put(sender.get(i), 1);
                            }
                        }
                        // 받은 질문 키, 벨류 중복일시 + 1

                        // 주고받은 10개이상
                        for (Map.Entry<String, Integer> entry : count.entrySet()) {
                            if (entry.getValue() >= maxValue) {
                                maxSender.add(entry.getKey());
                            }
                        }

                        for (Map.Entry<String, Integer> entry : sendcount.entrySet()) {
                            if (entry.getValue() >= maxValue) {
                                maxReceiver.add(entry.getKey());
                            }
                        }
                        // 주고받은 10개이상 겹치는 사람
                        for (String str : maxSender) {
                            if (maxReceiver.contains(str)) {
                                arrayList.add(str);
                            }
                        }
                        Log.v("@@@@@",maxSender.toString());
                        Log.v("@@@@@",maxReceiver.toString());
                        Log.v("@@@@", arrayList.toString());

                        for (int i = 0; i < arrayList.size(); i++) {
                            FriendDto friendDto = new FriendDto();
                            friendDto.setName(arrayList.get(i));

                            myNnaaAdapter.addItem(friendDto);
                        }
                        myNnaaAdapter.notifyDataSetChanged();


                        totalcount.putAll(sendcount);
                        totalcount.putAll(count);

                        for (String member : totalcount.keySet()) {
                            int memcount = totalcount.get(member);
                            yValues.add(new PieEntry(memcount, member));
                        }

                        List<Map.Entry<String, Integer>> list = new ArrayList<>(count.entrySet());
                        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

                        for (Map.Entry<String, Integer> entry : list) {
                            top.add(entry.getKey());
                            topval.add(entry.getValue());
                        }

                        if (top.size() > 3 && topval.size() > 3) {
                            topmap.put(top.get(0), topval.get(0));
                            topmap.put(top.get(1), topval.get(1));
                            topmap.put(top.get(2), topval.get(2));

                            num1.setText(String.format("%s님", top.get(0)));
                            num2.setText(String.format("%s님", top.get(1)));
                            num3.setText(String.format("%s님", top.get(2)));
                            firstPer.setText(String.format("%d회", topval.get(0)));
                            secondPer.setText(String.format("%d회", topval.get(1)));
                            thirdPer.setText(String.format("%d회", topval.get(2)));
                        } else {
                            firstPer.setText(String.format("질문을 더 보내주세요!"));
                        }

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
                        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                        PieData data = new PieData((dataSet));
                        data.setValueTextSize(15);
                        data.setValueTextColor(Color.BLUE);

                        pieChart.setData(data);
                    }

                    @Override
                    public void onFailure(Call<List<InboxQuestionnaireDto>> call, Throwable t) {

                    }
                });

                if (topsend.size() > 3) {
                    rec1.setText(String.format("%s님", topsend.get(0)));
                    rec2.setText(String.format("%s님", topsend.get(1)));
                    rec3.setText(String.format("%s님", topsend.get(2)));
                    firstRP.setText(String.format("%d회", topsendval.get(0)));
                    secondRP.setText(String.format("%d회", topsendval.get(1)));
                    thirdRP.setText(String.format("%d회", topsendval.get(2)));
                } else {
                    firstRP.setText(String.format("질문을 더 보내주세요!"));
                }
            }

            @Override
            public void onFailure(Call<List<OutboxQuestionnaireDto>> call, Throwable t) {

            }
        });
    }
}
