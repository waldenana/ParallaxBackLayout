package com.github.anzewei.sample.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.github.anzewei.parallaxbacklayout.ViewDragHelper;
import com.github.anzewei.parallaxbacklayout.widget.ParallaxBackLayout;
import com.github.anzewei.sample.R;

public class BaseActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView mTxtContent;
    private RadioGroup mGroupLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getSimpleName());
        setContentView(R.layout.content_main);
        mTxtContent = (TextView) findViewById(R.id.txt_content);
        mGroupLayout = ((RadioGroup) findViewById(R.id.rgp_layout));
        RadioGroup radioGroup = ((RadioGroup) findViewById(R.id.rgp_orientation));

        ParallaxBackLayout parallaxBackLayout = ParallaxHelper.getParallaxBackLayout(this);
        if (parallaxBackLayout != null) {
            int oritation = parallaxBackLayout.getEdgeFlag();
            switch (oritation) {
                case ViewDragHelper.EDGE_BOTTOM:
                    radioGroup.check(R.id.radio_bottom);
                    break;
                case ViewDragHelper.EDGE_TOP:
                    radioGroup.check(R.id.radio_top);
                    break;
                case ViewDragHelper.EDGE_LEFT:
                    radioGroup.check(R.id.radio_left);
                    break;
                case ViewDragHelper.EDGE_RIGHT:
                    radioGroup.check(R.id.radio_right);
                    break;
            }
            int layout = parallaxBackLayout.getLayoutType();
            switch (layout) {
                case ParallaxBackLayout.LAYOUT_COVER:
                    mGroupLayout.check(R.id.radio_cover);
                    break;
                case ParallaxBackLayout.LAYOUT_PARALLAX:
                    mGroupLayout.check(R.id.radio_parallax);
                    break;
                case ParallaxBackLayout.LAYOUT_SLIDE:
                    mGroupLayout.check(R.id.radio_slide);
                    break;
            }

        } else {
            setChildEnable(mGroupLayout,false);
            radioGroup.check(R.id.radio_none);
        }

        mGroupLayout.setOnCheckedChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_bottom:
                ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(this, true);
                layout.setEdgeFlag(ViewDragHelper.EDGE_BOTTOM);
                layout.setEnableGesture(true);
                break;
            case R.id.radio_left:
                layout = ParallaxHelper.getParallaxBackLayout(this, true);
                layout.setEdgeFlag(ViewDragHelper.EDGE_LEFT);
                layout.setEnableGesture(true);
                break;
            case R.id.radio_top:
                layout = ParallaxHelper.getParallaxBackLayout(this, true);
                layout.setEdgeFlag(ViewDragHelper.EDGE_TOP);
                layout.setEnableGesture(true);
                break;
            case R.id.radio_right:
                layout = ParallaxHelper.getParallaxBackLayout(this, true);
                layout.setEdgeFlag(ViewDragHelper.EDGE_RIGHT);
                layout.setEnableGesture(true);
                break;
            case R.id.radio_none:
                ParallaxHelper.disableParallaxBack(this);
                setChildEnable(mGroupLayout,false);
                break;
            case R.id.radio_cover:
                ParallaxHelper.getParallaxBackLayout(this, true).setLayoutType(ParallaxBackLayout.LAYOUT_COVER);
                break;
            case R.id.radio_parallax:
                ParallaxHelper.getParallaxBackLayout(this, true).setLayoutType(ParallaxBackLayout.LAYOUT_PARALLAX);
                break;
            case R.id.radio_slide:
                ParallaxHelper.getParallaxBackLayout(this, true).setLayoutType(ParallaxBackLayout.LAYOUT_SLIDE);
                break;
        }
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_top:
                startActivity(TopActivity.class);
                break;
            case R.id.btn_bottom:
                startActivity(BottomActivity.class);
                break;
            case R.id.btn_left:
                startActivity(LeftActivity.class);
                break;
            case R.id.btn_right:
                startActivity(RightActivity.class);
                break;
            case R.id.btn_none:
                startActivity(NoneActivity.class);
                break;
        }
    }

    private void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void setChildEnable(ViewGroup group,boolean enable){
        int count  = group.getChildCount();
        for (int i = 0; i < count; i++) {
            group.getChildAt(i).setEnabled(enable);
        }
    }
}
