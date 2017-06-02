package com.github.anzewei.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.github.anzewei.sample.basic.BackExtendsBaseActivity;

public class NormalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ((TextView) findViewById(R.id.txt_content)).setText("This activity extends AppCompatActivity and no annotation");
        ((ToggleButton) findViewById(R.id.toggle)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ParallaxHelper.enableParallaxBack(NormalActivity.this);
                else
                    ParallaxHelper.disableParallaxBack(NormalActivity.this);
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

}
