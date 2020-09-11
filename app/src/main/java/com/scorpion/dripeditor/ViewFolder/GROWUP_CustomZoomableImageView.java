package com.scorpion.dripeditor.ViewFolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import androidx.core.view.MotionEventCompat;

public class GROWUP_CustomZoomableImageView extends ImageView {
    private static final int INVALID_POINTER_ID = -1;
    private static final String LOG_TAG = "TouchImageView";
    private Paint backgroundPaint;
    private Paint borderPaint;
    private int mActivePointerId;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private ScaleGestureDetector mScaleDetector;
    /* access modifiers changed from: private */
    public float mScaleFactor;
    float pivotPointX;
    float pivotPointY;

    public GROWUP_CustomZoomableImageView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public GROWUP_CustomZoomableImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GROWUP_CustomZoomableImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.pivotPointX = 0.0f;
        this.pivotPointY = 0.0f;
        this.borderPaint = null;
        this.backgroundPaint = null;
        this.mPosX = 0.0f;
        this.mPosY = 0.0f;
        this.mActivePointerId = -1;
        this.mScaleFactor = 1.0f;
        this.mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        this.borderPaint = new Paint();
        this.borderPaint.setARGB(255, 255, 128, 0);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(4.0f);
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setARGB(32, 255, 255, 255);
        this.backgroundPaint.setStyle(Paint.Style.FILL);
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

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0.0f, 0.0f, (float) (getWidth() - 1), (float) (getHeight() - 1), this.borderPaint);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawRect(0.0f, 0.0f, (float) (getWidth() - 1), (float) (getHeight() - 1), this.backgroundPaint);
        if (getDrawable() != null) {
            canvas.save();
            canvas.translate(this.mPosX, this.mPosY);
            Matrix matrix = new Matrix();
            float f = this.mScaleFactor;
            matrix.postScale(f, f, this.pivotPointX, this.pivotPointY);
            canvas.drawBitmap(((BitmapDrawable) getDrawable()).getBitmap(), matrix, (Paint) null);
            canvas.restore();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        this.mPosX = 0.0f;
        this.mLastTouchX = 0.0f;
        this.mPosY = 0.0f;
        this.mLastTouchY = 0.0f;
        float strokeWidth = (float) ((int) this.borderPaint.getStrokeWidth());
        float f = (float) intrinsicWidth;
        float f2 = (float) intrinsicHeight;
        this.mScaleFactor = Math.min((((float) getLayoutParams().width) - strokeWidth) / f, (((float) getLayoutParams().height) - strokeWidth) / f2);
        this.pivotPointX = ((((float) getLayoutParams().width) - strokeWidth) - ((float) ((int) (f * this.mScaleFactor)))) / 2.0f;
        this.pivotPointY = ((((float) getLayoutParams().height) - strokeWidth) - ((float) ((int) (f2 * this.mScaleFactor)))) / 2.0f;
        super.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        this.mPosX = 0.0f;
        this.mLastTouchX = 0.0f;
        this.mPosY = 0.0f;
        this.mLastTouchY = 0.0f;
        float strokeWidth = (float) ((int) this.borderPaint.getStrokeWidth());
        float f = (float) width;
        float f2 = (float) height;
        this.mScaleFactor = Math.min((((float) getLayoutParams().width) - strokeWidth) / f, (((float) getLayoutParams().height) - strokeWidth) / f2);
        this.pivotPointX = ((((float) getLayoutParams().width) - strokeWidth) - ((float) ((int) (f * this.mScaleFactor)))) / 2.0f;
        this.pivotPointY = ((((float) getLayoutParams().height) - strokeWidth) - ((float) ((int) (f2 * this.mScaleFactor)))) / 2.0f;
        super.setImageBitmap(bitmap);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            GROWUP_CustomZoomableImageView gROWUP_CustomZoomableImageView = GROWUP_CustomZoomableImageView.this;
            float unused = gROWUP_CustomZoomableImageView.mScaleFactor = gROWUP_CustomZoomableImageView.mScaleFactor * scaleGestureDetector.getScaleFactor();
            GROWUP_CustomZoomableImageView.this.pivotPointX = scaleGestureDetector.getFocusX();
            GROWUP_CustomZoomableImageView.this.pivotPointY = scaleGestureDetector.getFocusY();
            Log.d(GROWUP_CustomZoomableImageView.LOG_TAG, "mScaleFactor " + GROWUP_CustomZoomableImageView.this.mScaleFactor);
            Log.d(GROWUP_CustomZoomableImageView.LOG_TAG, "pivotPointY " + GROWUP_CustomZoomableImageView.this.pivotPointY + ", pivotPointX= " + GROWUP_CustomZoomableImageView.this.pivotPointX);
            GROWUP_CustomZoomableImageView gROWUP_CustomZoomableImageView2 = GROWUP_CustomZoomableImageView.this;
            float unused2 = gROWUP_CustomZoomableImageView2.mScaleFactor = Math.max(0.05f, gROWUP_CustomZoomableImageView2.mScaleFactor);
            GROWUP_CustomZoomableImageView.this.invalidate();
            return true;
        }
    }
}
