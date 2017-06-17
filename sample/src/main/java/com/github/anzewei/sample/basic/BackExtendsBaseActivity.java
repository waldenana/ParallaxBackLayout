package com.github.anzewei.sample.basic;

import android.os.Bundle;

import com.github.anzewei.sample.R;

public class BackExtendsBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }
}
