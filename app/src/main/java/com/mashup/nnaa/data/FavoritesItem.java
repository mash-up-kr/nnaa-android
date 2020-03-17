package com.mashup.nnaa.data;

public class FavoritesItem {

    private String favoritesQuestion;
    private boolean favoirtesCheck;

    public String getFavoritesQuestion() {
        return favoritesQuestion;
    }

    public void setFavoritesQuestion(String favoritesQuestion) {
        this.favoritesQuestion = favoritesQuestion;
    }

    public boolean isFavoirtesCheck() {
        return favoirtesCheck;
    }

    public void setFavoirtesCheck(boolean favoirtesCheck) {
        this.favoirtesCheck = favoirtesCheck;
    }

    public FavoritesItem() {
        this.favoritesQuestion = favoritesQuestion;
        this.favoirtesCheck = favoirtesCheck;
    }
}