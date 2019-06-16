package com.moneyevents2020.app.util;

import com.google.firebase.firestore.EventListener;

import com.moneyevents2020.app.board.listeners.MoneyEventListener;
import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.domain.MoneyEvent;
import com.moneyevents2020.app.domain.MoneyEventUpdateType;
import com.moneyevents2020.app.home.FirstTimePresenter;
import com.moneyevents2020.app.home.listeners.UserBoardListener;

public interface BoardService {
    void getBoard(String boardId, String date, EventListener valueEventListener);

    void getAllBoard(String userId, UserBoardListener boardEventListner);

    void registerEvent(MoneyEvent moneyEvent);

    void updateBoardSpending(MoneyEvent moneyEvent, MoneyEventUpdateType moneyEventUpdateType);

    String registerBoard(DashBoardReport dashBoardReport);

    String getUserBoardUrl(String userId, String boardKey);

    void  showEvents(String boardId, String date, MoneyEventListener moneyEventCallback);

    void deleteEvent(String boardId, String date, String eventId);
    boolean isNewUser();
    void registerUser(FirstTimePresenter firstTimePresenter);

    String getUserId();

    void deleteBoard(String eventId);

}
