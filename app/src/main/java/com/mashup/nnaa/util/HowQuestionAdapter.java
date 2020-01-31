package com.mashup.nnaa.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.QuestionActivity;
import com.mashup.nnaa.R;
import com.mashup.nnaa.data.HowQuestionItem;

import java.util.ArrayList;

public class HowQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FIRST_CONTENT = 0;
    public static final int SECOND_CONTENT = 1;
    public static final int THIRD_CONTENT = 2;

    Context context;
    private ArrayList<HowQuestionItem> nDataset;

    public HowQuestionAdapter(ArrayList<HowQuestionItem> hItems) {
        nDataset = hItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == FIRST_CONTENT) {
            view = inflater.inflate(R.layout.first_content, parent, false);
            return new FirstViewHolder(view);
        } else if (viewType == SECOND_CONTENT) {
            view = inflater.inflate(R.layout.second_content, parent, false);
            return new SecondViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.third_content, parent, false);
            return new ThirdViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        //set을 id로 줘버리면 뷰타입에 따라 화면이 보여지는가봄
        if (viewHolder instanceof FirstViewHolder) {
            ((FirstViewHolder) viewHolder).how_info_txt.setText(nDataset.get(position).getQuestionary2());
            ((FirstViewHolder) viewHolder).img_how_item.setId(nDataset.get(position).getImgbtn());
            ((FirstViewHolder) viewHolder).img_how_item1.setId(nDataset.get(position).getImgbtn());
            ((FirstViewHolder) viewHolder).img_how_item2.setId(nDataset.get(position).getImgbtn());
            ((FirstViewHolder) viewHolder).img_how_item3.setId(nDataset.get(position).getImgbtn());

        } else if (viewHolder instanceof SecondViewHolder) {
            ((SecondViewHolder) viewHolder).txt_long.setText(nDataset.get(position).getQuestionary2());
            ((SecondViewHolder) viewHolder).img_long.setId(nDataset.get(position).getImgbtn());

        } else {
            ((ThirdViewHolder) viewHolder).txt_ox.setText(nDataset.get(position).getQuestionary2());
            ((ThirdViewHolder) viewHolder).img_good.setId(nDataset.get(position).getImgbtn());
            ((ThirdViewHolder) viewHolder).img_bad.setId(nDataset.get(position).getImgbtn());
        }
    }


    @Override
    public int getItemCount() {

        return nDataset.size();
    }

    @Override
    public int getItemViewType(int position) {
        return nDataset.get(position).getViewType();
    }


    public class FirstViewHolder extends RecyclerView.ViewHolder {

        TextView how_info_txt;
        Button img_how_item, img_how_item1, img_how_item2, img_how_item3;

        FirstViewHolder(View itemView) {
            super(itemView);

            how_info_txt = itemView.findViewById(R.id.how_info_txt);
            img_how_item = itemView.findViewById(R.id.img_how_item);
            img_how_item1 = itemView.findViewById(R.id.img_how_item1);
            img_how_item2 = itemView.findViewById(R.id.img_how_item2);
            img_how_item3 = itemView.findViewById(R.id.img_how_item3);


        }
    }
    public class SecondViewHolder extends RecyclerView.ViewHolder {

        TextView txt_long;
        Button img_long;
        CardView card_view2;

        SecondViewHolder(View itemView) {
            super(itemView);

            txt_long = itemView.findViewById(R.id.txt_long);
            img_long = itemView.findViewById(R.id.img_long);
            card_view2 = itemView.findViewById(R.id.card_view2);

            card_view2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            Toast.makeText(view.getContext(), "주관식 질문으로 넘어갈게요!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), QuestionActivity.class);

                            //img_long.setBackgroundColor(view.getContext().getResources().getColor(R.color.blue));

                            img_long.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorBlue));

                            view.getContext().startActivity(intent);

                            break;
                        }
                        case MotionEvent.ACTION_UP: {

                            img_long.setBackgroundColor(view.getContext().getResources().getColor(R.color.grey));
                            break;
                        }
                    }
                    return false;
                }
            });
        }
    }


    public class ThirdViewHolder extends RecyclerView.ViewHolder {

        TextView txt_ox;
        Button img_good, img_bad;

        ThirdViewHolder(View itemView) {
            super(itemView);

            txt_ox = itemView.findViewById(R.id.txt_ox);
            img_good = itemView.findViewById(R.id.img_good);
            img_bad = itemView.findViewById(R.id.img_bad);
        }
    }
}

