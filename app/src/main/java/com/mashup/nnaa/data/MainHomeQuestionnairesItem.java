package com.mashup.nnaa.data;

public class MainHomeQuestionnairesItem {
    public int questionnairesId;
    public String sender;

    /**
     * Create questionnaire item
     * @param questionnairesId
     * @param sender
     */
    public MainHomeQuestionnairesItem(int questionnairesId,
                                      String sender) {
        this.questionnairesId = questionnairesId;
        this.sender = sender;
    }

    /**
     * Create "No Items" item
     */
    public MainHomeQuestionnairesItem() {
        this.questionnairesId = -1;
        this.sender = "No items";
    }
}
