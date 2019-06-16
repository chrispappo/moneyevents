package com.moneyevents2020.app.board;

import android.support.v4.app.FragmentManager;
import android.widget.ListView;

import com.google.firebase.firestore.EventListener;

import com.moneyevents2020.app.board.dialogs.NewEventDialog;
import com.moneyevents2020.app.board.listeners.BoardListener;
import com.moneyevents2020.app.board.listeners.MoneyEventListener;
import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.domain.MoneyEvent;
import com.moneyevents2020.app.domain.MoneyEventUpdateType;
import com.moneyevents2020.app.util.BoardService;
import com.moneyevents2020.app.util.BoardServiceCloudFireStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BoardPresenter {
    private BoardModel boardModel;
    private BoardService boardServices;
    private BoardView boardView;
    private NewEventDialog addNewEventDialog;
    private BoardEventsAdapter boardEventsAdapter;
    private ListView listView;

    public BoardPresenter(BoardView boardView, String userdId, ListView listView) {
        this.listView = listView;
        this.boardServices = new BoardServiceCloudFireStore();
        this.boardModel = new BoardModel(userdId);
        this.boardEventsAdapter = new BoardEventsAdapter(this,boardModel);
        this.boardView = boardView;
        this.addNewEventDialog = NewEventDialog.newInstance("Some Title",this);
    }

    public void deleteEvent(String boardId,String date,String eventId){
        boardServices.deleteEvent(boardId,date,eventId);
    }

    public void updateDashBoardReport(DashBoardReport dashBoardReport){
        boardView.updateBoardHeader(dashBoardReport.getName(),dashBoardReport.getSpent());
        boardModel.updateReport(dashBoardReport);
        boardEventsAdapter.notifyDataSetChanged();
    }

    public void showDashBoardReport(String boardId) {
        EventListener boardListener = new BoardListener(this);
        boardServices.getBoard(boardId,"",boardListener);
    }

    public void showNewEventPopUp(FragmentManager fm, String tag){
        addNewEventDialog.show(fm, "fragment_edit_name");
    }


    public void updateBoardWithEvents(List<MoneyEvent> moneyEvents){
        boardModel.addEvents(moneyEvents);
        //boardView.updateBoardHeader(boardModel.getDate(),boardModel.getBudget(),boardModel.getSpent());
        boardEventsAdapter.notifyDataSetChanged();
    }

    public void saveEvent(MoneyEvent moneyEvent){
        boardServices.registerEvent(moneyEvent);
    }


    public BoardModel getBoardModel() {
        return boardModel;
    }

    public void showAllEventsFromBoard(String boardId) {
        MoneyEventListener moneyEventListener = new MoneyEventListener(this);
        boardServices.showEvents(boardId,new SimpleDateFormat("yyyy-MM").format(new Date()),moneyEventListener);
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void cleanModel() {
        this.boardModel = new BoardModel(boardModel.getUserId());
        this.boardEventsAdapter = new BoardEventsAdapter(this,boardModel);
        this.listView.setAdapter(boardEventsAdapter);
    }

    public void removeEventFromModel(MoneyEvent moneyEvent) {
        this.boardModel.remove(moneyEvent);
        boardServices.updateBoardSpending(moneyEvent,MoneyEventUpdateType.deleteEvent);
        this.boardEventsAdapter.notifyDataSetChanged();

    }
}
