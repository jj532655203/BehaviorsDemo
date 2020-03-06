package com.fronttcapital.behaviorsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fronttcapital.behaviorsdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void swipeUpShowTitle(View view) {
        startActivity(new Intent(this, SlideUpShowTitleActivity.class));
    }

    public void slideDownShowTitle(View view) {
        startActivity(new Intent(this, SlideDownShowTitleActivity.class));
    }

    public void slideUpShowTabsAndTitle(View view) {
        startActivity(new Intent(this, SlideUpShowTabsAndTitleActivity.class));
    }
}
