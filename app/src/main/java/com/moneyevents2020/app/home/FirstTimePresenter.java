package com.moneyevents2020.app.home;

import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.moneyevents2020.app.domain.MoneyEvent;
import com.moneyevents2020.app.home.listeners.UserBoardListener;
import com.moneyevents2020.app.util.BoardService;
import com.moneyevents2020.app.util.BoardServiceCloudFireStore;
import com.moneyevents2020.app.domain.DashBoardReport;

public class FirstTimePresenter {
    private FirstTimeView firstTimeView;
    private final BoardListAdapter recordAdapter;
    private final BoardListModel boardListModel;
    private final BoardService boardServices;

    public FirstTimePresenter(FirstTimeView firstTimeView) {
        this.firstTimeView = firstTimeView;
        this.boardListModel = new BoardListModel();
        this.recordAdapter =  new BoardListAdapter(this, firstTimeView,boardListModel);
        boardServices = new BoardServiceCloudFireStore();
    }

    public void presentBoards(List<DashBoardReport> dashBoardReportList) {
        boardListModel.addBoards(dashBoardReportList);
        recordAdapter.notifyDataSetChanged();
    }

    public void showDashBoard(String boardId) {
        firstTimeView.showDashBoard(boardId);
    }

    public String getUserId(){
        return boardServices.getUserId();
    }

    public ListAdapter getBoardListAdapter() {
        return recordAdapter;
    }

    public void presentBoards() {
        firstTimeView.updateTitle("Money Dashboards");

        if(boardServices.isNewUser()) {
             boardServices.registerUser(this);
          //   initExample();
        }else {
            boardServices.getAllBoard(boardServices.getUserId(),new UserBoardListener(this));
        }
    }

    public void initExample() {
        DashBoardReport dashboard = new DashBoardReport(null,"Shopping list example",
                boardServices.getUserId(),
                new SimpleDateFormat("yyyy-MM").format(new Date()),
                0f,
                0f);
        String boardId = boardServices.registerBoard(dashboard);
        MoneyEvent appleEvent = new MoneyEvent(boardId,
                dashboard.getDate(),
                1,
                UUID.randomUUID().toString(),
                boardServices.getUserId(),
                "apples",
                2f,
                "GBP", System.currentTimeMillis());
        MoneyEvent cheese = new MoneyEvent(boardId,
                dashboard.getDate(),
                1,
                UUID.randomUUID().toString(),
                boardServices.getUserId(),
                "cheese",
                5.3f,
                "GBP", System.currentTimeMillis());
        boardServices.registerEvent(appleEvent);
        boardServices.registerEvent(cheese);
        boardServices.getAllBoard(boardServices.getUserId(),new UserBoardListener(this));
    }

    public String registerBoard(DashBoardReport dashboard) {
        return boardServices.registerBoard(dashboard);
    }

    public void clearModel(){
        boardListModel.clear();
        recordAdapter.notifyDataSetChanged();
    }

    public void addFriend(TextView userId, DashBoardReport dashBoardReport) {

    }

    public void deleteBoard(String eventId) {
        this.boardListModel.remove(eventId);
        boardServices.deleteBoard(eventId);
        this.recordAdapter.notifyDataSetChanged();
    }
}
