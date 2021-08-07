package com.example.minesweeper;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

public class DynamicTitle extends AppCompatTextView {
    ValueAnimator sizeAnimator;
    ValueAnimator colorAnimator;
    int titleSize = 60;
    int titleColor = Color.BLACK;
    public DynamicTitle(Context context) {
        super(context);
        setText(R.string.title);
        setGravity(Gravity.CENTER);
        setTextSize(titleSize);
        setPadding(0,0,0,125);
        sizeAnimator = ValueAnimator.ofInt(50, 60);
        sizeAnimator.setDuration(500);
        sizeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        sizeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        sizeAnimator.addUpdateListener(valueAnimator -> {
                titleSize = (int) valueAnimator.getAnimatedValue();
                setTextSize(titleSize);
                invalidate();
        });
        Log.i("System", "I made it to colorAnimator");
        colorAnimator = ValueAnimator.ofArgb(Color.BLACK, Color.RED);
        colorAnimator.setDuration(500);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.addUpdateListener(valueAnimator -> {
            titleColor = (int) valueAnimator.getAnimatedValue();
            setTextColor(titleColor);
            invalidate();
        });
        sizeAnimator.start();
        colorAnimator.start();
    }

}
