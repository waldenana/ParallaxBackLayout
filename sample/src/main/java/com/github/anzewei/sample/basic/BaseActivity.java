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
    private RadioGroup mGroupLayout;
    private RadioGroup mGroupEdge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getSimpleName());
        setContentView(R.layout.content_main);
        mGroupLayout = ((RadioGroup) findViewById(R.id.rgp_layout));
        mGroupEdge = ((RadioGroup) findViewById(R.id.rgp_edge_size));
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
            setChildEnable(mGroupEdge,false);
            radioGroup.check(R.id.radio_none);
        }

        mGroupLayout.setOnCheckedChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_bottom:
                setEdgeFlag(ViewDragHelper.EDGE_BOTTOM);
                break;
            case R.id.radio_left:
                setEdgeFlag(ViewDragHelper.EDGE_LEFT);
                break;
            case R.id.radio_top:
                setEdgeFlag(ViewDragHelper.EDGE_TOP);
                break;
            case R.id.radio_right:
                setEdgeFlag(ViewDragHelper.EDGE_RIGHT);
                break;
            case R.id.radio_none:
                ParallaxHelper.disableParallaxBack(this);
                setChildEnable(mGroupLayout,false);
                setChildEnable(mGroupEdge,false);
                break;
            case R.id.radio_cover:
                ParallaxHelper.getParallaxBackLayout(this, true).setLayoutType(ParallaxBackLayout.LAYOUT_COVER,null);
                break;
            case R.id.radio_parallax:
                ParallaxHelper.getParallaxBackLayout(this, true).setLayoutType(ParallaxBackLayout.LAYOUT_PARALLAX,null);
                break;
            case R.id.radio_slide:
                ParallaxHelper.getParallaxBackLayout(this, true).setLayoutType(ParallaxBackLayout.LAYOUT_SLIDE,null);
                break;
            case R.id.radio_default:
                ParallaxHelper.getParallaxBackLayout(this, true).setEdgeMode(ParallaxBackLayout.EDGE_MODE_DEFAULT);
                break;
            case R.id.radio_full:
                ParallaxHelper.getParallaxBackLayout(this, true).setEdgeMode(ParallaxBackLayout.EDGE_MODE_FULL);
                break;
        }
    }

    private void setEdgeFlag(int edgeFlag){
        ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(this, true);
        layout.setEdgeFlag(edgeFlag);
        layout.setEnableGesture(true);
        setChildEnable(mGroupLayout,true);
        setChildEnable(mGroupEdge,true);
    }

    @Override
    public void onBackPressed() {
        ParallaxBackLayout layout = ParallaxHelper.getParallaxBackLayout(this,false);
        if (layout!= null){
            layout.scrollToFinishActivity(0);
        }else super.onBackPressed();
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
