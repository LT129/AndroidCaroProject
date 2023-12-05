package com.example.caroproject;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caroproject.Adapter.AdapterGridview;
import com.example.caroproject.Adapter.CaroCenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InGameAIFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InGameAIFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InGameAIFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InGameAIFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InGameAIFragment newInstance(String param1, String param2) {
        InGameAIFragment fragment = new InGameAIFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private Button btnTop, btnBottom, btnBack, btnHome;
    private CountDownTimer countDownTimer;
    private int sizeBoard;
    private int times;
    private Bundle bundle;
    private int currentPlayer, countAI=0, countPlayer=0;
    private int bestMovePosition=0;
    private LinearLayout linearLayout;
    private float offsetX=0, offsetY=0;
    private CaroCenter caroCenter=new CaroCenter();
    private GridView gridView;
    private int[] savePlayerPosition, saveAIPosition;
    private boolean gameOver;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 1.0f;
    private static final float MIN_SCALE = 1.0f;
    private static final float MAX_SCALE = 3.0f;
    private int[][] board;  // Bảng lưu trạng thái của ô cờ
    private AIPlayer aiPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Bundle args = getArguments();
        if (args != null) {
            // Lấy dữ liệu từ Bundle
            sizeBoard = args.getInt("sizeBoard");
            times=args.getInt("time");
        }
        // Tạo một Bundle để chứa dữ liệu
        bundle = new Bundle();
        // Đặt dữ liệu vào Bundle, ví dụ:
        bundle.putInt("sizeBoard", sizeBoard);
        bundle.putInt("time", times);
    }

    private void initializeBoard(View v) {
        board = new int[sizeBoard][sizeBoard];  // Bảng sizeBoardxsizeBoard
        savePlayerPosition=new int[sizeBoard*sizeBoard/2];
        saveAIPosition=new int[sizeBoard*sizeBoard/2];
        // Khởi tạo tất cả ô cờ là trống
        for (int i = 0; i < sizeBoard; i++) {
            for (int j = 0; j < sizeBoard; j++) {
                board[i][j] = 0;
            }
        }
        currentPlayer = 1;
        gameOver = false;

        TextView txtWatch=v.findViewById(R.id.txtBottomInGameAI);
        startCountdownTimer(times, txtWatch, v);
        ImageView imgBottom = v.findViewById(R.id.imgBottomInGameAI);
        imgBottom.setBackgroundResource(R.drawable.custom_picture2);
    }

    // Hàm xử lý sự kiện khi người chơi đánh vào một ô cờ
    public void onCellClicked(int position, View v, AdapterGridview adapter) {
        if (!gameOver) {
            TextView txtWatch1=v.findViewById(R.id.txtTopInGameAI);
            TextView txtWatch2=v.findViewById(R.id.txtBottomInGameAI);
            ImageView imgBottom=v.findViewById(R.id.imgBottomInGameAI);
            ImageView imgTop=v.findViewById(R.id.imgTopInGameAI);
            int row = position / sizeBoard; // Lấy hàng dựa trên vị trí ô
            int col = position % sizeBoard; // Lấy cột dựa trên vị trí ô
            // Kiểm tra nếu ô đã được đánh
            if (adapter.isCellEmpty(position)) {
                // Đánh dấu ô và thay đổi hình ảnh
                if (currentPlayer == 1) {
                    countDownTimer.cancel();
                    board[row][col]=1;
                    adapter.markCellAsPlayer1(position);
                    startCountdownTimer(times, txtWatch1, v);
                    imgBottom.setBackgroundResource(R.drawable.custom_picture);
                    imgTop.setBackgroundResource(R.drawable.custom_picture2);
                }
                if (currentPlayer == 2) {
                     // Chuyển vị trí tốt nhất thành chỉ mục của ô cờ
                    countDownTimer.cancel();
                    board[row][col]=2;
                    //adapter.markCellAsPlayer2(position);
                    adapter.markCellBackground2(bestMovePosition);
                    if(countAI>0) {
                        adapter.markCellAsPlayer2(saveAIPosition[countAI - 1]);
                    }
                    startCountdownTimer(times, txtWatch2, v);
                    imgTop.setBackgroundResource(R.drawable.custom_picture);
                    imgBottom.setBackgroundResource(R.drawable.custom_picture2);
                }

                // Kiểm tra thắng
                if (caroCenter.checkWin(currentPlayer, sizeBoard,board)) {
                    gameOver = true;
                    countDownTimer.cancel();
                    showWinDialog(currentPlayer, v);
                }
                else{
                    currentPlayer = (currentPlayer == 1) ? 2 : 1;
                    if (currentPlayer==1){
                        return;
                    }
                    int[] bestMove = aiPlayer.findBestMove(board, position, bestMovePosition);
                    bestMovePosition = bestMove[0] * sizeBoard + bestMove[1];
                    onCellClicked(bestMovePosition, v, adapter);
                }
            } else {
                // Ô đã được đánh, xử lý theo ý của bạn (ví dụ: thông báo ô đã được đánh)
                Toast.makeText(requireContext(), "Cell already marked", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Hàm hiển thị thông báo thắng
    private void showWinDialog(int player, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        if(player==1) {
            builder.setTitle("Player" + " wins!");
            builder.setMessage("Congratulations, Player!");
        }
        else{
            builder.setTitle("AI" + " wins!");
            builder.setMessage("Congratulations, AI!");
        }

        builder.setCancelable(false);
        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickNew(v);
            }
        });
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickBack(v);
            }
        });
        builder.show();
    }


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
                if(times!=-1) {
                    if (txtWatch.getText().toString().equals("00:00")) {
                        gameOver = true;
                    }
                    if (currentPlayer == 1) {
                        currentPlayer = 2;
                    } else currentPlayer = 1;
                    {
                        showWinDialog(currentPlayer, v);
                    }
                }
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
    float xOriginal,yOriginal;
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_SCALE, Math.min(scaleFactor, MAX_SCALE));

            ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(gridView, "scaleX", scaleFactor);
            ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(gridView, "scaleY", scaleFactor);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleAnimatorX, scaleAnimatorY);

            int duration=1000;
            if(scaleFactor==1){
                duration=0;
            }
            animatorSet.setDuration(duration);
            animatorSet.start();

            return true;
        }
    }
    public void onClickNew(View v){
        countDownTimer.cancel();
        NavController navController=Navigation.findNavController(v);
        navController.navigate(R.id.action_inGameAIFragment_self, bundle);
    }
    public void onClickBack(View v){
        countDownTimer.cancel();
        getActivity().getOnBackPressedDispatcher().onBackPressed();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_game_a_i, container, false);
        scaleGestureDetector = new ScaleGestureDetector(requireContext(), new InGameAIFragment.ScaleListener());

        aiPlayer = new AIPlayer(2,4, sizeBoard);
        gridView = view.findViewById(R.id.gridViewAI);
        linearLayout=view.findViewById(R.id.linearGrid);
        AdapterGridview adapter = new AdapterGridview(view.getContext(),sizeBoard);
        // Khởi tạo bảng cờ và bắt đầu trò chơi
        gridView.setAdapter(adapter);
        gridView.setNumColumns(sizeBoard);
        initializeBoard(view);
        int[] locationOriginal = new int[2];
        gridView.getLocationOnScreen(locationOriginal);

        xOriginal = locationOriginal[0];
        yOriginal = locationOriginal[1];
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Xử lý đánh cờ
                if(adapter.isCellEmpty(position)==false&&countAI>0) {
                    position=savePlayerPosition[countAI-1];
                    countAI--;
                    countPlayer--;
                }
                onCellClicked(position, view, adapter);
                saveAIPosition[countAI++]=bestMovePosition;
                savePlayerPosition[countPlayer++]=position;
            }
        });
        gridView.setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY;

            private boolean isZooming = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(scaleFactor==1){
                    gridView.setTranslationX(xOriginal);
                    gridView.setTranslationY(yOriginal);
                }
                int pointerCount = event.getPointerCount();
                if (pointerCount > 1) {
                    isZooming = true;
                    scaleGestureDetector.onTouchEvent(event);
                } else {
                    if (scaleFactor > 1) {
                        isZooming = false;
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (!isZooming) {
                                    startX = event.getX();
                                    startY = event.getY();
                                }
                                break;

                            case MotionEvent.ACTION_MOVE:
                                if (!isZooming) {
                                    float endX = event.getX();
                                    float endY = event.getY();
                                    float dx = endX - startX;
                                    float dy = endY - startY;

                                    offsetX += dx;
                                    offsetY += dy;

                                    float[] max= caroCenter.calculateMaxOffset(gridView,scaleFactor);

                                    offsetX = Math.min(Math.max(offsetX, -max[0]), max[0]);
                                    offsetY = Math.min(Math.max(offsetY, -max[1]), max[1]);

                                    gridView.setTranslationX(offsetX);
                                    gridView.setTranslationY(offsetY);

                                    startX = endX;
                                    startY = endY;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                if (!isZooming) {
                                    break;
                                }
                        }
                    }
                }
                return false;
            }
        });

        Button btnZoom=view.findViewById(R.id.btnZoomPVE);
        btnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newScale = 1.5f; // Scale X 2 lần
                float newScale2 = 3f; // Scale X 3 lần

                if (scaleFactor < newScale) {
                    scaleFactor = newScale; // Cập nhật scaleFactor
                } else if (scaleFactor < newScale2) {
                    scaleFactor = newScale2; // Cập nhật scaleFactor
                } else {
                    scaleFactor = 1.0f; // Trở về tỷ lệ ban đầu
                }

                // Cập nhật tỷ lệ phóng to của gridView bằng scaleFactor
                gridView.setScaleX(scaleFactor);
                gridView.setScaleY(scaleFactor);

                gridView.setTranslationX(xOriginal);
                gridView.setTranslationY(yOriginal);
            }
        });
        btnBack=view.findViewById(R.id.btnBackOneTurnInGameAI);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countPlayer > 0 && countAI > 0) {
                    countDownTimer.cancel();
                    int c1 = --countPlayer;
                    int c2 = --countAI;
                    adapter.markCellAsPlayer0(savePlayerPosition[c1]);
                    board[savePlayerPosition[c1] / sizeBoard][savePlayerPosition[c1] % sizeBoard] = 0;
                    adapter.markCellAsPlayer0(saveAIPosition[c2]);
                    board[saveAIPosition[c2] / sizeBoard][saveAIPosition[c2] % sizeBoard] = 0;
                    if (countAI > 0) {
                        adapter.markCellBackground2(saveAIPosition[countAI - 1]);
                    }
                }
            }
        });

        btnTop=view.findViewById(R.id.btnTopInGameAI);

        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack(v);
            }
        });
        btnBottom =view.findViewById(R.id.btnBottomInGameAI);
        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNew(v);
            }
        });
        btnHome=view.findViewById(R.id.btnHomeInGameAI);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                NavController navController=Navigation.findNavController(v);
                navController.navigate(R.id.action_inGameAIFragment_to_mainMenuFragment);
            }
        });
        return view;
    }
}