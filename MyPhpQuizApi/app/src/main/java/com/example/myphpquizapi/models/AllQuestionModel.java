package com.example.myphpquizapi.models;

import java.util.ArrayList;

public class AllQuestionModel {
ArrayList<Question>  question;

    public AllQuestionModel(ArrayList<Question> question) {
        this.question = question;
    }

    public AllQuestionModel() {
    }

    public ArrayList<Question> getQuestion() {
        return question;
    }

    public void setQuestion(ArrayList<Question> question) {
        this.question = question;
    }
}
