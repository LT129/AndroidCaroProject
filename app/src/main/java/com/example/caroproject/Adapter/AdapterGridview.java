package com.example.caroproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.example.caroproject.R;

public class AdapterGridview extends BaseAdapter {
    private Context context;
    private int[] board; // Mảng lưu trạng thái các ô (1: người chơi 1, 2: người chơi 2, 0: ô trống)
    private int currentPlayer = 1;

    public AdapterGridview(Context context) {
        this.context = context;
        board = new int[15 * 15]; // Ban đầu, tất cả ô đều trống (0)
    }

    @Override
    public int getCount() {
        return board.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItem = convertView;

        if (gridItem == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            gridItem = inflater.inflate(R.layout.gridview_item, null);
        }

        ImageView imageView = gridItem.findViewById(R.id.gridItem);
        int cellValue = board[position];

        if (cellValue == 1) {
            imageView.setImageResource(R.drawable.player1_piece);
            imageView.setBackgroundResource(R.drawable.empty);
        } else if (cellValue == 2) {
            imageView.setImageResource(R.drawable.player2_piece);
            imageView.setBackgroundResource(R.drawable.empty);
        }
        else if (cellValue == -1) {
            imageView.setImageResource(R.drawable.player1_piece);
            imageView.setBackgroundResource(R.drawable.custom_background_new);
        }
        else if (cellValue == -2) {
            imageView.setImageResource(R.drawable.player2_piece);
            imageView.setBackgroundResource(R.drawable.custom_background_new);
        }
         else {
            imageView.setImageResource(R.drawable.empty);
        }
        return gridItem;
    }

    public void markCellAsPlayer1(int position) {
            board[position] = 1;
            notifyDataSetChanged();
    }

    public void markCellAsPlayer2(int position) {
            board[position] = 2;
            notifyDataSetChanged();
    }
    public void markCellAsPlayer0(int position) {
        board[position] = 0;
        notifyDataSetChanged();
    }
    public void markCellBackground1(int position) {
        board[position] = -1;
        notifyDataSetChanged();
    }
    public void markCellBackground2(int position) {
        board[position] = -2;
        notifyDataSetChanged();
    }
    public boolean isCellEmpty(int position) {
        return board[position] == 0;
    }
}
