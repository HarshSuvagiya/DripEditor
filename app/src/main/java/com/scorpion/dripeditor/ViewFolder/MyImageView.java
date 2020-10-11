package com.scorpion.dripeditor.ViewFolder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import androidx.core.view.MotionEventCompat;

public class MyImageView extends View {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId;
    private Drawable mImage;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private ScaleGestureDetector mScaleDetector;
    public float mScaleFactor;

    public MyImageView(Context context) {
        this(context, (AttributeSet) null, 0);
        Drawable drawable = this.mImage;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mImage.getIntrinsicHeight());
    }

    public MyImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MyImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mActivePointerId = -1;
        this.mScaleFactor = 1.0f;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mScaleDetector.onTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        int i = 0;
        if (action == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            this.mLastTouchX = x;
            this.mLastTouchY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (action == 1) {
            this.mActivePointerId = -1;
        } else if (action == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            float x2 = motionEvent.getX(findPointerIndex);
            float y2 = motionEvent.getY(findPointerIndex);
            if (!this.mScaleDetector.isInProgress()) {
                this.mPosX += x2 - this.mLastTouchX;
                this.mPosY += y2 - this.mLastTouchY;
                invalidate();
            }
            this.mLastTouchX = x2;
            this.mLastTouchY = y2;
        } else if (action == 3) {
            this.mActivePointerId = -1;
        } else if (action == 6) {
            int action2 = (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
            if (motionEvent.getPointerId(action2) == this.mActivePointerId) {
                if (action2 == 0) {
                    i = 1;
                }
                this.mLastTouchX = motionEvent.getX(i);
                this.mLastTouchY = motionEvent.getY(i);
                this.mActivePointerId = motionEvent.getPointerId(i);
            }
        }
        return true;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float intrinsicWidth = (float) (this.mImage.getIntrinsicWidth() / 2);
        float intrinsicHeight = (float) (this.mImage.getIntrinsicHeight() / 2);
        canvas.save();
        Log.d("DEBUG", "X: " + this.mPosX + " Y: " + this.mPosY);
        Log.d("DEBUG", "pivotX: " + intrinsicWidth + " pivotY: " + intrinsicHeight);
        canvas.translate(this.mPosX, this.mPosY);
        float f = this.mScaleFactor;
        canvas.scale(f, f, intrinsicWidth, intrinsicHeight);
        this.mImage.draw(canvas);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            MyImageView MyImageView1 = MyImageView.this;
            float unused = MyImageView1.mScaleFactor = MyImageView1.mScaleFactor * scaleGestureDetector.getScaleFactor();
            MyImageView MyImageView11 = MyImageView.this;
            float unused2 = MyImageView11.mScaleFactor = Math.max(0.1f, Math.min(MyImageView11.mScaleFactor, 10.0f));
            MyImageView.this.invalidate();
            return true;
        }
    }
}
