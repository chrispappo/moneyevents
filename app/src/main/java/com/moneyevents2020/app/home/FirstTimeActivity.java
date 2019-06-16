package com.moneyevents2020.app.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.moneyevents2020.app.R;
import com.moneyevents2020.app.board.BoardActivity;

import com.moneyevents2020.app.domain.BoardAction;
import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.home.dialogs.CreateDashBoardDialog;


public class FirstTimeActivity extends AppCompatActivity implements FirstTimeView {
    private FirstTimePresenter firstTimePresenter;
    private ListView listView;
    private FragmentManager fm;
    public static final String EXTRA_BOARD_REPORT = "com.example.chris.myapplication.popup.boardReport";
    public static final String BOARD_ACTION = "com.example.chris.myapplication.popup.boardAction";
    public static final String BOARD_ID = "com.example.chris.myapplication.popup.boardId";
    private TextView board_header_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_no_dashboard);
        fm = getSupportFragmentManager();
        listView = findViewById(R.id.board_list);
        listView.setItemsCanFocus(false);
        firstTimePresenter = new FirstTimePresenter(this);
        listView.setAdapter(firstTimePresenter.getBoardListAdapter());
        CreateDashBoardDialog dd = CreateDashBoardDialog.newInstance("Some Title", firstTimePresenter);
        findViewById(R.id.btn_create_board).setOnClickListener(v -> dd.show(fm, "fragment_edit_name"));
        board_header_name = findViewById(R.id.boardName);

        listView.setDivider(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firstTimePresenter.presentBoards();
        firstTimePresenter.clearModel();
    }

    @Override
    public void showDashBoard(DashBoardReport dashboard) {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra(EXTRA_BOARD_REPORT, dashboard);
        intent.putExtra(BOARD_ACTION, BoardAction.create.name());
        startActivity(intent);
    }

    @Override
    public void showDashBoard(String boardId) {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra(BOARD_ID, boardId);
        intent.putExtra(BOARD_ACTION, BoardAction.create.name());
        startActivity(intent);
    }

    @Override
    public void updateTitle(String money_dashboards) {
        board_header_name.setText(money_dashboards);
    }


}
