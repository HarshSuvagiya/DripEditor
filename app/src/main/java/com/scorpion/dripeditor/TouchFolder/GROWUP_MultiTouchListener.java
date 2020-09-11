package com.scorpion.dripeditor.TouchFolder;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
public class GROWUP_MultiTouchListener implements View.OnTouchListener {
    private static final int INVALID_POINTER_ID = -1;
    public boolean isRotateEnabled = true;
    public boolean isScaleEnabled = true;
    public boolean isTranslateEnabled = true;
    private int mActivePointerId = -1;
    private float mPrevX;
    private float mPrevY;
    private GROWUP_ScaleGestureDetector mScaleGestureDetector = new GROWUP_ScaleGestureDetector(new ScaleGestureListener());
    public float maximumScale = 10.0f;
    public float minimumScale = 0.5f;

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    /* access modifiers changed from: private */
    public static void move(View view, TransformInfo transformInfo) {
        computeRenderOffset(view, transformInfo.pivotX, transformInfo.pivotY);
        adjustTranslation(view, transformInfo.deltaX, transformInfo.deltaY);
        float max = Math.max(transformInfo.minimumScale, Math.min(transformInfo.maximumScale, view.getScaleX() * transformInfo.deltaScale));
        view.setScaleX(max);
        view.setScaleY(max);
        view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[0] - fArr[0];
            float f4 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - f3);
            view.setTranslationY(view.getTranslationY() - f4);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        if (!this.isTranslateEnabled) {
            return true;
        }
        int action = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked() & action;
        int i = 0;
        if (actionMasked == 0) {
            this.mPrevX = motionEvent.getX();
            this.mPrevY = motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (actionMasked == 1) {
            this.mActivePointerId = -1;
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            if (findPointerIndex != -1) {
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);
                if (!this.mScaleGestureDetector.isInProgress()) {
                    adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                }
            }
        } else if (actionMasked == 3) {
            this.mActivePointerId = -1;
        } else if (actionMasked == 6) {
            int i2 = (65280 & action) >> 8;
            if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                if (i2 == 0) {
                    i = 1;
                }
                this.mPrevX = motionEvent.getX(i);
                this.mPrevY = motionEvent.getY(i);
                this.mActivePointerId = motionEvent.getPointerId(i);
            }
        }
        return true;
    }

    public static class Vector2D extends PointF {
        public Vector2D() {
        }

        public Vector2D(float f, float f2) {
            super(f, f2);
        }

        public static float getAngle(Vector2D vector2D, Vector2D vector2D2) {
            vector2D.normalize();
            vector2D2.normalize();
            return (float) ((Math.atan2((double) vector2D2.y, (double) vector2D2.x) - Math.atan2((double) vector2D.y, (double) vector2D.x)) * 57.29577951308232d);
        }

        public void normalize() {
            float sqrt = (float) Math.sqrt((double) ((this.x * this.x) + (this.y * this.y)));
            this.x /= sqrt;
            this.y /= sqrt;
        }
    }

    private class ScaleGestureListener extends GROWUP_ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private Vector2D mPrevSpanVector;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
        }

        public boolean onScaleBegin(View view, GROWUP_ScaleGestureDetector gROWUP_ScaleGestureDetector) {
            this.mPivotX = gROWUP_ScaleGestureDetector.getFocusX();
            this.mPivotY = gROWUP_ScaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(gROWUP_ScaleGestureDetector.getCurrentSpanVector());
            return true;
        }

        public boolean onScale(View view, GROWUP_ScaleGestureDetector gROWUP_ScaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaScale = GROWUP_MultiTouchListener.this.isScaleEnabled ? gROWUP_ScaleGestureDetector.getScaleFactor() : 1.0f;
            float f = 0.0f;
            transformInfo.deltaAngle = GROWUP_MultiTouchListener.this.isRotateEnabled ? Vector2D.getAngle(this.mPrevSpanVector, gROWUP_ScaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = GROWUP_MultiTouchListener.this.isTranslateEnabled ? gROWUP_ScaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            if (GROWUP_MultiTouchListener.this.isTranslateEnabled) {
                f = gROWUP_ScaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = GROWUP_MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = GROWUP_MultiTouchListener.this.maximumScale;
            GROWUP_MultiTouchListener.move(view, transformInfo);
            return false;
        }
    }

    private class TransformInfo {
        public float deltaAngle;
        public float deltaScale;
        public float deltaX;
        public float deltaY;
        public float maximumScale;
        public float minimumScale;
        public float pivotX;
        public float pivotY;

        private TransformInfo() {
        }
    }
}
