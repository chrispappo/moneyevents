package com.moneyevents2020.app.home.listeners;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.moneyevents2020.app.domain.User;
import com.moneyevents2020.app.home.FirstTimePresenter;

public class UserProfilListner implements ValueEventListener {
    private FirstTimePresenter firstTimePresenter;
    private User user;
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (!dataSnapshot.exists()){
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
