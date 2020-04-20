package com.mashup.nnaa.data;

public class MainHomeQuestionnairesItem {
    public String questionnairesId;
    public String sender;

    /**
     * Create questionnaire item
     * @param questionnairesId
     * @param sender
     */
    public MainHomeQuestionnairesItem(String questionnairesId,
                                      String sender) {
        this.questionnairesId = questionnairesId;
        this.sender = sender;
    }

    /**
     * Create "No Items" item
     */
    public MainHomeQuestionnairesItem() {
        this.questionnairesId = "";
        this.sender = "No items";
    }
}
