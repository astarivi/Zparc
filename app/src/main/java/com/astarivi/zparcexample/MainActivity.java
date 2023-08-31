package com.astarivi.zparcexample;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.astarivi.zparc.Zparc;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // region Make the activity fullscreen

        Window window = getWindow();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(
                    Color.TRANSPARENT
            );

            window.setNavigationBarColor(
                    Color.TRANSPARENT
            );
        }

        // endregion

        View view = findViewById(R.id.root_view);

        new Zparc.Builder(this)
                .setDuration(4000)
                .setView(view)
                .setAnimList(Zparc.ANIM_RED_PURPLE)
                .build()
                .startAnimation();
    }
}