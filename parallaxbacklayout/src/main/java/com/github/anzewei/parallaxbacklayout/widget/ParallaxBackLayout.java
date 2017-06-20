package com.github.anzewei.parallaxbacklayout.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.github.anzewei.parallaxbacklayout.ViewDragHelper;
import com.github.anzewei.parallaxbacklayout.transform.CoverTransform;
import com.github.anzewei.parallaxbacklayout.transform.ITransform;
import com.github.anzewei.parallaxbacklayout.transform.ParallaxTransform;
import com.github.anzewei.parallaxbacklayout.transform.SlideTransform;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.github.anzewei.parallaxbacklayout.ViewDragHelper.EDGE_BOTTOM;
import static com.github.anzewei.parallaxbacklayout.ViewDragHelper.EDGE_RIGHT;
import static com.github.anzewei.parallaxbacklayout.ViewDragHelper.EDGE_TOP;

/**
 * The type Parallax back layout.
 */
public class ParallaxBackLayout extends FrameLayout {

    //region cont
    @IntDef({LAYOUT_COVER, LAYOUT_PARALLAX, LAYOUT_SLIDE, LAYOUT_CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    private @interface LayoutType {
    }

    @IntDef({ViewDragHelper.EDGE_LEFT, EDGE_RIGHT, EDGE_TOP, EDGE_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Edge {
    }

    @IntDef({EDGE_MODE_DEFAULT, EDGE_MODE_FULL})
    @Retention(RetentionPolicy.SOURCE)
    private @interface EdgeMode {
    }

    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;

    private static final int FULL_ALPHA = 255;

    /**
     * Default threshold of scroll
     */
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.5f;

    private static final int OVERSCROLL_DISTANCE = 0;
    private static final int EDGE_LEFT = ViewDragHelper.EDGE_LEFT;

    /**
     * The constant LAYOUT_PARALLAX.
     */
    public static final int LAYOUT_PARALLAX = 1;
    /**
     * The constant LAYOUT_COVER.
     */
    public static final int LAYOUT_COVER = 0;
    /**
     * The constant LAYOUT_SLIDE.
     */
    public static final int LAYOUT_SLIDE = 2;
    public static final int LAYOUT_CUSTOM = -1;
    public static final int EDGE_MODE_FULL = 0;
    public static final int EDGE_MODE_DEFAULT = 1;
    //endregion

    //region field
    /**
     * Threshold of scroll, we will close the activity, when scrollPercent over
     * this value;
     */
    private float mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;

    private Activity mSwipeHelper;
    private Rect mInsets = new Rect();

    private boolean mEnable = true;


    private View mContentView;

    private ViewDragHelper mDragHelper;
    private ParallaxSlideCallback mSlideCallback;
    private ITransform mTransform;
    private int mContentLeft;
    private int mEdgeMode = EDGE_MODE_DEFAULT;

    private int mContentTop;
    private int mLayoutType = LAYOUT_PARALLAX;

    private IBackgroundView mBackgroundView;
    //    private String mThumbFile;
    private Drawable mShadowLeft;

//    private Bitmap mSecondBitmap;
//    private Paint mPaintCache;


    private boolean mInLayout;

    /**
     * Edge being dragged
     */
    private int mTrackingEdge;

    private
    @Edge
    int mEdgeFlag = -1;
    //endregion

    //region super method

    /**
     * Instantiates a new Parallax back layout.
     *
     * @param context the context
     */
    public ParallaxBackLayout(Context context) {
        super(context);
        mDragHelper = ViewDragHelper.create(this, new ViewDragCallback());
        setEdgeFlag(EDGE_LEFT);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        int top = insets.getSystemWindowInsetTop();
        if (mContentView.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams params = (MarginLayoutParams) mContentView.getLayoutParams();
            mInsets.set(params.leftMargin, params.topMargin + top, params.rightMargin, params.bottomMargin);
        }
        applyWindowInset();
        return super.onApplyWindowInsets(insets);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mEnable || !mBackgroundView.canGoBack()) {
            return false;
        }
        try {
            return mDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            // FIXME: handle exception
            // issues #9
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnable || !mBackgroundView.canGoBack()) {
            return false;
        }
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mInLayout = true;
        if (mContentView != null) {
            int cleft = mContentLeft;
            int ctop = mContentTop;
            Log.d(View.VIEW_LOG_TAG, "left = " + left + " top = " + top);
            ViewGroup.LayoutParams params = mContentView.getLayoutParams();
            if (params instanceof MarginLayoutParams) {
                cleft += ((MarginLayoutParams) params).leftMargin;
                ctop += ((MarginLayoutParams) params).topMargin;
            }
            mContentView.layout(cleft, ctop,
                    cleft + mContentView.getMeasuredWidth(),
                    ctop + mContentView.getMeasuredHeight());
        }
        mInLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.d(VIEW_LOG_TAG, "drawChild");
        final boolean drawContent = child == mContentView;
        if (mEnable)
            drawThumb(canvas, child);
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (mEnable && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child);
        }
        return ret;
    }
    //endregion

    //region private method

    /**
     * Set up contentView which will be moved by user gesture
     *
     * @param view
     */
    private void setContentView(View view) {
        mContentView = view;
    }

    private void applyWindowInset() {
        if (mInsets == null)
            return;
        if (mEdgeMode == EDGE_MODE_FULL) {
            mDragHelper.setEdgeSize(Math.max(getWidth(), getHeight()));
        } else if (mEdgeFlag == EDGE_TOP)
            mDragHelper.setEdgeSize(mInsets.top + mDragHelper.getEdgeSizeDefault());
        else if (mEdgeFlag == EDGE_BOTTOM) {
            mDragHelper.setEdgeSize(mInsets.bottom + mDragHelper.getEdgeSizeDefault());
        } else if (mEdgeFlag == ViewDragHelper.EDGE_LEFT) {
            mDragHelper.setEdgeSize(mDragHelper.getEdgeSizeDefault() + mInsets.left);
        } else
            mDragHelper.setEdgeSize(mDragHelper.getEdgeSizeDefault() + mInsets.right);
    }


    /**
     *
     */
    private void drawThumb(Canvas canvas, View child) {
        if (mContentLeft == 0 && mContentTop == 0)
            return;
        int store = canvas.save();
        mTransform.transform(canvas, this, child);
        mBackgroundView.draw(canvas);

        canvas.restoreToCount(store);
    }

    /**
     * draw shadow
     */
    private void drawShadow(Canvas canvas, View child) {
        if (mContentLeft == 0 && mContentTop == 0)
            return;
        if (mEdgeFlag == EDGE_LEFT) {
            mShadowLeft.setBounds(child.getLeft() - mShadowLeft.getIntrinsicWidth(), child.getTop(),
                    child.getLeft(), child.getBottom());
        } else if (mEdgeFlag == EDGE_RIGHT) {
            mShadowLeft.setBounds(child.getRight(), child.getTop(),
                    child.getRight() + mShadowLeft.getIntrinsicWidth(), child.getBottom());
        } else if (mEdgeFlag == EDGE_BOTTOM) {
            mShadowLeft.setBounds(child.getLeft(), child.getBottom(),
                    child.getRight(), child.getBottom() + mShadowLeft.getIntrinsicHeight());
        } else if (mEdgeFlag == EDGE_TOP) {
            mShadowLeft.setBounds(child.getLeft(), child.getTop() - mShadowLeft.getIntrinsicHeight() + getSystemTop(),
                    child.getRight(), child.getTop() + getSystemTop());
        }
        mShadowLeft.draw(canvas);
    }

    //endregion

    //region Public Method

    /**
     * Sets enable gesture.
     *
     * @param enable the enable
     */
    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }

    /**
     * set slide callback
     * @param slideCallback callback
     */
    public void setSlideCallback(ParallaxSlideCallback slideCallback) {
        mSlideCallback = slideCallback;
    }
    /**
     * Set scroll threshold, we will close the activity, when scrollPercent over
     * this value
     *
     * @param threshold the threshold
     */
    public void setScrollThresHold(float threshold) {
        if (threshold >= 1.0f || threshold <= 0) {
            throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
        }
        mScrollThreshold = threshold;
    }

    /**
     * attach to activity
     *
     * @param activity the activity
     */
    public void attachToActivity(Activity activity) {
        mSwipeHelper = activity;

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decor.removeView(decorChild);
        addView(decorChild, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(decorChild);
        decor.addView(this);
    }

    /**
     * set the slide mode fullscreen or default
     *
     * @param mode
     */
    public void setEdgeMode(@EdgeMode int mode) {
        mEdgeMode = mode;
        applyWindowInset();
    }

    /**
     * Scroll out contentView and finish the activity
     *
     * @param duration default 0
     */
    public boolean scrollToFinishActivity(int duration) {
        if (!mEnable || !mBackgroundView.canGoBack()) {
            return false;
        }
        final int childWidth = getWidth();
        int left = 0, top = 0;
        mTrackingEdge = mEdgeFlag;
        switch (mTrackingEdge) {
            case EDGE_LEFT:
                left = childWidth;
                break;
            case EDGE_BOTTOM:
                top = -getHeight();
                break;
            case EDGE_RIGHT:
                left = -getWidth();
                break;
            case EDGE_TOP:
                top = getHeight();
                break;
        }
        if (mDragHelper.smoothSlideViewTo(mContentView, left, top, duration)) {
            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
            return true;
        }
        return false;
    }

    /**
     * shadow drawable
     *
     * @param drawable
     */
    public void setShadowDrawable(Drawable drawable) {
        mShadowLeft = drawable;
    }

    /**
     * Sets background view.
     *
     * @param backgroundView the background view
     */
    public void setBackgroundView(IBackgroundView backgroundView) {
        mBackgroundView = backgroundView;
    }

    public int getEdgeFlag() {
        return mEdgeFlag;
    }

    /**
     * Sets edge flag.
     *
     * @param edgeFlag the edge flag
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setEdgeFlag(@Edge int edgeFlag) {
        if (mEdgeFlag == edgeFlag)
            return;
        mEdgeFlag = edgeFlag;
        mDragHelper.setEdgeTrackingEnabled(edgeFlag);
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
        if (edgeFlag == EDGE_LEFT)
            orientation = GradientDrawable.Orientation.RIGHT_LEFT;
        else if (edgeFlag == EDGE_TOP) {
            orientation = GradientDrawable.Orientation.BOTTOM_TOP;
        } else if (edgeFlag == EDGE_RIGHT)
            orientation = GradientDrawable.Orientation.LEFT_RIGHT;
        else if (edgeFlag == EDGE_BOTTOM)
            orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            mShadowLeft = null;
        }
        if (mShadowLeft == null) {
            int colors[] = {0x66000000, 0x11000000, 0x00000000};
            ShadowDrawable drawable = new ShadowDrawable(orientation, colors);
            drawable.setGradientRadius(90);
            drawable.setSize(50, 50);
            mShadowLeft = drawable;
        } else if (mShadowLeft instanceof ShadowDrawable) {
            ((ShadowDrawable) mShadowLeft).setOrientation(orientation);
        }
        applyWindowInset();
    }

    public int getSystemTop() {
        return mInsets.top;
    }

    public int getSystemLeft() {
        return mInsets.left;
    }

    public int getLayoutType() {
        return mLayoutType;
    }

    /**
     * Sets layout type.
     *
     * @param layoutType the layout type
     */
    public void setLayoutType(@LayoutType int layoutType, ITransform transform) {
        mLayoutType = layoutType;
        switch (layoutType) {
            case LAYOUT_CUSTOM:
                assert transform != null;
                mTransform = transform;
                break;
            case LAYOUT_COVER:
                mTransform = new CoverTransform();
                break;
            case LAYOUT_PARALLAX:
                mTransform = new ParallaxTransform();
                break;
            case LAYOUT_SLIDE:
                mTransform = new SlideTransform();
                break;
        }
    }


    //endregion

    //region class

    private class ViewDragCallback extends ViewDragHelper.Callback {

        private float mScrollPercent;

        @Override
        public boolean tryCaptureView(View view, int pointerId) {
            boolean ret = mDragHelper.isEdgeTouched(mEdgeFlag, pointerId);
            if (ret) {
                mTrackingEdge = mEdgeFlag;
            }
            boolean directionCheck = false;
            if (mEdgeFlag == EDGE_LEFT || mEdgeFlag == EDGE_RIGHT) {
                directionCheck = !mDragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_VERTICAL, pointerId);
            } else if (mEdgeFlag == EDGE_BOTTOM || mEdgeFlag == EDGE_TOP) {
                directionCheck = !mDragHelper
                        .checkTouchSlop(ViewDragHelper.DIRECTION_HORIZONTAL, pointerId);
            }
            return ret & directionCheck;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mEdgeFlag & (EDGE_LEFT | EDGE_RIGHT);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mEdgeFlag & (EDGE_BOTTOM | EDGE_TOP);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if ((mTrackingEdge & EDGE_LEFT) != 0) {
                mScrollPercent = Math.abs((float) (left - mInsets.left)
                        / mContentView.getWidth());
            }
            if ((mTrackingEdge & EDGE_RIGHT) != 0) {
                mScrollPercent = Math.abs((float) (left - mInsets.left)
                        / mContentView.getWidth());
            }
            if ((mTrackingEdge & EDGE_BOTTOM) != 0) {
                mScrollPercent = Math.abs((float) (top - getSystemTop())
                        / mContentView.getHeight());
            }
            if ((mTrackingEdge & EDGE_TOP) != 0) {
                mScrollPercent = Math.abs((float) top
                        / mContentView.getHeight());
            }
            mContentLeft = left;
            mContentTop = top;
            invalidate();
            if (mSlideCallback != null)
                mSlideCallback.onPositionChanged(mScrollPercent);
            if (mScrollPercent >= 0.999f) {
                if (!mSwipeHelper.isFinishing()) {
                    mSwipeHelper.finish();
                    mSwipeHelper.overridePendingTransition(0, 0);
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int childWidth = releasedChild.getWidth();
            final int childHeight = releasedChild.getHeight();

            int left = mInsets.left, top = 0;
            if ((mTrackingEdge & EDGE_LEFT) != 0) {
                left = xvel >= 0 && mScrollPercent > mScrollThreshold ? childWidth + mInsets.left : mInsets.left;
            }
            if ((mTrackingEdge & EDGE_RIGHT) != 0) {
                left = xvel <= 0 && mScrollPercent > mScrollThreshold ? -childWidth + mInsets.left : mInsets.left;
            }
            if ((mTrackingEdge & EDGE_TOP) != 0) {
                top = yvel >= 0 && mScrollPercent > mScrollThreshold ? childHeight : 0;
            }
            if ((mTrackingEdge & EDGE_BOTTOM) != 0) {
                top = yvel <= 0 && mScrollPercent > mScrollThreshold ? -childHeight + getSystemTop() : 0;
            }
            mDragHelper.settleCapturedViewAt(left, top);
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (mSlideCallback != null)
                mSlideCallback.onStateChanged(state);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int ret = mInsets.left;
            if ((mTrackingEdge & EDGE_LEFT) != 0) {
                ret = Math.min(child.getWidth(), Math.max(left, 0));
            } else if ((mTrackingEdge & EDGE_RIGHT) != 0) {
                ret = Math.min(mInsets.left, Math.max(left, -child.getWidth()));
            } else {

            }
            return ret;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int ret = mContentView.getTop();
            if ((mTrackingEdge & EDGE_BOTTOM) != 0) {
                ret = Math.min(0, Math.max(top, -child.getHeight()));
            } else if ((mTrackingEdge & EDGE_TOP) != 0) {
                ret = Math.min(child.getHeight(), Math.max(top, 0));
            }
            return ret;
        }

    }

    /**
     * The interface Background view.
     */
    public interface IBackgroundView {
        /**
         * Draw.
         *
         * @param canvas the canvas
         */
        void draw(Canvas canvas);

        /**
         * Can go back boolean.
         *
         * @return the boolean
         */
        boolean canGoBack();
    }

    public interface ParallaxSlideCallback {
        void onStateChanged(int state);

        void onPositionChanged(float percent);
    }
    //endregion

}
