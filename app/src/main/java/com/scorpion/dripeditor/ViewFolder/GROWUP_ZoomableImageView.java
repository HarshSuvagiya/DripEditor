package com.scorpion.dripeditor.ViewFolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class GROWUP_ZoomableImageView extends ImageView {
    static final int CLICK = 3;
    static final int DRAG = 1;
    static final int NONE = 0;
    static final int ZOOM = 2;
    float bmHeight;
    float bmWidth;
    float bottom;
    Context context;
    float height;
    PointF last = new PointF();
    float[] m;
    ScaleGestureDetector mScaleDetector;
    Matrix matrix = new Matrix();
    float maxScale = 4.0f;
    float minScale = 1.0f;
    int mode = 0;
    float origHeight;
    float origWidth;
    float redundantXSpace;
    float redundantYSpace;
    float right;
    float saveScale = 1.0f;
    PointF start = new PointF();
    float width;

    public GROWUP_ZoomableImageView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        super.setClickable(true);
        this.context = context2;
        this.mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener());
        this.matrix.setTranslate(1.0f, 1.0f);
        this.m = new float[9];
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(new OnTouchListener() {
            /* JADX WARNING: Removed duplicated region for block: B:43:0x0110  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean onTouch(View r9, android.view.MotionEvent r10) {
                /*
                    r8 = this;
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.view.ScaleGestureDetector r9 = r9.mScaleDetector
                    r9.onTouchEvent(r10)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.Matrix r9 = r9.matrix
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float[] r0 = r0.m
                    r9.getValues(r0)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float[] r9 = r9.m
                    r0 = 2
                    r9 = r9[r0]
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float[] r1 = r1.m
                    r2 = 5
                    r1 = r1[r2]
                    android.graphics.PointF r3 = new android.graphics.PointF
                    float r4 = r10.getX()
                    float r5 = r10.getY()
                    r3.<init>(r4, r5)
                    int r4 = r10.getAction()
                    r5 = 1
                    if (r4 == 0) goto L_0x015d
                    r6 = 0
                    if (r4 == r5) goto L_0x0132
                    if (r4 == r0) goto L_0x0066
                    if (r4 == r2) goto L_0x0046
                    r9 = 6
                    if (r4 == r9) goto L_0x0040
                    goto L_0x017b
                L_0x0040:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    r9.mode = r6
                    goto L_0x017b
                L_0x0046:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r9 = r9.last
                    float r1 = r10.getX()
                    float r10 = r10.getY()
                    r9.set(r1, r10)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r9 = r9.start
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r10 = r10.last
                    r9.set(r10)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    r9.mode = r0
                    goto L_0x017b
                L_0x0066:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    int r10 = r10.mode
                    if (r10 == r0) goto L_0x007e
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    int r10 = r10.mode
                    if (r10 != r5) goto L_0x017b
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r10 = r10.saveScale
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r0 = r0.minScale
                    int r10 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
                    if (r10 <= 0) goto L_0x017b
                L_0x007e:
                    float r10 = r3.x
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r0 = r0.last
                    float r0 = r0.x
                    float r10 = r10 - r0
                    float r0 = r3.y
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r2 = r2.last
                    float r2 = r2.y
                    float r0 = r0 - r2
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r2 = r2.origWidth
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r4 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r4 = r4.saveScale
                    float r2 = r2 * r4
                    int r2 = java.lang.Math.round(r2)
                    float r2 = (float) r2
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r4 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r4 = r4.origHeight
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r6 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r6 = r6.saveScale
                    float r4 = r4 * r6
                    int r4 = java.lang.Math.round(r4)
                    float r4 = (float) r4
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r6 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r6 = r6.width
                    r7 = 0
                    int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
                    if (r2 >= 0) goto L_0x00d0
                    float r9 = r1 + r0
                    int r10 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                    if (r10 <= 0) goto L_0x00bf
                L_0x00bd:
                    float r0 = -r1
                    goto L_0x00ce
                L_0x00bf:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r10 = r10.bottom
                    float r10 = -r10
                    int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                    if (r9 >= 0) goto L_0x00ce
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r9 = r9.bottom
                    float r1 = r1 + r9
                    goto L_0x00bd
                L_0x00ce:
                    r10 = 0
                    goto L_0x011f
                L_0x00d0:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r2 = r2.height
                    int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                    if (r2 >= 0) goto L_0x00f1
                    float r0 = r9 + r10
                    int r1 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
                    if (r1 <= 0) goto L_0x00e0
                L_0x00de:
                    float r10 = -r9
                    goto L_0x00ef
                L_0x00e0:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r1 = r1.right
                    float r1 = -r1
                    int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                    if (r0 >= 0) goto L_0x00ef
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r10 = r10.right
                    float r9 = r9 + r10
                    goto L_0x00de
                L_0x00ef:
                    r0 = 0
                    goto L_0x011f
                L_0x00f1:
                    float r2 = r9 + r10
                    int r4 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
                    if (r4 <= 0) goto L_0x00f9
                L_0x00f7:
                    float r10 = -r9
                    goto L_0x0108
                L_0x00f9:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r4 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r4 = r4.right
                    float r4 = -r4
                    int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                    if (r2 >= 0) goto L_0x0108
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r10 = r10.right
                    float r9 = r9 + r10
                    goto L_0x00f7
                L_0x0108:
                    float r9 = r1 + r0
                    int r2 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                    if (r2 <= 0) goto L_0x0110
                L_0x010e:
                    float r0 = -r1
                    goto L_0x011f
                L_0x0110:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r2 = r2.bottom
                    float r2 = -r2
                    int r9 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
                    if (r9 >= 0) goto L_0x011f
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    float r9 = r9.bottom
                    float r1 = r1 + r9
                    goto L_0x010e
                L_0x011f:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.Matrix r9 = r9.matrix
                    r9.postTranslate(r10, r0)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r9 = r9.last
                    float r10 = r3.x
                    float r0 = r3.y
                    r9.set(r10, r0)
                    goto L_0x017b
                L_0x0132:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    r9.mode = r6
                    float r9 = r3.x
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r10 = r10.start
                    float r10 = r10.x
                    float r9 = r9 - r10
                    float r9 = java.lang.Math.abs(r9)
                    int r9 = (int) r9
                    float r10 = r3.y
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r0 = r0.start
                    float r0 = r0.y
                    float r10 = r10 - r0
                    float r10 = java.lang.Math.abs(r10)
                    int r10 = (int) r10
                    r0 = 3
                    if (r9 >= r0) goto L_0x017b
                    if (r10 >= r0) goto L_0x017b
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    r9.performClick()
                    goto L_0x017b
                L_0x015d:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r9 = r9.last
                    float r0 = r10.getX()
                    float r10 = r10.getY()
                    r9.set(r0, r10)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r9 = r9.start
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r10 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.PointF r10 = r10.last
                    r9.set(r10)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    r9.mode = r5
                L_0x017b:
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    android.graphics.Matrix r10 = r9.matrix
                    r9.setImageMatrix(r10)
                    growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                    r9.invalidate()
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.AnonymousClass1.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.bmWidth = (float) bitmap.getWidth();
        this.bmHeight = (float) bitmap.getHeight();
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.width = (float) MeasureSpec.getSize(i);
        this.height = (float) MeasureSpec.getSize(i2);
        float min = Math.min(this.width / this.bmWidth, this.height / this.bmHeight);
        this.matrix.setScale(min, min);
        setImageMatrix(this.matrix);
        this.saveScale = 1.0f;
        this.redundantYSpace = this.height - (this.bmHeight * min);
        this.redundantXSpace = this.width - (min * this.bmWidth);
        this.redundantYSpace /= 2.0f;
        this.redundantXSpace /= 2.0f;
        this.matrix.postTranslate(this.redundantXSpace, this.redundantYSpace);
        float f = this.width;
        float f2 = this.redundantXSpace;
        this.origWidth = f - (f2 * 2.0f);
        float f3 = this.height;
        float f4 = this.redundantYSpace;
        this.origHeight = f3 - (f4 * 2.0f);
        float f5 = this.saveScale;
        this.right = ((f * f5) - f) - ((f2 * 2.0f) * f5);
        this.bottom = ((f3 * f5) - f3) - ((f4 * 2.0f) * f5);
        setImageMatrix(this.matrix);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            GROWUP_ZoomableImageView.this.mode = 2;
            return true;
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x00a7  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x011a  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onScale(ScaleGestureDetector r9) {
            /*
                r8 = this;
                float r0 = r9.getScaleFactor()
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r1 = r1.saveScale
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r2.saveScale
                float r3 = r3 * r0
                r2.saveScale = r3
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.saveScale
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.maxScale
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 <= 0) goto L_0x0028
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r0.maxScale
                r0.saveScale = r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r0 = r0.maxScale
            L_0x0026:
                float r0 = r0 / r1
                goto L_0x003f
            L_0x0028:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.saveScale
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.minScale
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 >= 0) goto L_0x003f
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r0.minScale
                r0.saveScale = r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r0 = r0.minScale
                goto L_0x0026
            L_0x003f:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r1.width
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.saveScale
                float r2 = r2 * r3
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.width
                float r2 = r2 - r3
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.redundantXSpace
                r4 = 1073741824(0x40000000, float:2.0)
                float r3 = r3 * r4
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r5 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r5 = r5.saveScale
                float r3 = r3 * r5
                float r2 = r2 - r3
                r1.right = r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r1.height
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.saveScale
                float r2 = r2 * r3
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.height
                float r2 = r2 - r3
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r3 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r3 = r3.redundantYSpace
                float r3 = r3 * r4
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r5 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r5 = r5.saveScale
                float r3 = r3 * r5
                float r2 = r2 - r3
                r1.bottom = r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r1 = r1.origWidth
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.saveScale
                float r1 = r1 * r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.width
                r3 = 5
                r5 = 2
                r6 = 1065353216(0x3f800000, float:1.0)
                r7 = 0
                int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                if (r1 <= 0) goto L_0x011a
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r1 = r1.origHeight
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.saveScale
                float r1 = r1 * r2
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.height
                int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                if (r1 > 0) goto L_0x00a7
                goto L_0x011a
            L_0x00a7:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r1 = r1.matrix
                float r2 = r9.getFocusX()
                float r9 = r9.getFocusY()
                r1.postScale(r0, r0, r2, r9)
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r9 = r9.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float[] r1 = r1.m
                r9.getValues(r1)
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float[] r9 = r9.m
                r9 = r9[r5]
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float[] r1 = r1.m
                r1 = r1[r3]
                int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r0 >= 0) goto L_0x01a8
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r0 = r0.right
                float r0 = -r0
                int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
                if (r0 >= 0) goto L_0x00e8
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r0 = r0.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.right
                float r9 = r9 + r2
                float r9 = -r9
                r0.postTranslate(r9, r7)
                goto L_0x00f4
            L_0x00e8:
                int r0 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r0 <= 0) goto L_0x00f4
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r0 = r0.matrix
                float r9 = -r9
                r0.postTranslate(r9, r7)
            L_0x00f4:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r9 = r9.bottom
                float r9 = -r9
                int r9 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
                if (r9 >= 0) goto L_0x010c
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r9 = r9.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r0 = r0.bottom
                float r1 = r1 + r0
                float r0 = -r1
                r9.postTranslate(r7, r0)
                goto L_0x01a8
            L_0x010c:
                int r9 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
                if (r9 <= 0) goto L_0x01a8
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r9 = r9.matrix
                float r0 = -r1
                r9.postTranslate(r7, r0)
                goto L_0x01a8
            L_0x011a:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r9 = r9.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r1 = r1.width
                float r1 = r1 / r4
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.height
                float r2 = r2 / r4
                r9.postScale(r0, r0, r1, r2)
                int r9 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r9 >= 0) goto L_0x01a8
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r9 = r9.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float[] r1 = r1.m
                r9.getValues(r1)
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float[] r9 = r9.m
                r9 = r9[r5]
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float[] r1 = r1.m
                r1 = r1[r3]
                int r0 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
                if (r0 >= 0) goto L_0x01a8
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r0 = r0.origWidth
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.saveScale
                float r0 = r0 * r2
                int r0 = java.lang.Math.round(r0)
                float r0 = (float) r0
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r2 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r2 = r2.width
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 >= 0) goto L_0x0185
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r9 = r9.bottom
                float r9 = -r9
                int r9 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
                if (r9 >= 0) goto L_0x0178
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r9 = r9.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r0 = r0.bottom
                float r1 = r1 + r0
                float r0 = -r1
                r9.postTranslate(r7, r0)
                goto L_0x01a8
            L_0x0178:
                int r9 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
                if (r9 <= 0) goto L_0x01a8
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r9 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r9 = r9.matrix
                float r0 = -r1
                r9.postTranslate(r7, r0)
                goto L_0x01a8
            L_0x0185:
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r0 = r0.right
                float r0 = -r0
                int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
                if (r0 >= 0) goto L_0x019c
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r0 = r0.matrix
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r1 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                float r1 = r1.right
                float r9 = r9 + r1
                float r9 = -r9
                r0.postTranslate(r9, r7)
                goto L_0x01a8
            L_0x019c:
                int r0 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r0 <= 0) goto L_0x01a8
                growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView r0 = growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.this
                android.graphics.Matrix r0 = r0.matrix
                float r9 = -r9
                r0.postTranslate(r9, r7)
            L_0x01a8:
                r9 = 1
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: growup.bvb.dripartphotoeditor.ViewFolder.GROWUP_ZoomableImageView.ScaleListener.onScale(android.view.ScaleGestureDetector):boolean");
        }
    }
}
