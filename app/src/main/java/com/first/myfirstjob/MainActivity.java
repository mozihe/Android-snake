package com.first.myfirstjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        Button start = findViewById(R.id.startButton);
        start.setOnClickListener(v -> startGame());
    }

    public void startGame() {
        setContentView(R.layout.activity_main);
        MyView layout = findViewById(R.id.myView);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) layout.getLayoutParams();
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels - 50;
        params.height = Resources.getSystem().getDisplayMetrics().widthPixels - 50;
        layout.setLayoutParams(params);
    }
}