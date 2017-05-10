package com.example.social.classes;

/**
 * Created by Maxim on 10.05.2017.
 */

public class SurveyShort {
    private int surveyId;
    private String name;
    private String comment;
    private boolean isDeleted;
    private int userId;

    public SurveyShort(int surveyId, String name, String comment, boolean isDeleted, int userId) {
        this.surveyId = surveyId;
        this.name = name;
        this.comment = comment;
        this.isDeleted = isDeleted;
        this.userId = userId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCooment() {
        return comment;
    }

    public void setCooment(String cooment) {
        this.comment = cooment;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
