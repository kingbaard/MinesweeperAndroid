package com.example.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class GameView extends View {
    com.example.minesweeper.Game game;
    Paint paint = new Paint();
    String gameMode;
    public GameView(Context context, String gameMode) {
        super(context);
        this.gameMode = gameMode;

        // DONE: Define a GestureDetectorCompat with an onSingleTapUp method
        //      and and onLongPress method.
        //      For each method, notify the game while action was performed with the motion Event.
        //      Don't forget to call invalidate()
        GestureDetectorCompat gestureDetector = new GestureDetectorCompat(context,
                new GestureDetector.SimpleOnGestureListener() {
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        Log.i("Single tap", "(" + e.getX() + ", " + e.getY() + ")");
                        game.handleTap(e);
                        invalidate();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        Log.i("Long press", "(" + e.getX() + ", " + e.getY() + ")");
                        game.handleLongPress(e);
                        invalidate();
                    }
                });


        setOnTouchListener((view, motionEvent) -> {
            // DONE: use your gesture detector here.
            return gestureDetector.onTouchEvent(motionEvent);
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        game = new Game(gameMode, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        game.draw(canvas, paint);
    }
}
