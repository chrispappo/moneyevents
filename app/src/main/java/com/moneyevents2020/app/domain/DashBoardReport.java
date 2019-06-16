package com.moneyevents2020.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.moneyevents2020.app.domain.DataType.REPORT;
import static com.moneyevents2020.app.domain.DataType.USERS;
import static com.moneyevents2020.app.domain.DataType.USER_BOARDS_LIST;

public class DashBoardReport implements Serializable {
     public String boardId;
     public String name;
     private List<String> users = new ArrayList<>();
     public String owner;
     public String date;
     public float budget;
     public Float spent;

    public DashBoardReport() {
    }

    public DashBoardReport(String boardId, String name, String owner, String date, float budget, Float spent) {
        this.boardId = boardId;
        this.name = name;
        this.owner = owner;
        this.date = date;
        this.budget = budget;
        this.spent = spent;
        users.add(owner);
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }


    public String getBoardUrl(){
        return REPORT +"/"+date+"/"+boardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public Float getSpent() {
        return spent;
    }

    public void setSpent(Float spent) {
        this.spent = spent;
    }

    public String getCollection() {
        return REPORT.name();
    }

    public String getUserBoardCollection(){
        return USERS+"/"+owner+"/"+ USER_BOARDS_LIST;
    }
}
