package com.github.anzewei.parallaxbacklayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by zewei on 2015-11-26.
 */
public abstract class ParallaxActivityBase extends AppCompatActivity {
    private ParallaxBackActivityHelper mHelper;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    @NonNull
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    public ParallaxBackLayout getBackLayout() {
        return mHelper.getBackLayout();
    }

    public void setBackEnable(boolean enable) {
        mHelper.setBackEnable(enable);
    }

    public void scrollToFinishActivity() {
        mHelper.scrollToFinishActivity();
    }

    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            scrollToFinishActivity();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        intent.putExtra("plfile",mHelper.getBackLayout().getCacheFile().getAbsolutePath());
        super.startActivityForResult(intent, requestCode, options);
        mHelper.onStartActivity();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mHelper = new ParallaxBackActivityHelper(this);
    }
}
