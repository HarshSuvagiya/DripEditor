package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.facebook.ads.AdSize;
import com.scorpion.dripeditor.FBInterstitial;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.R;
import com.simpleimagegallery.ImageDisplay;

public class PreviewImage extends Activity {
    ImageView btndelete;
    ImageView btnshare;
    Context cn = this;
    String path;
    ImageView setimage;
    private com.facebook.ads.AdView adViewfb;
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.preview_page);
        //banner ad
        adViewfb = new com.facebook.ads.AdView(PreviewImage.this, getString(R.string.banner_ad_unit_idfb), AdSize.BANNER_HEIGHT_50);

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
        Glide.with(this.cn).load(this.path).into(this.setimage);
    }

    private void click() {
        this.btnshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new MyUtils(PreviewImage.this.cn);
                MyUtils.shareAnyFile(PreviewImage.this.cn, PreviewImage.this.path);
            }
        });
        this.btndelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final AlertDialog create = new AlertDialog.Builder(PreviewImage.this.cn).create();
                create.setTitle("Delete");
                create.setMessage("Are You Sure ?");
                create.setButton(-2, (CharSequence) "NO", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        create.dismiss();
                    }
                });
                create.setButton(-1, (CharSequence) "YES", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        create.dismiss();
                        MyUtils.deleteFile(PreviewImage.this.cn, PreviewImage.this.path);
                        PreviewImage.this.finish();
                    }
                });
                create.show();
            }
        });
    }

    private void init() {
        this.setimage = (ImageView) findViewById(R.id.setimage);
        this.btnshare = (ImageView) findViewById(R.id.btnshare);
        this.btndelete = (ImageView) findViewById(R.id.btndelete);
        ImageView imageView = (ImageView) findViewById(R.id.btnback);
        MyUtils.setsize(this.cn, (View) imageView, 80, (Boolean) true);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PreviewImage.this.onBackPressed();
            }
        });
    }

    public void onBackPressed() {

        FBInterstitial.getInstance().displayFBInterstitial(PreviewImage.this, new FBInterstitial.FbCallback() {
            public void callbackCall() {
             onBackPressed();
                finish();
            }
        });


    }


}
