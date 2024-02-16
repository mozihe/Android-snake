package com.first.myfirstjob;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MyView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private SurfaceHolder holder;
    private int width;
    private int height;
    private int cellSize;
    private int cellNum;
    private Game game;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        initListener();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        drawThread = new DrawThread(holder);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
        cellNum = getResources().getInteger(R.integer.cellNum);
        cellSize = (int) (width - 50) / cellNum;
        game = new Game(cellNum);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void initListener() {
        setOnTouchListener(new View.OnTouchListener() {
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5 && game.getSnake().getDirection().x != 1) {
                                game.setDirection(new Point(-1, 0));
                            }
                            if (offsetX > 5 && game.getSnake().getDirection().x != -1) {
                                game.setDirection(new Point(1, 0));
                            }
                        } else {
                            if (offsetY < -5 && game.getSnake().getDirection().y != 1) {
                                game.setDirection(new Point(0, -1));
                            }
                            if (offsetY > 5 && game.getSnake().getDirection().y != -1) {
                                game.setDirection(new Point(0, 1));
                            }
                        }
                        break;
                }

                return true;
            }
        });
    }



    class DrawThread extends Thread {
        private boolean runFlag = false;
        private SurfaceHolder holder;
        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        public void setRunning(boolean run) {
            runFlag = run;
        }


        @Override
        public void run() {
            while (runFlag) {

                if (game == null) {
                    continue;
                }

                if (game.getScore() > 380)
                {
                    runFlag = false;
                    Activity activity = (Activity) getContext();
                    activity.runOnUiThread(() -> {
                        activity.setContentView(R.layout.win);
                        Button start = activity.findViewById(R.id.restartButtonWin);
                        TextView scoreView = activity.findViewById(R.id.scoreWin);
                        scoreView.setText("Score: " + game.getScore());
                        MainActivity mainActivity = (MainActivity) activity;
                        start.setOnClickListener(v -> mainActivity.startGame());
                    });
                    return;
                }

                long startTime = System.currentTimeMillis();

                game.update();

                if (game.isOver()) {
                    runFlag = false;
                    Activity activity = (Activity) getContext();
                    activity.runOnUiThread(() -> {
                        activity.setContentView(R.layout.gameover);
                        Button start = activity.findViewById(R.id.restartButton);
                        TextView scoreView = activity.findViewById(R.id.scoreGameOver);
                        scoreView.setText("Score: " + game.getScore());
                        MainActivity mainActivity = (MainActivity) activity;
                        start.setOnClickListener(v -> mainActivity.startGame());
                    });
                    return;
                }

                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas(null);
                    draw(canvas);
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }

                long endTime = System.currentTimeMillis();
                long deltaTime = endTime - startTime;
                long delay = 1000 / game.getSnake().getSpeed() - deltaTime;

                if (delay > 0) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }
            }
        }

        private void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setARGB(255, 0, 0, 0);
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRGB(255, 255, 255);
            canvas.drawRect(25, 25, height - 25, width - 25, paint);

            game.draw(canvas, cellSize);

            Activity activity = (Activity) getContext();
            activity.runOnUiThread(() -> {
                TextView scoreView = activity.findViewById(R.id.score);
                scoreView.setText(game.getScore() + "");
            });
        }

    }
}
