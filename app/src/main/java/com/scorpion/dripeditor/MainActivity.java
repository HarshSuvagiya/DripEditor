package com.scorpion.dripeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.scorpion.dripeditor.ActivityFolder.GROWUP_CreationList;
import com.scorpion.dripeditor.ActivityFolder.GROWUP_CropPage;
import com.scorpion.dripeditor.ActivityFolder.GROWUP_EditPage;

import static com.scorpion.dripeditor.GROWUP_MyUtils.selectedBit;

public class MainActivity extends Activity {

    Button btnstart,btnmycreation;
    String[] permission = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    Context cn = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnstart=findViewById(R.id.btnstart);
        btnmycreation=findViewById(R.id.btnmycreation);


        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!GROWUP_MyUtils.checkPermission(MainActivity.this, MainActivity.this.permission)) {
                    return;
                }

                GROWUP_MyUtils.pickImagefromGallery(MainActivity.this, 10);
            }
        });

        btnmycreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GROWUP_CreationList.class));
            }
        });



    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 10 && i2 == -1 && intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                String realPathFromURI = GROWUP_MyUtils.getRealPathFromURI(this.cn, data);
                if (realPathFromURI != null) {
                    Log.e("AAA", "Path : " + realPathFromURI);
                    startActivityForResult(new Intent(this.cn, GROWUP_CropPage.class).putExtra("path", realPathFromURI), 11);
                    return;
                }
                GROWUP_MyUtils.Toast(this.cn, getResources().getString(R.string.select_another_image));
                return;
            }
            GROWUP_MyUtils.Toast(this.cn, getResources().getString(R.string.select_another_image));
        } else if (i != 11 || i2 != -1) {
        } else {
            if (selectedBit != null) {
                startActivity(new Intent(this.cn, GROWUP_EditPage.class));
            } else {
                GROWUP_MyUtils.Toast(this.cn, getResources().getString(R.string.select_another_image));
            }
        }
    }

}