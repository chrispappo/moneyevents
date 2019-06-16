package com.moneyevents2020.app.board.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;


import java.util.UUID;

import com.moneyevents2020.app.R;
import com.moneyevents2020.app.board.BoardPresenter;
import com.moneyevents2020.app.domain.MoneyEvent;


public class NewEventDialog extends DialogFragment {
    private BoardPresenter boardPresenter;

    public NewEventDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NewEventDialog newInstance(String title, BoardPresenter boardPresenter) {

        NewEventDialog frag = new NewEventDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        frag.setBoardPresenter(boardPresenter);
        return frag;
    }

    public void setBoardPresenter(BoardPresenter boardPresenter) {
        this.boardPresenter = boardPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_event, container);
    }

    @Override
    public void onViewCreated(final View dialogView, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setLayout(10000, 10000);
        super.onViewCreated(dialogView, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        final Button buttonNewEvent = dialogView.findViewById(R.id.createEventBtn);
        buttonNewEvent.setOnClickListener(view -> {
            final TextView label_txt = dialogView.findViewById(R.id.label_val);
            final TextView value_txt = dialogView.findViewById(R.id.value_val);
            String boardId = boardPresenter.getBoardModel().getBoardId();
            String date = boardPresenter.getBoardModel().getDate();
            MoneyEvent moneyEvent = new MoneyEvent(boardId,
                    date,
                    1,
                    UUID.randomUUID().toString(),
                    boardPresenter.getBoardModel().getUserId(),
                    label_txt.getText().toString(),
                    Float.valueOf(value_txt.getText().toString()),
                    "GBP", System.currentTimeMillis());

            boardPresenter.saveEvent(moneyEvent);
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
