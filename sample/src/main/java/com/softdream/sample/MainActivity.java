package com.softdream.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.softdream.parallaxbacklayout.ParallaxActivityBase;

public class MainActivity extends ParallaxActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackEnable(false);
        setContentView(R.layout.content_main);
        ((TextView)findViewById(R.id.txt_content)).setText("Main Activity");
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }
}
