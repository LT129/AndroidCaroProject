package com.example.caroproject.Adapter;

import android.widget.GridView;



public class CaroCenter {
    public float[] calculateMaxOffset(GridView gridView, float scaleFactor) {
        float gridViewWidth = gridView.getWidth();
        float gridViewHeight = gridView.getHeight();
        float[] max=new float[2];
        max[0] = (float)((gridViewWidth * scaleFactor - gridViewHeight) / 1.93);
        max[1] = (float)((gridViewHeight * scaleFactor - gridViewHeight) / 1.93);
        return max;
    }
    public boolean checkWin(int player, int sizeBoard, int[][] board) {
        int boardSize =sizeBoard;
        // Kiểm tra hàng ngang
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player &&
                        board[row][col + 4] == player) {
                    return true;
                }
            }
        }

        // Kiểm tra hàng dọc
        for (int row = 0; row < boardSize - 4; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player &&
                        board[row + 3][col] == player &&
                        board[row + 4][col] == player) {
                    return true;
                }
            }
        }

        // Kiểm tra đường chéo chính
        for (int row = 0; row < boardSize - 4; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player &&
                        board[row + 4][col + 4] == player) {
                    return true;
                }
            }
        }

        // Kiểm tra đường chéo phụ
        for (int row = 4; row < boardSize; row++) {
            for (int col = 0; col < boardSize - 4; col++) {
                if (board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player &&
                        board[row - 3][col + 3] == player &&
                        board[row - 4][col + 4] == player) {
                    return true;
                }
            }
        }

        return false;
    }
}
