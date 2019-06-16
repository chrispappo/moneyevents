package com.moneyevents2020.app.home.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.moneyevents2020.app.R;
import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.home.FirstTimePresenter;

public class AddFriendDialog extends DialogFragment {


    private FirstTimePresenter firstTimePresenter;
    private DashBoardReport dashBoardReport;
    public AddFriendDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddFriendDialog newInstance(DashBoardReport dashBoardReport, FirstTimePresenter firstTimePresenter) {

        AddFriendDialog frag = new AddFriendDialog();
        Bundle args = new Bundle();
        args.putString("title", "Add someone to "+ dashBoardReport.getName());
        frag.setArguments(args);
        frag.setFirstTimePresenter(firstTimePresenter);
        frag.setDashBoardReport(dashBoardReport);
        return frag;
    }

    public void setDashBoardReport(DashBoardReport dashBoardReport) {
        this.dashBoardReport = dashBoardReport;
    }

    public void setFirstTimePresenter(FirstTimePresenter firstTimePresenter) {
        this.firstTimePresenter = firstTimePresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_dashboard, container);
    }

    @Override
    public void onViewCreated(final View dialogView, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setLayout(10000, 10000);
        super.onViewCreated(dialogView, savedInstanceState);
        String title = getArguments().getString("Search user");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        final Button buttonNewEvent = dialogView.findViewById(R.id.add_friend_btn);
        buttonNewEvent.setOnClickListener(view -> {
            final TextView userId = dialogView.findViewById(R.id.label_search_user_name);
            firstTimePresenter.addFriend(userId,dashBoardReport);
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(1000, 1000);
        window.setGravity(Gravity.CENTER);
    }

}

