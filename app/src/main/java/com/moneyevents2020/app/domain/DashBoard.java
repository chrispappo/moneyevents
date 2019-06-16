package com.moneyevents2020.app.domain;

import java.util.List;

public class DashBoard {
     public DashBoardReport DashBoardReport;
     public List<MoneyEvent> MoneyEventList;

    public DashBoard() {
    }

    public com.moneyevents2020.app.domain.DashBoardReport getDashBoardReport() {
        return DashBoardReport;
    }

    public void setDashBoardReport(com.moneyevents2020.app.domain.DashBoardReport dashBoardReport) {
        DashBoardReport = dashBoardReport;
    }

    public List<MoneyEvent> getMoneyEventList() {
        return MoneyEventList;
    }

    public void setMoneyEventList(List<MoneyEvent> moneyEventList) {
        MoneyEventList = moneyEventList;
    }
}
