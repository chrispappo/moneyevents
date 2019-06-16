package com.moneyevents2020.app.home.listeners;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.home.FirstTimePresenter;

public class UserBoardListener implements OnCompleteListener<QuerySnapshot> {
    private final FirstTimePresenter firstTimePresenter;

    public UserBoardListener(FirstTimePresenter firstTimePresenter) {
        this.firstTimePresenter = firstTimePresenter;
    }

    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> querySnapshotTask) {
        List<DashBoardReport> dashBoardReportList = new ArrayList<>();

        QuerySnapshot documentSnapshot = querySnapshotTask.getResult();
        for (QueryDocumentSnapshot data : documentSnapshot) {
            DashBoardReport dashBoardReport =  data.toObject(DashBoardReport.class);
            dashBoardReportList.add(dashBoardReport);
        }

        if (!dashBoardReportList.isEmpty()) {
            firstTimePresenter.presentBoards(dashBoardReportList);
        }
    }
}
