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


import java.text.SimpleDateFormat;
import java.util.Date;

import com.moneyevents2020.app.R;
import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.home.FirstTimePresenter;

public class CreateDashBoardDialog extends DialogFragment {


    private FirstTimePresenter firstTimePresenter;

    public CreateDashBoardDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static CreateDashBoardDialog newInstance(String title, FirstTimePresenter firstTimePresenter) {

        CreateDashBoardDialog frag = new CreateDashBoardDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        frag.setFirstTimePresenter(firstTimePresenter);
        return frag;
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
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        final Button buttonNewEvent = dialogView.findViewById(R.id.new_dashboard_create_btn);
        buttonNewEvent.setOnClickListener(view -> {
            final TextView dashboard_name_txt = dialogView.findViewById(R.id.label_new_dashboard_name);
            DashBoardReport dashboard = new DashBoardReport(null,dashboard_name_txt.getText().toString(),
                    firstTimePresenter.getUserId(),
                    new SimpleDateFormat("yyyy-MM").format(new Date()),
                    0f,
                    0f);
            String boardKey = firstTimePresenter.registerBoard(dashboard);
            firstTimePresenter.showDashBoard(boardKey);
            getDialog().dismiss();
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

