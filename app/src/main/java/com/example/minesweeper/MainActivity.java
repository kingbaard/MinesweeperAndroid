package com.example.minesweeper;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Paint paint = new Paint();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create Layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
//        mainParams.setMargins(10, 100, 10, 100);
        mainLayout.setGravity(Gravity.BOTTOM);
        mainLayout.setLayoutParams(mainParams);
        DynamicTitle title = new DynamicTitle(this);
        mainLayout.addView(title);

        //Create Buttons
        MenuButton easyButton = new MenuButton(this, "easy", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame("easy");
            }
        });
        mainLayout.addView(easyButton);
        MenuButton mediumButton = new MenuButton(this, "intermediate", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame("intermediate");
            }
        });
        mainLayout.addView(mediumButton);
        MenuButton hardButton = new MenuButton(this, "expert", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame("expert");
            }
        });
        mainLayout.addView(hardButton);

        //Set content view
        setContentView(mainLayout);
    }


    public void startGame(String difficulty){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }
}