package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.ads.AdSize;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.R;
import com.simpleimagegallery.ImageDisplay;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropActivity extends Activity {
    ImageView btncrop;
    ImageView btnrotate;
    Context cn = this;
    CropImageView cropImageView;
    LinearLayout layprogress;
    String path;
    Bitmap selectedbit;
    private com.facebook.ads.AdView adViewfb;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.crop_activity);
        //banner ad
        adViewfb = new com.facebook.ads.AdView(CropActivity.this, getString(R.string.banner_ad_unit_idfb), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.setVisibility(View.VISIBLE);
        // Add the ad view to your activity layout
        adContainer.addView(adViewfb);

        // Request an ad
        adViewfb.loadAd();
        this.path = getIntent().getStringExtra("path");
        init();
        click();
        this.cropImageView.post(new Runnable() {
            public void run() {
                Glide.with(CropActivity.this.cn).asBitmap().load(CropActivity.this.path).into(new CustomTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        CropActivity.this.selectedbit = MyUtils.getBitmapResize(CropActivity.this.cn, bitmap, CropActivity.this.cropImageView.getWidth(), CropActivity.this.cropImageView.getHeight());
                        CropActivity.this.cropImageView.setImageBitmap(CropActivity.this.selectedbit);
                    }

                    public void onLoadCleared(Drawable drawable) {
                        Log.e("AAA", "");
                    }
                });
            }
        });
        this.cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            public void onCropImageComplete(CropImageView cropImageView, CropImageView.CropResult cropResult) {
                MyUtils.selectedBit = cropResult.getBitmap();
                CropActivity.this.layprogress.setVisibility(View.GONE);
                CropActivity.this.setResult(-1);
                CropActivity.this.finish();
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
        MyUtils.setLSquare(this.cn, imageView, 80);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CropActivity.this.onBackPressed();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.btnsave);
        imageView2.setVisibility(View.VISIBLE);
        MyUtils.setLSquare(this.cn, imageView2, 80);
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                MyUtils.selectedBit = CropActivity.this.selectedbit;
                CropActivity.this.setResult(-1);
                CropActivity.this.finish();
            }
        });
    }


    private void click() {
        this.btncrop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                CropActivity.this.layprogress.setVisibility(View.VISIBLE);
                CropActivity.this.cropImageView.getCroppedImageAsync();
            }
        });
        this.btnrotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CropActivity.this.cropImageView.rotateImage(90);
            }
        });
    }

}
