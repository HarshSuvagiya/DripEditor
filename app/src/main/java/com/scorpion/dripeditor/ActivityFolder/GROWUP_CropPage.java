package com.scorpion.dripeditor.ActivityFolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.scorpion.dripeditor.GROWUP_MyUtils;
import com.scorpion.dripeditor.MainActivity;
import com.scorpion.dripeditor.R;
import com.theartofdev.edmodo.cropper.CropImageView;

public class GROWUP_CropPage extends Activity {
    ImageView btncrop;
    ImageView btnrotate;
    Context cn = this;
    CropImageView cropImageView;
    LinearLayout layprogress;
    String path;
    Bitmap selectedbit;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.growup_crop_page);
        this.path = getIntent().getStringExtra("path");
        init();
        resize();
        click();
        this.cropImageView.post(new Runnable() {
            public void run() {
                Glide.with(GROWUP_CropPage.this.cn).asBitmap().load(GROWUP_CropPage.this.path).into(new CustomTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        GROWUP_CropPage.this.selectedbit = GROWUP_MyUtils.getBitmapResize(GROWUP_CropPage.this.cn, bitmap, GROWUP_CropPage.this.cropImageView.getWidth(), GROWUP_CropPage.this.cropImageView.getHeight());
                        GROWUP_CropPage.this.cropImageView.setImageBitmap(GROWUP_CropPage.this.selectedbit);
                    }

                    public void onLoadCleared(Drawable drawable) {
                        Log.e("AAA", "");
                    }
                });
            }
        });
        this.cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            public void onCropImageComplete(CropImageView cropImageView, CropImageView.CropResult cropResult) {
                GROWUP_MyUtils.selectedBit = cropResult.getBitmap();
                GROWUP_CropPage.this.layprogress.setVisibility(View.GONE);
                GROWUP_CropPage.this.setResult(-1);
                GROWUP_CropPage.this.finish();
            }
        });
    }

    private void init() {
        this.cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        this.btnrotate = (ImageView) findViewById(R.id.btnrotate);
        this.btncrop = (ImageView) findViewById(R.id.btncrop);
        this.layprogress = (LinearLayout) findViewById(R.id.layprogress);
        this.layprogress.setVisibility(View.GONE);
        ImageView imageView = (ImageView) findViewById(R.id.btnback);
        GROWUP_MyUtils.setLSquare(this.cn, imageView, 80);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GROWUP_CropPage.this.onBackPressed();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.btnsave);
        imageView2.setVisibility(View.VISIBLE);
        GROWUP_MyUtils.setLSquare(this.cn, imageView2, 80);
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                GROWUP_MyUtils.selectedBit = GROWUP_CropPage.this.selectedbit;
                GROWUP_CropPage.this.setResult(-1);
                GROWUP_CropPage.this.finish();
            }
        });
    }

    private void resize() {
        GROWUP_MyUtils.setLLayout(this.cn, this.btncrop, 466, 180);
        GROWUP_MyUtils.setLLayout(this.cn, this.btnrotate, 466, 180);
        GROWUP_MyUtils.setLLayout(this.cn, this.cropImageView, 988, 1224);
    }

    private void click() {
        this.btncrop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                GROWUP_CropPage.this.layprogress.setVisibility(View.VISIBLE);
                GROWUP_CropPage.this.cropImageView.getCroppedImageAsync();
            }
        });
        this.btnrotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GROWUP_CropPage.this.cropImageView.rotateImage(90);
            }
        });
    }

}
