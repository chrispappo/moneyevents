package com.moneyevents2020.app.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import com.moneyevents2020.app.board.listeners.MoneyEventListener;
import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.domain.DataType;
import com.moneyevents2020.app.domain.MoneyEvent;
import com.moneyevents2020.app.domain.MoneyEventUpdateType;
import com.moneyevents2020.app.domain.User;
import com.moneyevents2020.app.home.FirstTimePresenter;
import com.moneyevents2020.app.home.listeners.UserBoardListener;

import static android.content.ContentValues.TAG;

public class BoardServiceCloudFireStore implements BoardService {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseMessaging firebaseMessaging =  FirebaseMessaging.getInstance();

    @Override
    public void getBoard(String boardId, String date, EventListener valueEventListener) {
        db.collection(DataType.REPORT.name()).document(boardId).addSnapshotListener(valueEventListener);
    }

    @Override
    public void getAllBoard(String user, UserBoardListener boardEventListner) {
            db.collection(DataType.REPORT.name())
                    .whereArrayContains("users", mAuth.getUid()).get().addOnCompleteListener(boardEventListner);

    }

    @Override
    public void registerEvent(MoneyEvent moneyEvent) {
        DocumentReference eventRef = db.collection(DataType.EVENTS.name() + moneyEvent.getMonth()).document();
        moneyEvent.setEventId(eventRef.getId());
        eventRef.set(moneyEvent)
                .addOnSuccessListener(aVoid -> updateBoardSpending(moneyEvent,MoneyEventUpdateType.newEvent))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
    }

    @Override
    public void showEvents(String boardId, String date, MoneyEventListener moneyEventCallback) {
        db.collection(DataType.EVENTS+date)
                .whereEqualTo("boardId", boardId)
                .addSnapshotListener(moneyEventCallback);
    }

    @Override
    public void updateBoardSpending(MoneyEvent moneyEvent, MoneyEventUpdateType moneyEventUpdateType) {
        final DocumentReference sfDocRef = db.collection(DataType.REPORT.name()).document(moneyEvent.getBoardId());

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentSnapshot snapshot = transaction.get(sfDocRef);

            Float budget = snapshot.get("spent",Float.class);
            if(moneyEventUpdateType.equals(MoneyEventUpdateType.newEvent)){
                budget+=moneyEvent.getValue();
            }else if(moneyEventUpdateType.equals(MoneyEventUpdateType.deleteEvent)){
                budget-=moneyEvent.getValue();
                budget = Math.max(0,budget);
            }

            transaction.update(sfDocRef, "spent", budget);

            return null;
        }).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "Transaction success!");
            if(moneyEventUpdateType.equals(MoneyEventUpdateType.newEvent)){
                db.collection(DataType.EVENTS+moneyEvent.getMonth()).document(moneyEvent.getEventId()).update("validated",true);
            }else if(moneyEventUpdateType.equals(MoneyEventUpdateType.deleteEvent)){
                Log.d(TAG, "delete ok");
            }
        })
        .addOnFailureListener(e -> Log.w(TAG, "Transaction failure.", e));
    }

    @Override
    public String registerBoard(DashBoardReport dashBoardReport) {
        WriteBatch batch = db.batch();

        DocumentReference boardRef = db.collection(dashBoardReport.getCollection()).document();
        dashBoardReport.setBoardId(boardRef.getId());
        batch.set(boardRef, dashBoardReport);
        batch.commit()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));;

        return boardRef.getId();
    }

    @Override
    public String getUserBoardUrl(String userId, String boardKey) {
        return null;
    }



    @Override
    public void deleteEvent(String boardId, String date, String eventId) {
        String key = DataType.EVENTS.name()+date;
        db.collection(key).document(eventId).delete();
    }

    @Override
    public boolean isNewUser() {
        return mAuth.getCurrentUser() == null;
    }

    @Override
    public void registerUser(FirstTimePresenter firstTimePresenter) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            mAuth.signInAnonymously().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.w(TAG, "sucess");

                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "getInstanceId failed", task.getException());
                                        return;
                                    }

                                    // Get new Instance ID token
                                    String token = task.getResult().getToken();
                                    User user = new User(firebaseUser.getUid(),"anonymous",token);
                                    DocumentReference userRef = db.collection(DataType.USERS.name()).document();
                                    userRef.set(user)
                                            .addOnSuccessListener(aVoid -> Log.w(TAG, "user registered"))
                                            .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
                                    firstTimePresenter.initExample();

                                }
                            });
                } else {
                    Log.d(TAG, "auth failed");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "auth failed");

                }
            });
        }
    }

    @Override
    public String getUserId() {
       return mAuth.getCurrentUser().getUid();
    }

    @Override
    public void deleteBoard(String eventId) {
        String key = DataType.REPORT.name();
        db.collection(key).document(eventId).delete();
    }
}
