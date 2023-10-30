package com.example.caroproject;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caroproject.Adapter.AdapterGridview;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InGameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InGameFragment newInstance(String param1, String param2) {
        InGameFragment fragment = new InGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private int currentPlayer;
    private boolean gameOver;
    private int[][] board;  // Bảng lưu trạng thái của ô cờ
    private void initializeBoard(View v) {
        board = new int[15][15];  // Bảng 15x15
        // Khởi tạo tất cả ô cờ là trống
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = 0;
            }
        }
        currentPlayer = 1;
        gameOver = false;

        TextView txtWatch=v.findViewById(R.id.txtBottomInGame);
        startCountdownTimer(46000, txtWatch, v);
        ImageView imgBottom = v.findViewById(R.id.imgBottomInGame);
        imgBottom.setBackgroundResource(R.drawable.custom_picture2);
    }

    // Hàm xử lý sự kiện khi người chơi đánh vào một ô cờ
    public void onCellClicked(int position, View v, AdapterGridview adapter ) {
        if (!gameOver) {
            TextView txtWatch1=v.findViewById(R.id.txtTopInGame);
            TextView txtWatch2=v.findViewById(R.id.txtBottomInGame);
            ImageView imgBottom=v.findViewById(R.id.imgBottomInGame);
            ImageView imgTop=v.findViewById(R.id.imgTopInGame);
            int row = position / 15; // Lấy hàng dựa trên vị trí ô
            int col = position % 15; // Lấy cột dựa trên vị trí ô
            // Kiểm tra nếu ô đã được đánh
            if (adapter.isCellEmpty(position)) {
                // Đánh dấu ô và thay đổi hình ảnh
                if (currentPlayer == 1) {
                    countDownTimer.cancel();
                    board[row][col]=1;
                    adapter.markCellAsPlayer1(position);
                    startCountdownTimer(46000, txtWatch1, v);
                    imgBottom.setBackgroundResource(R.drawable.custom_picture);
                    imgTop.setBackgroundResource(R.drawable.custom_picture2);
                } else if (currentPlayer == 2) {
                    countDownTimer.cancel();
                    board[row][col]=2;
                    adapter.markCellAsPlayer2(position);
                    startCountdownTimer(46000, txtWatch2, v);
                    imgTop.setBackgroundResource(R.drawable.custom_picture);
                    imgBottom.setBackgroundResource(R.drawable.custom_picture2);
                }

                // Kiểm tra thắng
                if (checkWin(currentPlayer)) {
                    gameOver = true;
                    showWinDialog(currentPlayer, v);
                }
                else{
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                }
            } else {
                // Ô đã được đánh, xử lý theo ý của bạn (ví dụ: thông báo ô đã được đánh)
                Toast.makeText(requireContext(), "Cell already marked", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Hàm kiểm tra trạng thái thắng/thua
    public boolean checkWin(int player) {
        int boardSize =15;
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

    // Hàm hiển thị thông báo thắng
    private void showWinDialog(int player, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Player " + player + " wins!");
        builder.setMessage("Congratulations, Player " + player + "!");
        builder.setCancelable(false);
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_inGameFragment_self);
            }
        });
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_inGameFragment_to_gameModeFragment);
            }
        });
        builder.show();
    }
    private Button btnTop, btnBottom;
    private CountDownTimer countDownTimer;

    private void startCountdownTimer(long millisInFuture, TextView txtWatch, View v) {
        // millisInFuture là thời gian đếm ngược theo mili giây

        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Cập nhật TextView với thời gian còn lại
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                seconds = seconds % 60;
                txtWatch.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                // Xử lý khi đếm ngược kết thúc (hết thời gian)
                if(txtWatch.getText().toString().equals("00:00")) {
                    gameOver = true;
                }

                if(currentPlayer==1) {
                    currentPlayer=2;
                }
                else {
                    currentPlayer=1;
                }
                showWinDialog(currentPlayer, v);
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Dừng đồng hồ đếm khi Activity bị hủy để tránh rò rỉ bộ nhớ
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_game, container, false);

        GridView gridView = view.findViewById(R.id.gridView);
        AdapterGridview adapter = new AdapterGridview(view.getContext());
        // Khởi tạo bảng cờ và bắt đầu trò chơi
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Xử lý đánh cờ
                onCellClicked(position, view, adapter);
            }
        });
        initializeBoard(view);

        btnTop=view.findViewById(R.id.btnTopInGame);
        btnBottom =view.findViewById(R.id.btnBottomInGame);
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController=Navigation.findNavController(v);
                navController.navigate(R.id.action_inGameFragment_self);
            }
        });
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController=Navigation.findNavController(v);
                navController.navigate(R.id.action_inGameFragment_to_gameModeFragment);
            }
        });
        return view;
    }
}