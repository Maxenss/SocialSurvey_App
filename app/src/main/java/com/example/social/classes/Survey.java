package com.example.social.classes;

import java.util.ArrayList;

/**
 * Created by Maxim on 15.05.2017.
 */

public class Survey {
    // ID опроса
    private int mSurveyId;
    // Название опроса
    private String mName;
    // Удален ли опрос
    private boolean mIsDeleted;
    // ID пользователя, который создал опрос
    private int mUserId;
    // Коллекция с вопросами для опроса
    private ArrayList<Question> mArrayListQuestions;

    public Survey(int surveyId, String name, boolean isDeleted, int userId, ArrayList<Question> arrayListQuestions) {
        mSurveyId = surveyId;
        mName = name;
        mIsDeleted = isDeleted;
        mUserId = userId;
        mArrayListQuestions = arrayListQuestions;
    }

    public int getSurveyId() {
        return mSurveyId;
    }

    public void setSurveyId(int surveyId) {
        mSurveyId = surveyId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isDeleted() {
        return mIsDeleted;
    }

    public void setDeleted(boolean deleted) {
        mIsDeleted = deleted;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public ArrayList<Question> getArrayListQuestions() {
        return mArrayListQuestions;
    }

    public void setArrayListQuestions(ArrayList<Question> arrayListQuestions) {
        mArrayListQuestions = arrayListQuestions;
    }

    // Метод, для генерация JSON-строки при создании нового опроса
    public String getJSON(){
        return null;
    }
}
