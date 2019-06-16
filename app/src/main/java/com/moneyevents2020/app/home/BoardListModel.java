package com.moneyevents2020.app.home;

import java.util.ArrayList;
import java.util.List;

import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.domain.User;

public class BoardListModel {
    private User user;
    private List<DashBoardReport> boards = new ArrayList<>();

    public void addBoards(List<DashBoardReport> dashBoardReports){
        boards.addAll(dashBoardReports);
    }

    public List<DashBoardReport> getBoards() {
        return boards;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void clear() {
        boards.clear();
    }

    public void remove(String boardId) {
        DashBoardReport report=null;
        for (DashBoardReport board : boards) {
            if(board.getBoardId().equals(boardId)){
                report = board;
            }
        }
        if(report!=null){
            boards.remove(report);
        }
    }
}
