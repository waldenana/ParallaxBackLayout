package com.github.anzewei.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.appsdream.parallax.ParallaxBack;

@ParallaxBack
public class DetailActivity extends AppCompatActivity {

    private int mCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.content_main);

        mCount = getIntent().getIntExtra("c",1);
        ((TextView) findViewById(R.id.txt_content)).setText(String.format("%s %s", DetailActivity.class.getSimpleName(), mCount));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w("pl", "newConfig" + mCount);
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("c",mCount+1);
        startActivity(intent);
    }
}
