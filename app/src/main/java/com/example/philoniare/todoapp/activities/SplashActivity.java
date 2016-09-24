package com.example.philoniare.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by philoniare on 9/9/2016.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1800);     // used to display the splash screen and mimic app load behavior
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
        finish();
    }
}
