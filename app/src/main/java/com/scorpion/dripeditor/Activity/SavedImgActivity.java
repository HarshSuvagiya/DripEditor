package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.FileProvider;

import com.scorpion.dripeditor.BuildConfig;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.R;

import java.io.File;

public class SavedImgActivity extends Activity {

    ImageView btn_share, btn_delete;
    ImageView img_final, btn_back2;
    File f;
    FrameLayout llexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_img);
        img_final = (ImageView) findViewById(R.id.img_final);
        btn_share = (ImageView) findViewById(R.id.btn_share);
        btn_delete = (ImageView) findViewById(R.id.btn_delete);
        btn_back2 = (ImageView) findViewById(R.id.btn_back2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        llexit=(FrameLayout)findViewById(R.id.llexit);


        img_final.setImageBitmap(Utils.finalEditedBitmapImage);

        btn_back2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        btn_share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new MyUtils(SavedImgActivity.this);
                MyUtils.shareAnyFile(SavedImgActivity.this, Utils.file.getAbsolutePath());
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                final Dialog dialog = new Dialog(SavedImgActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.deletedialog);

                dialog.getWindow().setBackgroundDrawable(

                        new ColorDrawable(android.graphics.Color.TRANSPARENT));

                ImageView no = (ImageView) dialog.findViewById(R.id.btnno);
                ImageView ok = (ImageView) dialog.findViewById(R.id.btnyes);


                LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(
                        getResources().getDisplayMetrics().widthPixels * 305/ 1080,
                        getResources().getDisplayMetrics().heightPixels * 122 / 1920);
                paramss.gravity = Gravity.CENTER;
                ok.setLayoutParams(paramss);
                no.setLayoutParams(paramss);

                LinearLayout mainpop = (LinearLayout) dialog.findViewById(R.id.mainpop);

                int w= Utils.getScreenWidth()-100;
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        getResources().getDisplayMetrics().widthPixels * w / 1080,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                mainpop.setLayoutParams(params);


                ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        Utils.file.delete();
                        onBackPressed();

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });

                dialog.show();


            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

                finish();
    }



}
