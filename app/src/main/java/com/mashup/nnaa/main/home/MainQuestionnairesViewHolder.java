package com.mashup.nnaa.main.home;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.MainHomeQuestionnairesItem;

public class MainQuestionnairesViewHolder extends RecyclerView.ViewHolder {
    private TextView tvIndex;
    private TextView tvSender;

    public MainQuestionnairesViewHolder(@NonNull View itemView) {
        super(itemView);

        tvIndex = itemView.findViewById(R.id.tv_index);
        tvSender = itemView.findViewById(R.id.tv_sender);
    }

    public void bind(int position, int totalCount, MainHomeQuestionnairesItem item) {
        if (item.questionnairesId == -1) {
            // Indicate that there is no item when question Id is -1 (empty)
            tvIndex.setVisibility(View.GONE);
            tvSender.setText(R.string.main_home_no_questionnaires);

        } else {
            tvIndex.setVisibility(View.VISIBLE);
            tvIndex.setText(getPositionIndexCharSeq(position, totalCount));
            tvSender.setText(String.format(
                    tvSender.getContext().getString(R.string.main_home_name_sent_this_questions),
                    item.sender));
        }
    }

    private CharSequence getPositionIndexCharSeq(int position, int total) {
        SpannableString strPos = new SpannableString(String.valueOf(position));
        SpannableString strElse = new SpannableString("/" + total);

        strPos.setSpan(new RelativeSizeSpan(1.2f), 0, strPos.length(), 0);
        return TextUtils.concat(strPos, strElse);
    }
}
