package com.example.social.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maxim on 15.05.2017.
 */

public class Survey {
    // ID опроса
    private int mSurveyId;
    // Название опроса
    private String mName;
    // Комментарии
    private String mComment;
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

    public Survey() {
        mArrayListQuestions = new ArrayList<>();
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

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public static Survey getSurveyFromJSON(String jsonString) {
        // Полный опрос
        JSONObject surveyJsonObj;
        // Массив вопросов
        JSONArray questionsJsonArray;
        // Массив ответов
        JSONArray optionsJsonArray;

        JSONObject JSONQuestion;
        JSONObject JSONOption;
        Question question;
        Option option;

        Survey tempSurvey = new Survey();

        try {
            // JSON объект - полный опрос
            surveyJsonObj = new JSONObject(jsonString).getJSONObject("response");
            // JSON массив - вопросы
            questionsJsonArray = surveyJsonObj.getJSONArray("questions");

            // Получаем базовую информацию о опросе
            tempSurvey.setName(surveyJsonObj.getString("name"));
            tempSurvey.setComment(surveyJsonObj.getString("comment"));
            tempSurvey.setDeleted(surveyJsonObj.getBoolean("isDeleted"));
            tempSurvey.setUserId(surveyJsonObj.getInt("userId"));


            for (int i = 0; i < questionsJsonArray.length(); i++) {
                JSONQuestion = questionsJsonArray.getJSONObject(i);

                question = new Question();
                question.setQuestionId(JSONQuestion.getInt("questionId"));
                question.setQuestionType(JSONQuestion.getString("questionType"));
                question.setText(JSONQuestion.getString("text"));
                question.setOrder(JSONQuestion.getInt("order"));
                question.setDeleted(JSONQuestion.getBoolean("isDeleted"));

                optionsJsonArray = JSONQuestion.getJSONArray("options");

                for (int j = 0; j < optionsJsonArray.length(); j++) {
                    JSONOption = optionsJsonArray.getJSONObject(j);

                    option = new Option();
                    option.setOptionId(JSONOption.getInt("optionId"));
                    option.setText(JSONOption.getString("text"));
                    option.setOrder(JSONOption.getInt("order"));
                    option.setDeleted(JSONOption.getBoolean("isDeleted"));

                    question.getArrayListOptions().add(option);
                }
                tempSurvey.getArrayListQuestions().add(question);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tempSurvey;
    }

    // Метод, для генерация JSON-строки при создании нового опроса
    public String getJSONFromSurvey() {
        return null;
    }

    @Override
    public String toString() {
        return "Count of questions - " + mArrayListQuestions.size() + " ";
    }
}
