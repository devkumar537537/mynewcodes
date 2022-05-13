package com.example.myphpquizapi.models;

public class Question {
    public String id;
    public String questionnumber;
    public String question;
    public String rightoption;
    public String firstoption;
    public String secondoption;
    public String thirdoption;
    public String fourthoption;

    public Question(String id, String questionnumber, String question, String rightoption, String firstoption, String secondoption, String thirdoption, String fourthoption) {
        this.id = id;
        this.questionnumber = questionnumber;
        this.question = question;
        this.rightoption = rightoption;
        this.firstoption = firstoption;
        this.secondoption = secondoption;
        this.thirdoption = thirdoption;
        this.fourthoption = fourthoption;
    }

    public Question() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionnumber() {
        return questionnumber;
    }

    public void setQuestionnumber(String questionnumber) {
        this.questionnumber = questionnumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRightoption() {
        return rightoption;
    }

    public void setRightoption(String rightoption) {
        this.rightoption = rightoption;
    }

    public String getFirstoption() {
        return firstoption;
    }

    public void setFirstoption(String firstoption) {
        this.firstoption = firstoption;
    }

    public String getSecondoption() {
        return secondoption;
    }

    public void setSecondoption(String secondoption) {
        this.secondoption = secondoption;
    }

    public String getThirdoption() {
        return thirdoption;
    }

    public void setThirdoption(String thirdoption) {
        this.thirdoption = thirdoption;
    }

    public String getFourthoption() {
        return fourthoption;
    }

    public void setFourthoption(String fourthoption) {
        this.fourthoption = fourthoption;
    }
}
