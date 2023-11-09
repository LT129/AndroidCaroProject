package com.example.caroproject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AIPlayer {
    private int player, sizeBoard;
    private Map<String, Integer> transpositionTable;
    private int searchDepth;

    public AIPlayer(int player, int searchDepth, int sizeBoard) {
        this.player = player;
        this.sizeBoard=sizeBoard;
        this.transpositionTable = new HashMap<>();
        this.searchDepth = searchDepth;
    }

    public int[] findBestMove(int[][] board, int position, int bestMovePosition) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;
        int row = position / sizeBoard; // Lấy hàng dựa trên vị trí ô
        int col = position % sizeBoard; // Lấy cột dựa trên vị trí ô
        int row2 = bestMovePosition / sizeBoard; // Lấy hàng dựa trên vị trí ô
        int col2 = bestMovePosition % sizeBoard; // Lấy cột dựa trên vị trí ô
        int score;
        int near = 2020;
        int far =0;
        for (int i = 0; i < sizeBoard; i++) {
            for (int j = 0; j < sizeBoard; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = player;
                    score = minimax(board, searchDepth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = 0;
                    // Tính khoảng cách từ nước đánh hiện tại đến nước cuối cùng của người chơi
                    int distance1 = Math.abs(i - row);
                    int distance2 = Math.abs(j - col);
                    // Điểm số ưu tiên cho các ô gần nước đánh của người chơi
                    if (distance1 == 1&&distance2 == 1) {
                        score += near;
                    }
                    if (distance2 == 1&&distance1 == 0) {
                        score += near+10;
                    }
                    if (distance2 == 0&&distance1 == 1) {
                        score += near+10;
                    }
                    // Tính khoảng cách từ nước đánh hiện tại đến nước cuối cùng của AI
                    int distance12 = Math.abs(i - row2);
                    int distance22 = Math.abs(j - col2);
                    // Điểm số ưu tiên cho các ô gần nước đánh của AI
                    if (distance12 == 1&&distance22 == 1) {
                        score += far;
                    }
                    if (distance22 == 1&&distance12 == 0) {
                        score += far;
                    }
                    if (distance22 == 0&&distance12 == 1) {
                        score += far;
                    }
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(int[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
        String boardString = Arrays.deepToString(board);
        if (transpositionTable.containsKey(boardString)) {
            return transpositionTable.get(boardString);
        }

        int result = evaluate(board);

        if (result != 0 || depth >= searchDepth) {
            return result;
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < sizeBoard; i++) {
                for (int j = 0; j < sizeBoard; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = player;
                        int score = minimax(board, depth + 1, false, alpha, beta);
                        board[i][j] = 0;
                        maxScore = Math.max(maxScore, score);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            transpositionTable.put(boardString, maxScore);
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < sizeBoard; i++) {
                for (int j = 0; j < sizeBoard; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = (player == 1) ? 2 : 1;
                        int score = minimax(board, depth + 1, true, alpha, beta);
                        board[i][j] = 0;
                        minScore = Math.min(minScore, score);
                        beta = Math.min(beta, score);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            transpositionTable.put(boardString, minScore);
            return minScore;
        }
    }

    private int evaluate(int[][] board) {
        int player1 = 1; // Người chơi 1
        int player2 = 2; // Người chơi 2
        int score = 0;
        int five=6000, four = 4000, three = 2000, two = 200, one = 50, zero = 0;
        int size=sizeBoard;
        int countPlayer1;
        int countPlayer2;
        // Đánh giá hàng ngang
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size-4; j++) {
                countPlayer1 = 0;
                countPlayer2 = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i][j + k] == player1) {
                        countPlayer1++;
                    } else if (board[i][j + k] == player2) {
                        countPlayer2++;
                    }
                }

                if (countPlayer1 == 5) {
                    score += five;
                } else if (countPlayer1 == 4) {
                    score += four;
                } else if (countPlayer1 == 3) {
                    score += three;
                } else if (countPlayer1 == 2) {
                    score += two;
                } else if (countPlayer1 == 1) {
                    score += one;
                } else {
                    score += zero;
                }

                if (countPlayer2 == 5) {
                    score -= five;
                } else if (countPlayer2 == 4) {
                    score -= four;
                } else if (countPlayer2 == 3) {
                    score -= three;
                } else if (countPlayer2 == 2) {
                    score -= two;
                } else if (countPlayer2 == 1) {
                    score -= one;
                } else {
                    score -= zero;
                }
            }
        }

        // Đánh giá hàng dọc
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size-4; i++) {
                countPlayer1 = 0;
                countPlayer2 = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j] == player1) {
                        countPlayer1++;
                    } else if (board[i + k][j] == player2) {
                        countPlayer2++;
                    }
                }

                if (countPlayer1 == 5) {
                    score += five;
                } else if (countPlayer1 == 4) {
                    score += four;
                } else if (countPlayer1 == 3) {
                    score += three;
                } else if (countPlayer1 == 2) {
                    score += two;
                } else if (countPlayer1 == 1) {
                    score += one;
                } else {
                    score += zero;
                }

                if (countPlayer2 == 5) {
                    score -= five;
                } else if (countPlayer2 == 4) {
                    score -= four;
                } else if (countPlayer2 == 3) {
                    score -= three;
                } else if (countPlayer2 == 2) {
                    score -= two;
                } else if (countPlayer2 == 1) {
                    score -= one;
                } else {
                    score -= zero;
                }
            }
        }

        // Đánh giá đường chéo (từ trái trên xuống phải dưới)
        for (int i = 0; i < size-4; i++) {
            for (int j = 0; j < size - 4; j++) {
                countPlayer1 = 0;
                countPlayer2 = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] == player1) {
                        countPlayer1++;
                    } else if (board[i + k][j + k] == player2) {
                        countPlayer2++;
                    }
                }

                if (countPlayer1 == 5) {
                    score += five;
                } else if (countPlayer1 == 4) {
                    score += four;
                } else if (countPlayer1 == 3) {
                    score += three;
                } else if (countPlayer1 == 2) {
                    score += two;
                } else if (countPlayer1 == 1) {
                    score += one;
                } else {
                    score += zero;
                }

                if (countPlayer2 == 5) {
                    score -= five;
                } else if (countPlayer2 == 4) {
                    score -= four;
                } else if (countPlayer2 == 3) {
                    score -= three;
                } else if (countPlayer2 == 2) {
                    score -= two;
                } else if (countPlayer2 == 1) {
                    score -= one;
                } else {
                    score -= zero;
                }
            }
        }

        // Đánh giá đường chéo (từ trái dưới lên phải trên)
        for (int i = 4; i < size; i++) {
            for (int j = 0; j < size-4; j++) {
                countPlayer1 = 0;
                countPlayer2 = 0;
                for (int k = 0; k < 5; k++) {
                    if (board[i - k][j + k] == player1) {
                        countPlayer1++;
                    } else if (board[i - k][j + k] == player2) {
                        countPlayer2++;
                    }
                }

                if (countPlayer1 == 5) {
                    score += five;
                } else if (countPlayer1 == 4) {
                    score += four;
                } else if (countPlayer1 == 3) {
                    score += three;
                } else if (countPlayer1 == 2) {
                    score += two;
                } else if (countPlayer1 == 1) {
                    score += one;
                } else {
                    score += zero;
                }

                if (countPlayer2 == 5) {
                    score -= five;
                } else if (countPlayer2 == 4) {
                    score -= four;
                } else if (countPlayer2 == 3) {
                    score -= three;
                } else if (countPlayer2 == 2) {
                    score -= two;
                } else if (countPlayer2 == 1) {
                    score -= one;
                } else {
                    score -= zero;
                }
            }
        }

        return score;
    }

}



