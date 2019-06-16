package com.moneyevents2020.app.board.listeners;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

import com.moneyevents2020.app.board.BoardPresenter;
import com.moneyevents2020.app.domain.DashBoardReport;

public class BoardListener implements EventListener<DocumentSnapshot> {

    private final BoardPresenter boardPresenter;

    public BoardListener(BoardPresenter boardPresenter) {
        this.boardPresenter = boardPresenter;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
           // Log.w(TAG, "Listen failed.", e);
            return;
        }

        if (snapshot != null && snapshot.exists()) {
            boardPresenter.updateDashBoardReport(snapshot.toObject(DashBoardReport.class));
        } else {
            //Log.d(TAG, "Current data: null");
        }
    }
}


