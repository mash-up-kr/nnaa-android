package com.mashup.nnaa.reply;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.airbnb.lottie.LottieAnimationView;
import com.mashup.nnaa.R;
import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.network.RetrofitHelper;
import com.mashup.nnaa.network.model.QuestionnaireAnswerDto;
import com.mashup.nnaa.util.AccountManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiReplyActivity extends AppCompatActivity {

    private static final String TAG = "MultiReplyActivity";
    TextView reply_number, reply_end_nubmer, txtA, txtB, txtC, txtD, a_txt, b_txt, c_txt, d_txt, txtQuestion;
    ImageButton reply_cancel, reply_choice, reply_O, reply_X, btnA, btnB, btnC, btnD;
    ImageView ox_bar, multi_img1, multi_img2, multi_img3, multi_img4, btn_past;
    Button btn_next_question;
    EditText replyEdit;
    ScrollView scrollView;
    private int count = 1;
    private int content_count = 0;
    private int type_count = 0;
    private long now = System.currentTimeMillis();
    private Date date = new Date(now);
    private SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.KOREA);
    private String time = simple.format(date);
    String id = AccountManager.getInstance().getUserAuthHeaderInfo().getUserId();
    String token = AccountManager.getInstance().getUserAuthHeaderInfo().getToken();
    LottieAnimationView lottieAnimationView, lottieStart, lottieClick;
    HashMap<String, String> answers = new HashMap<>();
    ArrayList<String> key = new ArrayList<>();
    ArrayList<String> val = new ArrayList<>();
    ArrayList<String> contentarray = new ArrayList<>();
    ArrayList<String> typearray = new ArrayList<>();
    HashMap<String, String> hashMap = new HashMap<>();
    JSONObject additional = new JSONObject();
    String a = "";
    String b = "";
    String c = "";
    String d = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_reply);

        txtQuestion = findViewById(R.id.text_question);
        reply_number = findViewById(R.id.reply_number);
        txtA = findViewById(R.id.txt_A);
        txtB = findViewById(R.id.txt_B);
        txtC = findViewById(R.id.txt_C);
        txtD = findViewById(R.id.txt_D);
        a_txt = findViewById(R.id.mutli_txt1);
        b_txt = findViewById(R.id.mutli_txt2);
        c_txt = findViewById(R.id.mutli_txt3);
        d_txt = findViewById(R.id.mutli_txt4);
        btnA = findViewById(R.id.multi_btn_a);
        btnB = findViewById(R.id.multi_btn_b);
        btnC = findViewById(R.id.multi_btn_c);
        btnD = findViewById(R.id.multi_btn_d);
        multi_img1 = findViewById(R.id.multi_img1);
        multi_img2 = findViewById(R.id.multi_img2);
        multi_img3 = findViewById(R.id.multi_img3);
        multi_img4 = findViewById(R.id.multi_img4);
        reply_O = findViewById(R.id.reply_o_btn);
        reply_X = findViewById(R.id.reply_x_btn);
        ox_bar = findViewById(R.id.reply_ox_bar);
        reply_end_nubmer = findViewById(R.id.reply_end_number);
        reply_cancel = findViewById(R.id.reply_cancel);
        btn_past = findViewById(R.id.btn_past);
        reply_choice = findViewById(R.id.reply_choice);
        btn_next_question = findViewById(R.id.btn_next_question);
        replyEdit = findViewById(R.id.reply_edit);
        lottieAnimationView = findViewById(R.id.lottie_complete);
        lottieStart = findViewById(R.id.lottie_start);
        lottieStart.bringToFront();
        lottieClick = findViewById(R.id.lottie_click);
        lottieClick.bringToFront();
        scrollView = findViewById(R.id.scroll_view);

        reply_cancel.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("답변 중단하기");
            builder.setMessage("답변을 중단하실건가요?");
            builder.setPositiveButton("네", (dialogInterface, i) -> {
                Toast.makeText(getApplicationContext(), "답변을 중단하겠습니다.", Toast.LENGTH_SHORT).show();
                finish();
            });
            builder.setNegativeButton("아니요", (dialogInterface, i) -> Toast.makeText(getApplicationContext(), "계속 답변해주세요~", Toast.LENGTH_SHORT).show());
            builder.show();
        });

        lottieClick.setOnClickListener(view -> {

            lottieStart.setVisibility(View.GONE);
            lottieClick.setVisibility(View.GONE);
        });

        // ui 키보드에 밀리는거 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        this.answerQuestion();
    }

    private void answerQuestion() {

        Intent intent = getIntent();
        String question = intent.getStringExtra("list");
        String q_id = intent.getStringExtra("id");
        reply_number.setText(String.valueOf(count));


        try {
            JSONObject object = new JSONObject(Objects.requireNonNull(question));
            Iterator j = object.keys();

            while (j.hasNext()) {
                String b = j.next().toString();
                key.add(b);
            }
            for (int k = 0; k < key.size(); k++) {
                val.add(object.getString(key.get(k)));
                hashMap.put(key.get(k), val.get(k));
                additional.put(key.get(k), val.get(k));
            }
            for (int i = 0; i < additional.length(); i++) {
                reply_end_nubmer.setText(String.valueOf(additional.length()));
                JSONObject jsonObject = object.getJSONObject(key.get(i));
                String content = jsonObject.getString("content");
                String type = jsonObject.getString("type");
                Boolean bookmark = jsonObject.getBoolean("isBookmarked");

                contentarray.add(content);
                typearray.add(type);

                if (bookmark.equals(true)) {
                    reply_choice.setBackgroundResource(R.drawable.choice_btn_heart_on);
                } else if (bookmark.equals(false)) {
                    reply_choice.setBackgroundResource(R.drawable.choice_btn_heart_off);
                }

                String choice = jsonObject.getString("choices");
                JSONObject c_object = new JSONObject(choice);
                if (c_object.has("a") && c_object.has("b") && c_object.has("c") && c_object.has("d")) {
                    a = c_object.getString("a");
                    b = c_object.getString("b");
                    c = c_object.getString("c");
                    d = c_object.getString("d");
                    a_txt.setText(a);
                    b_txt.setText(b);
                    c_txt.setText(c);
                    d_txt.setText(d);
                }
            }


            txtQuestion.setText(contentarray.get(0));

            if (typearray.get(0).equals("객관식")) {
                chooesQuestion();
                btnA.setOnClickListener(v -> {
                    btnA.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                    txtA.setTextColor(Color.WHITE);
                    txtB.getResources().getColor(R.color.darkergrey);
                    txtC.getResources().getColor(R.color.darkergrey);
                    txtD.getResources().getColor(R.color.darkergrey);
                    btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                    answers.put(key.get(0), a);
                });
                btnB.setVisibility(View.VISIBLE);
                btnB.setOnClickListener(v -> {
                    btnB.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                    txtB.setTextColor(Color.WHITE);

                    txtA.getResources().getColor(R.color.darkergrey);
                    txtC.getResources().getColor(R.color.darkergrey);
                    txtD.getResources().getColor(R.color.darkergrey);
                    btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                    answers.put(key.get(0), b);
                });
                btnC.setVisibility(View.VISIBLE);
                btnC.setOnClickListener(v -> {
                    btnC.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                    txtC.setTextColor(Color.WHITE);

                    txtA.getResources().getColor(R.color.darkergrey);
                    txtB.getResources().getColor(R.color.darkergrey);
                    txtD.getResources().getColor(R.color.darkergrey);
                    btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                    answers.put(key.get(0), c);
                });
                btnD.setVisibility(View.VISIBLE);
                btnD.setOnClickListener(v -> {
                    btnD.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                    txtD.setTextColor(Color.WHITE);

                    txtA.getResources().getColor(R.color.darkergrey);
                    txtB.getResources().getColor(R.color.darkergrey);
                    txtC.getResources().getColor(R.color.darkergrey);
                    btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                    btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                    answers.put(key.get(0), d);
                });
            } else if (typearray.get(0).equals("주관식")) {
                subjectQuestion();
                answers.put(key.get(0), replyEdit.getText().toString());

            } else if (typearray.get(0).equals("OX")) {
                oxQuestion();
                reply_X.setOnClickListener(v -> {
                    reply_X.setImageResource(R.drawable.btn_x_sel);
                    reply_O.setImageResource(R.drawable.group_3);
                    answers.put(key.get(0), "X");
                });
                reply_O.setOnClickListener(v -> {
                    reply_O.setImageResource(R.drawable.group_3_blue);
                    reply_X.setImageResource(R.drawable.quiz_btn_x);
                    answers.put(key.get(0), "O");
                });
            }

            btn_next_question.setOnClickListener(view1 -> {

                if (contentarray.size() != 1 && typearray.size() != 1) {
                    content_count++;
                    txtQuestion.setText(contentarray.get(content_count));

                    type_count++;


                    if (typearray.get(type_count).equals("객관식")) {
                        chooesQuestion();
                        btnA.setOnClickListener(v -> {
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtA.setTextColor(Color.WHITE);

                            txtB.getResources().getColor(R.color.darkergrey);
                            txtC.getResources().getColor(R.color.darkergrey);
                            txtD.getResources().getColor(R.color.darkergrey);
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval);

                            answers.put(key.get(type_count), a);
                        });
                        btnB.setVisibility(View.VISIBLE);
                        btnB.setOnClickListener(v -> {
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtB.setTextColor(Color.WHITE);

                            txtA.getResources().getColor(R.color.darkergrey);
                            txtC.getResources().getColor(R.color.darkergrey);
                            txtD.getResources().getColor(R.color.darkergrey);
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                            answers.put(key.get(type_count), b);
                        });
                        btnC.setVisibility(View.VISIBLE);
                        btnC.setOnClickListener(v -> {
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtC.setTextColor(Color.WHITE);

                            txtA.getResources().getColor(R.color.darkergrey);
                            txtB.getResources().getColor(R.color.darkergrey);
                            txtD.getResources().getColor(R.color.darkergrey);
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                            answers.put(key.get(type_count), c);
                        });
                        btnD.setVisibility(View.VISIBLE);
                        btnD.setOnClickListener(v -> {
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtD.setTextColor(Color.WHITE);

                            txtA.getResources().getColor(R.color.darkergrey);
                            txtB.getResources().getColor(R.color.darkergrey);
                            txtC.getResources().getColor(R.color.darkergrey);
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                            answers.put(key.get(type_count), d);
                        });
                    } else if (typearray.get(type_count).equals("주관식")) {
                        subjectQuestion();
                        answers.put(key.get(type_count), replyEdit.getText().toString());

                    } else if (typearray.get(type_count).equals("OX")) {
                        oxQuestion();
                        reply_X.setOnClickListener(v -> {
                            reply_X.setImageResource(R.drawable.btn_x_sel);
                            reply_O.setImageResource(R.drawable.group_3);
                            answers.put(key.get(type_count), "X");
                        });
                        reply_O.setOnClickListener(v -> {
                            reply_O.setImageResource(R.drawable.group_3_blue);
                            reply_X.setImageResource(R.drawable.quiz_btn_x);
                            answers.put(key.get(type_count), "O");
                        });
                    }
                }

                count++;
                reply_number.setText(String.valueOf(count));

                replyEdit.setText("");
                reply_X.setImageResource(R.drawable.quiz_btn_x);
                reply_O.setImageResource(R.drawable.group_3);
                btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                txtA.setTextColor(getResources().getColor(R.color.darkergrey));
                btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                txtB.setTextColor(getResources().getColor(R.color.darkergrey));
                btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                txtC.setTextColor(getResources().getColor(R.color.darkergrey));
                btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                txtD.setTextColor(getResources().getColor(R.color.darkergrey));

                btn_next_question.setEnabled(true);
                btn_past.setEnabled(true);

                if (count == object.length()) {
                    Toast.makeText(MultiReplyActivity.this, "마지막 질문입니다!", Toast.LENGTH_SHORT).show();
                    btn_next_question.setEnabled(false);
                    btn_past.setEnabled(true);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                }
            });


            btn_past.setOnClickListener(view2 -> {

                if (content_count != 0) {
                    content_count--;
                    txtQuestion.setText(contentarray.get(content_count));
                }

                if (type_count != 0) {
                    type_count--;

                    if (typearray.get(type_count).equals("객관식")) {
                        chooesQuestion();
                        btnA.setOnClickListener(v -> {
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtA.setTextColor(Color.WHITE);

                            txtB.getResources().getColor(R.color.darkergrey);
                            txtC.getResources().getColor(R.color.darkergrey);
                            txtD.getResources().getColor(R.color.darkergrey);
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                            answers.put(key.get(type_count), a);
                        });
                        btnB.setVisibility(View.VISIBLE);
                        btnB.setOnClickListener(v -> {
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtB.setTextColor(Color.WHITE);

                            txtA.getResources().getColor(R.color.darkergrey);
                            txtC.getResources().getColor(R.color.darkergrey);
                            txtD.getResources().getColor(R.color.darkergrey);
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                            answers.put(key.get(type_count), b);
                        });
                        btnC.setVisibility(View.VISIBLE);
                        btnC.setOnClickListener(v -> {
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtC.setTextColor(Color.WHITE);

                            txtA.getResources().getColor(R.color.darkergrey);
                            txtB.getResources().getColor(R.color.darkergrey);
                            txtD.getResources().getColor(R.color.darkergrey);
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval);
                            answers.put(key.get(type_count), c);
                        });
                        btnD.setVisibility(View.VISIBLE);
                        btnD.setOnClickListener(v -> {
                            btnD.setBackgroundResource(R.drawable.multi_reply_oval_blue);
                            txtD.setTextColor(Color.WHITE);

                            txtA.getResources().getColor(R.color.darkergrey);
                            txtB.getResources().getColor(R.color.darkergrey);
                            txtC.getResources().getColor(R.color.darkergrey);
                            btnA.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnB.setBackgroundResource(R.drawable.multi_reply_oval);
                            btnC.setBackgroundResource(R.drawable.multi_reply_oval);
                            answers.put(key.get(type_count), d);
                        });
                    } else if (typearray.get(type_count).equals("주관식")) {
                        subjectQuestion();
                        answers.put(key.get(type_count), replyEdit.getText().toString());

                    } else if (typearray.get(type_count).equals("OX")) {
                        oxQuestion();
                        reply_X.setOnClickListener(v -> {
                            reply_X.setImageResource(R.drawable.btn_x_sel);
                            reply_O.setImageResource(R.drawable.group_3);
                            answers.put(key.get(type_count), "X");
                        });
                        reply_O.setOnClickListener(v -> {
                            reply_O.setImageResource(R.drawable.group_3_blue);
                            reply_X.setImageResource(R.drawable.quiz_btn_x);
                            answers.put(key.get(type_count), "O");
                        });
                    }
                }

                if (count == 1) {
                    btn_past.setEnabled(false);
                    btn_next_question.setEnabled(true);
                    Toast.makeText(MultiReplyActivity.this, "첫번째 질문입니다!", Toast.LENGTH_SHORT).show();
                } else {
                    count--;
                    reply_number.setText(String.valueOf(count));
                    btn_past.setEnabled(true);
                    btn_next_question.setEnabled(true);
                }
            });

            lottieAnimationView.setOnClickListener(view1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("답변 완료");
                builder.setMessage("답변을 완료하셨나요?");
                builder.setNegativeButton("취소", (dialogInterface, ii) -> Toast.makeText(getApplicationContext(), "답변을 완료해주세요.", Toast.LENGTH_SHORT).show());

                lastQuestion();

                QuestionnaireAnswerDto answerDto = new QuestionnaireAnswerDto(answers, time);
                builder.setPositiveButton("확인", (dialogInterface, ii) ->
                        RetrofitHelper.getInstance().answerQuestionnaire(id, token, q_id, answerDto, new Callback<QuestionnaireAnswerDto>() {
                            @Override
                            public void onResponse(Call<QuestionnaireAnswerDto> call, Response<QuestionnaireAnswerDto> response) {
                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(view1.getContext(), MainActivity.class);
                                    startActivity(intent);

                                    showNotification();
                                }
                            }

                            @Override
                            public void onFailure(Call<QuestionnaireAnswerDto> call, Throwable t) {
                                Log.v(TAG, Objects.requireNonNull(t.getMessage()));
                            }
                        }));
                builder.show();
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragment", "notification");

        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap noti = BitmapFactory.decodeResource(getResources(), R.drawable.img_background);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MultiReplyActivity.this)
                .setSmallIcon(R.drawable.splashlogo)
                .setContentTitle("NNAA")
                .setContentText("답변이 도착했습니다.")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setLargeIcon(noti)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).notify(0, mBuilder.build());

    }

    private void subjectQuestion() {
        replyEdit.setVisibility(View.VISIBLE);
        reply_X.setVisibility(View.INVISIBLE);
        reply_O.setVisibility(View.INVISIBLE);
        ox_bar.setVisibility(View.INVISIBLE);
        txtA.setVisibility(View.INVISIBLE);
        txtB.setVisibility(View.INVISIBLE);
        txtC.setVisibility(View.INVISIBLE);
        txtD.setVisibility(View.INVISIBLE);
        a_txt.setVisibility(View.INVISIBLE);
        b_txt.setVisibility(View.INVISIBLE);
        c_txt.setVisibility(View.INVISIBLE);
        d_txt.setVisibility(View.INVISIBLE);
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        btnD.setVisibility(View.INVISIBLE);
        multi_img1.setVisibility(View.INVISIBLE);
        multi_img2.setVisibility(View.INVISIBLE);
        multi_img3.setVisibility(View.INVISIBLE);
        multi_img4.setVisibility(View.INVISIBLE);
    }

    private void chooesQuestion() {
        txtA.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        txtB.setVisibility(View.VISIBLE);
        txtC.setVisibility(View.VISIBLE);
        txtD.setVisibility(View.VISIBLE);
        a_txt.setVisibility(View.VISIBLE);
        b_txt.setVisibility(View.VISIBLE);
        c_txt.setVisibility(View.VISIBLE);
        d_txt.setVisibility(View.VISIBLE);
        btnA.setVisibility(View.VISIBLE);
        multi_img1.setVisibility(View.VISIBLE);
        multi_img2.setVisibility(View.VISIBLE);
        multi_img3.setVisibility(View.VISIBLE);
        multi_img4.setVisibility(View.VISIBLE);
        replyEdit.setVisibility(View.INVISIBLE);
        reply_X.setVisibility(View.INVISIBLE);
        reply_O.setVisibility(View.INVISIBLE);
        ox_bar.setVisibility(View.INVISIBLE);

    }

    private void oxQuestion() {
        reply_X.setVisibility(View.VISIBLE);
        reply_O.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
        ox_bar.setVisibility(View.VISIBLE);
        replyEdit.setVisibility(View.INVISIBLE);
        txtA.setVisibility(View.INVISIBLE);
        txtB.setVisibility(View.INVISIBLE);
        txtC.setVisibility(View.INVISIBLE);
        txtD.setVisibility(View.INVISIBLE);
        a_txt.setVisibility(View.INVISIBLE);
        b_txt.setVisibility(View.INVISIBLE);
        c_txt.setVisibility(View.INVISIBLE);
        d_txt.setVisibility(View.INVISIBLE);
        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        btnD.setVisibility(View.INVISIBLE);
        multi_img1.setVisibility(View.INVISIBLE);
        multi_img2.setVisibility(View.INVISIBLE);
        multi_img3.setVisibility(View.INVISIBLE);
        multi_img4.setVisibility(View.INVISIBLE);
    }

    private void lastQuestion() {
        btnA.setOnClickListener(v -> {
            btnA.setBackgroundResource(R.drawable.multi_reply_oval_blue);
            txtA.setTextColor(Color.WHITE);
            answers.put(key.get(key.size() - 1), a);
        });
        btnB.setVisibility(View.VISIBLE);
        btnB.setOnClickListener(v -> {
            btnB.setBackgroundResource(R.drawable.multi_reply_oval_blue);
            txtB.setTextColor(Color.WHITE);
            answers.put(key.get(key.size() - 1), b);
        });
        btnC.setVisibility(View.VISIBLE);
        btnC.setOnClickListener(v -> {
            btnC.setBackgroundResource(R.drawable.multi_reply_oval_blue);
            txtC.setTextColor(Color.WHITE);
            answers.put(key.get(key.size() - 1), c);
        });
        btnD.setVisibility(View.VISIBLE);
        btnD.setOnClickListener(v -> {
            btnD.setBackgroundResource(R.drawable.multi_reply_oval_blue);
            txtD.setTextColor(Color.WHITE);
            answers.put(key.get(key.size() - 1), d);
        });
        answers.put(key.get(key.size() - 1), replyEdit.getText().toString());

        reply_X.setOnClickListener(v -> {
            reply_X.setImageResource(R.drawable.btn_x_sel);
            answers.put(key.get(key.size() - 1), "X");
        });
        reply_O.setOnClickListener(v -> {
            reply_O.setImageResource(R.drawable.group_3_blue);
            answers.put(key.get(key.size() - 1), "O");
        });
    }
}