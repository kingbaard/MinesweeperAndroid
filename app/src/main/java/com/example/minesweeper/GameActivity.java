package com.example.minesweeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String gameMode = intent.getStringExtra("difficulty"); // DONE: Retrieve the game mode from the intent;
        GameView gameView = new GameView(this, gameMode);
        setContentView(gameView);
    }
}
