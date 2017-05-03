package com.example.social;

import java.util.ArrayList;

/**
 * Created by Maxim on 07.05.2016.
 */
public class questions {

    public questions(String ques_text, int countofAsk, boolean isMultiple, ArrayList<String> answers) {
        this.mQuestionsText = ques_text;
        this.mCountOfAsk = countofAsk;
        this.Answers = answers;
        this.mIsMultiple = isMultiple;
    }

    private String mQuestionsText;
    private Integer mCountOfAsk;
    private boolean mIsMultiple = false;
    private ArrayList<String> Answers;

    public String getQuestionsText() {
        return mQuestionsText;
    }

    public void setQuestionsText(String questionsText) {
        mQuestionsText = questionsText;
    }

    public Integer getCountOfAsk() {
        return mCountOfAsk;
    }

    public void setCountOfAsk(Integer countOfAsk) {
        mCountOfAsk = countOfAsk;
    }

    public boolean isMultiple() {
        return mIsMultiple;
    }

    public void setMultiple(boolean multiple) {
        mIsMultiple = multiple;
    }

    public ArrayList<String> getAnswers() {
        return Answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        Answers = answers;
    }
}
