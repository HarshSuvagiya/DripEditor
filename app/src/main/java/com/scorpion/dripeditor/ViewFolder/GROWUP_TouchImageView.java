package com.scorpion.dripeditor.ViewFolder;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class GROWUP_TouchImageView extends ImageView {
    static final int CLICK = 3;
    static final int DRAG = 1;
    static final int NONE = 0;
    static final int ZOOM = 2;
    Context context;
    PointF last = new PointF();
    float[] m;
    ScaleGestureDetector mScaleDetector;
    Matrix matrix;
    float maxScale = 3.0f;
    float minScale = 1.0f;
    int mode = 0;
    int oldMeasuredHeight;
    int oldMeasuredWidth;
    protected float origHeight;
    protected float origWidth;
    float saveScale = 1.0f;
    PointF start = new PointF();
    int viewHeight;
    int viewWidth;

    /* access modifiers changed from: package-private */
    public float getFixDragTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            return 0.0f;
        }
        return f;
    }

    /* access modifiers changed from: package-private */
    public float getFixTrans(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = f2 - f3;
            f5 = 0.0f;
        } else {
            f5 = f2 - f3;
            f4 = 0.0f;
        }
        if (f < f5) {
            return (-f) + f5;
        }
        if (f > f4) {
            return (-f) + f4;
        }
        return 0.0f;
    }

    public GROWUP_TouchImageView(Context context2) {
        super(context2);
        sharedConstructing(context2);
    }

    public GROWUP_TouchImageView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        sharedConstructing(context2);
    }

    private void sharedConstructing(Context context2) {
        super.setClickable(true);
        this.context = context2;
        this.mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener());
        this.matrix = new Matrix();
        this.m = new float[9];
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                GROWUP_TouchImageView.this.mScaleDetector.onTouchEvent(motionEvent);
                PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                int action = motionEvent.getAction();
                if (action == 0) {
                    GROWUP_TouchImageView.this.last.set(pointF);
                    GROWUP_TouchImageView.this.start.set(GROWUP_TouchImageView.this.last);
                    GROWUP_TouchImageView.this.mode = 1;
                } else if (action == 1) {
                    GROWUP_TouchImageView.this.mode = 0;
                    int abs = (int) Math.abs(pointF.x - GROWUP_TouchImageView.this.start.x);
                    int abs2 = (int) Math.abs(pointF.y - GROWUP_TouchImageView.this.start.y);
                    if (abs < 3 && abs2 < 3) {
                        GROWUP_TouchImageView.this.performClick();
                    }
                } else if (action != 2) {
                    if (action == 6) {
                        GROWUP_TouchImageView.this.mode = 0;
                    }
                } else if (GROWUP_TouchImageView.this.mode == 1) {
                    float f = pointF.x - GROWUP_TouchImageView.this.last.x;
                    float f2 = pointF.y - GROWUP_TouchImageView.this.last.y;
                    GROWUP_TouchImageView gROWUP_TouchImageView = GROWUP_TouchImageView.this;
                    float fixDragTrans = gROWUP_TouchImageView.getFixDragTrans(f, (float) gROWUP_TouchImageView.viewWidth, GROWUP_TouchImageView.this.origWidth * GROWUP_TouchImageView.this.saveScale);
                    GROWUP_TouchImageView gROWUP_TouchImageView2 = GROWUP_TouchImageView.this;
                    GROWUP_TouchImageView.this.matrix.postTranslate(fixDragTrans, gROWUP_TouchImageView2.getFixDragTrans(f2, (float) gROWUP_TouchImageView2.viewHeight, GROWUP_TouchImageView.this.origHeight * GROWUP_TouchImageView.this.saveScale));
                    GROWUP_TouchImageView.this.fixTrans();
                    GROWUP_TouchImageView.this.last.set(pointF.x, pointF.y);
                }
                GROWUP_TouchImageView gROWUP_TouchImageView3 = GROWUP_TouchImageView.this;
                gROWUP_TouchImageView3.setImageMatrix(gROWUP_TouchImageView3.matrix);
                GROWUP_TouchImageView.this.invalidate();
                return true;
            }
        });
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            GROWUP_TouchImageView.this.mode = 2;
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0066  */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0076  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onScale(ScaleGestureDetector r5) {
            /*
                r4 = this;
                float r0 = r5.getScaleFactor()
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r1 = r1.saveScale
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r3 = r2.saveScale
                float r3 = r3 * r0
                r2.saveScale = r3
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r2 = r2.saveScale
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r3 = r3.maxScale
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 <= 0) goto L_0x0028
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r2 = r0.maxScale
                r0.saveScale = r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r0 = r0.maxScale
            L_0x0026:
                float r0 = r0 / r1
                goto L_0x003f
            L_0x0028:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r2 = r2.saveScale
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r3 = r3.minScale
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 >= 0) goto L_0x003f
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r2 = r0.minScale
                r0.saveScale = r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r0 = r0.minScale
                goto L_0x0026
            L_0x003f:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r1 = r1.origWidth
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r2 = r2.saveScale
                float r1 = r1 * r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                int r2 = r2.viewWidth
                float r2 = (float) r2
                int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                if (r1 <= 0) goto L_0x0076
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r1 = r1.origHeight
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                float r2 = r2.saveScale
                float r1 = r1 * r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                int r2 = r2.viewHeight
                float r2 = (float) r2
                int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                if (r1 > 0) goto L_0x0066
                goto L_0x0076
            L_0x0066:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                android.graphics.Matrix r1 = r1.matrix
                float r2 = r5.getFocusX()
                float r5 = r5.getFocusY()
                r1.postScale(r0, r0, r2, r5)
                goto L_0x008b
            L_0x0076:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r5 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                android.graphics.Matrix r5 = r5.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                int r1 = r1.viewWidth
                int r1 = r1 / 2
                float r1 = (float) r1
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                int r2 = r2.viewHeight
                int r2 = r2 / 2
                float r2 = (float) r2
                r5.postScale(r0, r0, r1, r2)
            L_0x008b:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView r5 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.this
                r5.fixTrans()
                r5 = 1
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_TouchImageView.ScaleListener.onScale(android.view.ScaleGestureDetector):boolean");
        }
    }

    /* access modifiers changed from: package-private */
    public void fixTrans() {
        this.matrix.getValues(this.m);
        float[] fArr = this.m;
        float f = fArr[2];
        float f2 = fArr[5];
        float fixTrans = getFixTrans(f, (float) this.viewWidth, this.origWidth * this.saveScale);
        float fixTrans2 = getFixTrans(f2, (float) this.viewHeight, this.origHeight * this.saveScale);
        if (fixTrans != 0.0f || fixTrans2 != 0.0f) {
            this.matrix.postTranslate(fixTrans, fixTrans2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        this.viewWidth = MeasureSpec.getSize(i);
        this.viewHeight = MeasureSpec.getSize(i2);
        int i5 = this.oldMeasuredHeight;
        if ((i5 != this.viewWidth || i5 != this.viewHeight) && (i3 = this.viewWidth) != 0 && (i4 = this.viewHeight) != 0) {
            this.oldMeasuredHeight = i4;
            this.oldMeasuredWidth = i3;
            if (this.saveScale == 1.0f) {
                Drawable drawable = getDrawable();
                if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0) {
                    int intrinsicWidth = drawable.getIntrinsicWidth();
                    int intrinsicHeight = drawable.getIntrinsicHeight();
                    Log.d("bmSize", "bmWidth: " + intrinsicWidth + " bmHeight : " + intrinsicHeight);
                    float f = (float) intrinsicWidth;
                    float f2 = (float) intrinsicHeight;
                    float min = Math.min(((float) this.viewWidth) / f, ((float) this.viewHeight) / f2);
                    this.matrix.setScale(min, min);
                    float f3 = (((float) this.viewHeight) - (f2 * min)) / 2.0f;
                    float f4 = (((float) this.viewWidth) - (min * f)) / 2.0f;
                    this.matrix.postTranslate(f4, f3);
                    this.origWidth = ((float) this.viewWidth) - (f4 * 2.0f);
                    this.origHeight = ((float) this.viewHeight) - (f3 * 2.0f);
                    setImageMatrix(this.matrix);
                } else {
                    return;
                }
            }
            fixTrans();
        }
    }
}
