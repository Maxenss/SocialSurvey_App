package com.example.social.classes;

import java.util.ArrayList;

/**
 * Created by Maxim on 15.05.2017.
 */

// Класс содержаший ответ
public class Question {
    // ID вопроса
    private int mQuestionId;
    // Тип вопроса : Select(одиночный), MultiSelect(множетсвенный )
    private String mQuestionType;
    // Текст вопроса
    private String mText;
    // Порядковый номер вопроса
    private int mOrder;
    // Удален ли вопрос
    private boolean mIsDeleted;
    // Коллекция, содержащая варианты ответов
    private ArrayList<Option> mArrayListOptions;

    public Question(int questionId, String questionType, String text, int order, boolean isDeleted, ArrayList<Option> arrayListOptions) {
        mQuestionId = questionId;
        mQuestionType = questionType;
        mText = text;
        mOrder = order;
        mIsDeleted = isDeleted;
        mArrayListOptions = arrayListOptions;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public String getQuestionType() {
        return mQuestionType;
    }

    public void setQuestionType(String questionType) {
        mQuestionType = questionType;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setOrder(int order) {
        mOrder = order;
    }

    public boolean isDeleted() {
        return mIsDeleted;
    }

    public void setDeleted(boolean deleted) {
        mIsDeleted = deleted;
    }

    public ArrayList<Option> getArrayListOptions() {
        return mArrayListOptions;
    }

    public void setArrayListOptions(ArrayList<Option> arrayListOptions) {
        mArrayListOptions = arrayListOptions;
    }
}
