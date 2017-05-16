package com.example.social.classes;

/**
 * Created by Maxim on 15.05.2017.
 */

// Класс содержащий ответ
public class Option {
    // ID ответа
    private int mOptionId;
    // Текст ответа
    private String mText;
    // Порядковый номер ответа
    private int mOrder;
    // Удален ли ответ
    private boolean mIsDeleted;

    public Option(int optionId, String text, int order, boolean isDeleted) {
        mOptionId = optionId;
        mText = text;
        mOrder = order;
        mIsDeleted = isDeleted;
    }

    public Option(){
    }

    public Option(String text) {
        mText = text;
    }

    public int getOptionId() {
        return mOptionId;
    }

    public void setOptionId(int optionId) {
        mOptionId = optionId;
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
}
