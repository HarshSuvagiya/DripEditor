package com.scorpion.dripeditor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.scorpion.dripeditor.Activity.MyCreationActivity;
import com.scorpion.dripeditor.Activity.CropActivity;
import com.scorpion.dripeditor.Activity.DripEditorActivity;
import com.simpleimagegallery.MainActivitygallery;

import static com.scorpion.dripeditor.MyUtils.dripeffect;
import static com.scorpion.dripeditor.MyUtils.selectedBit;

public class MainActivity extends Activity {

    ImageView btnstart,btnbgchanger,btnmycreation;
    Context cn = this;

    String[] PERMISSIONS = new String[]{ "android.permission.READ_EXTERNAL_STORAGE" ,
            "android.permission.WRITE_EXTERNAL_STORAGE" ,
            "android.permission.INTERNET" ,
            "android.permission.ACCESS_NETWORK_STATE" ,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        btnstart=findViewById(R.id.btnstart);
        btnbgchanger=findViewById(R.id.btnbgchanger);
        btnmycreation=findViewById(R.id.btnmycreation);
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dripeffect=true;
                startActivity(new Intent(MainActivity.this, MainActivitygallery.class));

            }
        });

        btnbgchanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dripeffect=false;
                startActivity(new Intent(MainActivity.this, MainActivitygallery.class));

            }
        });

        btnmycreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyCreationActivity.class));

            }
        });



    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (!(Build.VERSION.SDK_INT < 23 || context == null || permissions == null)) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 10 && i2 == -1 && intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                String realPathFromURI = MyUtils.getRealPathFromURI(this.cn, data);
                if (realPathFromURI != null) {
                    Log.e("AAA", "Path : " + realPathFromURI);
                    startActivityForResult(new Intent(this.cn, CropActivity.class).putExtra("path", realPathFromURI), 11);
                    return;
                }
                MyUtils.Toast(this.cn, getResources().getString(R.string.select_another_image));
                return;
            }
            MyUtils.Toast(this.cn, getResources().getString(R.string.select_another_image));
        } else if (i != 11 || i2 != -1) {
        } else {
            if (selectedBit != null) {
                startActivity(new Intent(this.cn, DripEditorActivity.class));
            } else {
                MyUtils.Toast(this.cn, getResources().getString(R.string.select_another_image));
            }
        }
    }

}