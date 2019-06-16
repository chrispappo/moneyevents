package com.moneyevents2020.app.board;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.moneyevents2020.app.R;
import com.moneyevents2020.app.domain.BoardAction;

import static com.moneyevents2020.app.home.FirstTimeActivity.BOARD_ACTION;
import static com.moneyevents2020.app.home.FirstTimeActivity.BOARD_ID;

public class BoardActivity extends AppCompatActivity implements BoardView {
    private BoardPresenter boardPresenter;
    private ListView listView;
    private FragmentManager fm;
    private TextView board_header_spent;
    private TextView board_header_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board_header_name = findViewById(R.id.boardName);
        board_header_spent = findViewById(R.id.dashboard_header_budget);
        listView = findViewById(R.id.records_view);
        String userdId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        boardPresenter = new BoardPresenter(this,userdId,listView);
        fm = getSupportFragmentManager();
        findViewById(R.id.eventgen).setOnClickListener(v -> boardPresenter.showNewEventPopUp(fm,"tag"));
    }

    @Override
    public void updateBoardHeader(String name,float val) {
        board_header_spent.setText(String.valueOf(val));
        board_header_name.setText(name);

    }

    @Override
    protected void onStart() {
        Intent intent = getIntent();
        String enumString = intent.getStringExtra(BOARD_ACTION);
        if(BoardAction.create.name().equals(enumString)){
            String boardId = intent.getStringExtra(BOARD_ID);
            boardPresenter.cleanModel();
            boardPresenter.showDashBoardReport(boardId);
            boardPresenter.showAllEventsFromBoard(boardId);
        }

        super.onStart();
    }



}
