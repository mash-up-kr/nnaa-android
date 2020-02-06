package com.mashup.nnaa.data;

public class QuestionnaireItem{
    public String answeredAt;   //datetime
    public String answer;
    public String category;
    public Boolean completeFlag;
    public String createUserId;
    public String createdAt;         //datetime
    public String id;
    public String [] questions;      //{"엄마는 현재 삶에 만족하나요?","그렇다","매우 그렇다","아니다"}
    public String receiverId;
    public String updatedAt;         //datetime
    /*********************/
    public int index;
    public boolean bookmark;


    public QuestionnaireItem() {
        answer = "";
        completeFlag = false;
    }

    public boolean getCompleteFlag() { return completeFlag; }
    public String getCategory() { return this.category; }
    public String getAnswer() { return answer; }
    public String [] getQuestion() { return questions; }

    public void setAnswer(String ans) { this.answer = ans; }
    public void setQuestions(String [] q) { questions = q; }



}
