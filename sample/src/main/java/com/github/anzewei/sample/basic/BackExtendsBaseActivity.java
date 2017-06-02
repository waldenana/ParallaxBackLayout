package com.github.anzewei.sample.basic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.github.anzewei.sample.DetailActivity;
import com.github.anzewei.sample.NormalActivity;
import com.github.anzewei.sample.R;

public class BackExtendsBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ((TextView) findViewById(R.id.txt_content)).setText("This activity extends BaseActivity and BaseActivity has Parallax annotation");
        ((ToggleButton) findViewById(R.id.toggle)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ParallaxHelper.enableParallaxBack(BackExtendsBaseActivity.this);
                else
                    ParallaxHelper.disableParallaxBack(BackExtendsBaseActivity.this);
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, NormalActivity.class);
        startActivity(intent);
    }
}
