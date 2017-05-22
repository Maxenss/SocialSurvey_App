package com.example.social.classes;

import java.util.ArrayList;

/**
 * Created by Maxim on 22.05.2017.
 */

public class Answer {
    private int mQuestionId;
    private String mText;
    private ArrayList<Integer> mAnswersId;

    public Answer(int questionId, String text, ArrayList<Integer> answersId) {
        mQuestionId = questionId;
        mText = text;
        mAnswersId = answersId;
    }

    public Answer(int questionId, String text) {
        mQuestionId = questionId;
        mText = text;
        mAnswersId = new ArrayList<>();
    }

    public Answer(int questionId) {
        mQuestionId = questionId;
        mText = "";
        mAnswersId = new ArrayList<>();
    }

    public Answer() {
        mQuestionId = 0;
        mText = "";
        mAnswersId = new ArrayList<>();
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public ArrayList<Integer> getAnswersId() {
        return mAnswersId;
    }

    public void setAnswersId(ArrayList<Integer> answersId) {
        mAnswersId = answersId;
    }
}
