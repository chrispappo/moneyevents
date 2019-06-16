package com.moneyevents2020.app.home;

import com.moneyevents2020.app.domain.DashBoardReport;

public interface FirstTimeView {
    void showDashBoard(DashBoardReport dashboard);

    void showDashBoard(String boardId);

    void updateTitle(String money_dashboards);

}
