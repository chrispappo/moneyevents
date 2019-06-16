package com.moneyevents2020.app.board.listeners;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;

import javax.annotation.Nullable;

import com.moneyevents2020.app.board.BoardPresenter;
import com.moneyevents2020.app.domain.MoneyEvent;

public class MoneyEventListener implements EventListener<QuerySnapshot> {

    final BoardPresenter boardPresenter;

    public MoneyEventListener(BoardPresenter boardPresenter) {
        this.boardPresenter = boardPresenter;
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

        if (e != null) {
            //Log.w(TAG, "listen:error", e);
            return;
        }

        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
            switch (dc.getType()) {
                case ADDED:
                    MoneyEvent moneyEvent = dc.getDocument().toObject(MoneyEvent.class);
                    if(moneyEvent!=null && !moneyEvent.isDeleted() ){
                        boardPresenter.updateBoardWithEvents(Arrays.asList(moneyEvent));
                    }
                    break;
                case MODIFIED:
                    break;
                case REMOVED:
                    MoneyEvent moneyEventDeleted = dc.getDocument().toObject(MoneyEvent.class);
                    if(moneyEventDeleted!=null){
                        Toast.makeText((Context)boardPresenter.getBoardView(), "Event deleted", Toast.LENGTH_SHORT).show();
                        boardPresenter.removeEventFromModel(moneyEventDeleted);
                    }
                    break;
            }
        }

    }
}
