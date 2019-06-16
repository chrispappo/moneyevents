package com.moneyevents2020.app.board;

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

public class BoardEventsAdapter extends BaseAdapter {
    private final BoardPresenter boardPresenter;
    private final BoardModel boardModel;

    public BoardEventsAdapter(BoardPresenter boardPresenter, BoardModel boardModel) {
        this.boardPresenter = boardPresenter;
        this.boardModel = boardModel;
    }

    @Override
    public int getCount() {
        return boardModel.getMoneyEvents().size();
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
            Context context = (Context) boardPresenter.getBoardView();
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.board_list_row,null);
        }
        String eventId = boardModel.getMoneyEvents().get(i).getEventId();
        TextView txt = view.findViewById(R.id.row_label);
        txt.setText(boardModel.getMoneyEvents().get(i).getLabel());

        TextView amount = view.findViewById(R.id.row_amount);
        amount.setText(String.valueOf(boardModel.getMoneyEvents().get(i).getValue()));
        TextView mImageButton = (TextView) view.findViewById(R.id.imageButton);
        mImageButton.setOnClickListener(view1 -> showPopupMenu(view1,eventId,boardModel.getBoardId(),boardModel.getDate()));
        return  view;

    }

    private void showPopupMenu(View view, String eventId, String boardId, String date) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.overflow_menu_example, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(eventId,boardId,date));
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private final String eventId;
        private final String boardId;
        private final String date;
        public MyMenuItemClickListener(String eventId, String boardId, String date) {
            this.eventId = eventId;
            this.boardId = boardId;
            this.date = date;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int itemId = menuItem.getItemId();

            if(itemId == R.id.menu_remove)
            {
                boardPresenter.deleteEvent(boardId,date,eventId);
            }
            return true;
        }
    }

}
