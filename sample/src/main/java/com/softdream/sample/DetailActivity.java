package com.softdream.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.softdream.parallaxbacklayout.ParallaxActivityBase;

public class DetailActivity extends ParallaxActivityBase {

    static int mCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mCount++;
        ((TextView)findViewById(R.id.txt_content)).setText(String.format("%s %s",DetailActivity.class.getSimpleName(),mCount));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCount--;
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }
}
