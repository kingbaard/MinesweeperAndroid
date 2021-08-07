package com.example.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class MenuButton extends AppCompatButton {
    public MenuButton(@NonNull Context context, String difficulty, OnClickListener onClickListener) {
        super(context);
        setTextSize(40);
        switch (difficulty) {
            case "easy":
                setText(R.string.easy);
                setBackgroundColor(Color.GREEN);
                break;
            case "intermediate":
                setText(R.string.intermediate);
                setBackgroundColor(Color.GRAY);
                break;
            case "expert":
                setText(R.string.expert);
                setBackgroundColor(Color.RED);
                break;
        }
        setHeight(700);
        setPadding(20, 50, 10, 50);
        setOnClickListener(onClickListener);
    }
}
