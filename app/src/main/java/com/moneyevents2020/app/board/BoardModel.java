package com.moneyevents2020.app.board;

import java.util.ArrayList;
import java.util.List;

import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.domain.MoneyEvent;
import com.moneyevents2020.app.domain.User;

import static java.lang.Math.max;

public class BoardModel {
    private User user;
    private String boardId;
    private String date;
    private Float budget;
    private Float spent=0f;
    private List<MoneyEvent> moneyEvents = new ArrayList<>();
    private final String userId;

    public BoardModel(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getBudget() {
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

    public String getBoardId() {
        return boardId;
    }

    public void addEvent(MoneyEvent moneyEvent){
        moneyEvents.add(moneyEvent);
        spent += moneyEvent.getValue();
    }

    public void remove(MoneyEvent moneyEvent) {
        spent -=moneyEvent.getValue();
        spent  = max(spent,0);
        moneyEvents.remove(moneyEvent);
    }

    public void addEvents(List<MoneyEvent> moneyEvent){
        moneyEvents.addAll(moneyEvent);
    }

    public List<MoneyEvent> getMoneyEvents() {
        return moneyEvents;
    }


    public void updateReport(DashBoardReport dashBoardReport){
        boardId = dashBoardReport.getBoardId();
        date = dashBoardReport.getDate();
        budget = dashBoardReport.getBudget();
        spent = dashBoardReport.getSpent();
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }


    public User getUser() {
        return user;
    }
}
