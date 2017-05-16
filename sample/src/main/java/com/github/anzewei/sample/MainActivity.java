package com.github.anzewei.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
//        setBackEnable(false);
        setContentView(R.layout.content_main);
        ((TextView)findViewById(R.id.txt_content)).setText("Main Activity");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w("pl","newConfig");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
        startActivity(intent);
    }
}
