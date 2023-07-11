package com.astarivi.zparcexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.astarivi.zparc.Zparc;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.root_view);

//        new Zparc.Builder(this)
//                .setDuration(4000)
//                .setView(view)
//                .setAnimList(Zparc.ANIM_BLUE_PURPLE)
//                .build()
//                .startAnimation();

        new Zparc.Builder(this)
                .setDuration(4000)
                .setView(view)
                .setAnimColors(Color.parseColor("#7117EA"), Color.parseColor("#EA6060"))
                .build()
                .startAnimation();
    }
}