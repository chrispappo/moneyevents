package com.moneyevents2020.app.home.listeners;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.home.FirstTimePresenter;

public class BoardEventListner implements ValueEventListener {
    private final FirstTimePresenter firstTimePresenter;

    public BoardEventListner(FirstTimePresenter firstTimePresenter) {
        this.firstTimePresenter = firstTimePresenter;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        List<DashBoardReport> dashBoardReportList = new ArrayList<>();
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            DashBoardReport report = child.getValue(DashBoardReport.class);
            dashBoardReportList.add(report);
        }
        if (!dashBoardReportList.isEmpty()) {
            firstTimePresenter.presentBoards(dashBoardReportList);
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
