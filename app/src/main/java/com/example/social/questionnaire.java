package com.example.social;

import java.util.ArrayList;

/**
 * Created by Maxim on 07.05.2016.
 */
 public class questionnaire {

    public questionnaire (String nameOfCreatorB , String nameOfQuestionnaireB , int countOfQuestionsB){
        this.nameOfCreator = nameOfCreatorB;
        this.nameOfQuestionnaire = nameOfQuestionnaireB;
        this.CountOfQuestions = countOfQuestionsB;
    }

    private String nameOfCreator = null;
    private String nameOfCustomer = null;
    private String nameOfQuestionnaire = null;
    private boolean status = true;
    private String location = null; // STRING ?
    private String dateOfCreate = null;
    private String dateOfChange = null;
    private int CountOfQuestions = 0;
    private String Error = null;
    private  ArrayList<questions> CollectionWithQuestions = new ArrayList<>();

    public String getNameOfCreator() {
        return nameOfCreator;
    }
    public void setNameOfCreator(String nameOfCreator) {
        this.nameOfCreator = nameOfCreator;
    }

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }
    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    public String getNameOfQuestionnaire() {
        return nameOfQuestionnaire;
    }
    public void setNameOfQuestionnaire(String nameOfQuestionnaire) {
        this.nameOfQuestionnaire = nameOfQuestionnaire;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateOfCreate() {
        return dateOfCreate;
    }
    public void setDateOfCreate(String dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public String getDateOfChange() {
        return dateOfChange;
    }
    public void setDateOfChange(String dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public int getCountOfQuestions() {
        return CountOfQuestions;
    }
    public void setCountOfQuestions(int countOfQuestions) {
        CountOfQuestions = countOfQuestions;
    }

    public ArrayList<questions> getCollectionWithQuestions() {
        return CollectionWithQuestions;
    }

    public void setCollectionWithQuestions(ArrayList<questions> collectionWithQuestions) {
        CollectionWithQuestions = collectionWithQuestions;
    }

    // Добавляем новый вопрос и ответы.
    public void NewQuestion(String ques_text, int countofAsk, boolean isMultiple, ArrayList<String> answers){
        questions question = new questions(ques_text, countofAsk, isMultiple, answers);
        CollectionWithQuestions.add(question);
    }


}
