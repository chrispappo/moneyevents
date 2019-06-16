package com.moneyevents2020.app.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.moneyevents2020.app.R;
import com.moneyevents2020.app.domain.DashBoardReport;
import com.moneyevents2020.app.home.dialogs.AddFriendDialog;

public class BoardListAdapter extends BaseAdapter {
    private final FirstTimePresenter firstTimePresenter;
    private final FirstTimeView firstTimeView;
    private final BoardListModel boardListModel;

    public BoardListAdapter(FirstTimePresenter firstTimePresenter, FirstTimeView firstTimeView, BoardListModel boardListModel) {
        this.firstTimePresenter = firstTimePresenter;
        this.firstTimeView = firstTimeView;
        this.boardListModel = boardListModel;
    }

    @Override
    public int getCount() {
        return boardListModel.getBoards().size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            Context context = (Context)firstTimeView;
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.rows,null);
        }

        TextView mImageButton = (TextView) view.findViewById(R.id.textViewOptions);

        DashBoardReport board = boardListModel.getBoards().get(i);
        mImageButton.setOnClickListener(view1 -> showPopupMenu(view1,board.getBoardId()));

        AddFriendDialog dd = AddFriendDialog.newInstance(board, firstTimePresenter);

        view.setOnClickListener(view1 -> firstTimePresenter.showDashBoard(board.getBoardId()));

        TextView txt = view.findViewById(R.id.row_board_name);
        txt.setText(board.getName());
        view.setTag(board.getBoardId());
        TextView amount = view.findViewById(R.id.row_board_current_month);
        amount.setText(String.valueOf(board.getSpent()));

        return  view;
    }

    private void showPopupMenu(View view, String eventId) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.overflow_menu_example, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(eventId));
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private final String eventId;
        public MyMenuItemClickListener(String eventId) {
            this.eventId = eventId;

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int itemId = menuItem.getItemId();

            if(itemId == R.id.menu_remove)
            {
                firstTimePresenter.deleteBoard(eventId);
            }
            return true;
        }
    }
}
