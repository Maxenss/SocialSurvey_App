package com.example.social.classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maxim on 22.05.2017.
 */

public class PassedSurvey {
    private String mStartDate;
    private String mEndDate;
    private String mLatitude;
    private String mLongitude;
    private String mComments;
    private int mSurveyId;
    private ArrayList<Answer> mAnswers;

    public PassedSurvey(String startDate, String endDate, String latitude, String comments,
                        String longitude, int surveyId, ArrayList<Answer> answers) {
        this.mStartDate = startDate;
        this.mEndDate = endDate;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mSurveyId = surveyId;
        this.mAnswers = answers;
        this.mComments = comments;
    }

    public PassedSurvey(String startDate, int surveyId) {
        this.mStartDate = startDate;
        this.mEndDate = "";
        this.mSurveyId = surveyId;
        this.mLatitude = "";
        this.mLongitude = "";
        this.mComments = "";
        this.mAnswers = new ArrayList<>();
    }

    public PassedSurvey() {
        this.mStartDate = "";
        this.mEndDate = "";
        this.mLatitude = "";
        this.mLongitude = "";
        this.mComments = "";
        this.mSurveyId = 0;
        this.mAnswers = new ArrayList<>();
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public int getSurveyId() {
        return mSurveyId;
    }

    public void setSurveyId(int surveyId) {
        mSurveyId = surveyId;
    }

    public ArrayList<Answer> getAnswers() {
        return mAnswers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        mAnswers = answers;
    }

    public String getJSONFromPassedSurvey() {
        JSONObject mainJSONObject = new JSONObject();
        JSONArray answersJSONArray = new JSONArray();
        JSONObject answerJSONObject;
        JSONArray answersIdJSONArray;

        try {
            mainJSONObject.put("startDate", this.mStartDate);
            mainJSONObject.put("endDate", this.mEndDate);
            mainJSONObject.put("latitude", this.mLatitude);
            mainJSONObject.put("longitude", this.mLongitude);
            mainJSONObject.put("comments", this.mComments);
            mainJSONObject.put("surveyId", this.mSurveyId);

            for (int i = 0; i < mAnswers.size(); i++) {
                answerJSONObject = new JSONObject();
                answersIdJSONArray = new JSONArray();

                answerJSONObject.put("questionId", mAnswers.get(i).getQuestionId());
                answerJSONObject.put("text", mAnswers.get(i).getText());

                for (int j = 0; j < mAnswers.get(i).getAnswersId().size(); j++) {
                    answersIdJSONArray.put(mAnswers.get(i).getAnswersId().get(j));
                }

                answerJSONObject.put("optionsId", answersIdJSONArray);

                answersJSONArray.put(answerJSONObject);
            }

            mainJSONObject.put("answers", answersJSONArray);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return mainJSONObject.toString();
    }
}
