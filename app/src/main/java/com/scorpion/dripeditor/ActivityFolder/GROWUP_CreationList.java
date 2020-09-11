package com.scorpion.dripeditor.ActivityFolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scorpion.dripeditor.AdapterFolder.GROWUP_Ad_CreationList;
import com.scorpion.dripeditor.GROWUP_MyUtils;
import com.scorpion.dripeditor.GROWUP_RVGridSpacing;
import com.scorpion.dripeditor.InterfaceFol.GROWUP_OnMyCommonItem;
import com.scorpion.dripeditor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GROWUP_CreationList extends Activity {
    private FrameLayout adContainerView;

    GROWUP_Ad_CreationList ad_creationList;
    ArrayList<File> allfile = new ArrayList<>();
    Context cn = this;
    RecyclerView recyclerView;

    private void click() {
    }

    private void resize() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.growup_creation_list);
        init();
        resize();
        click();
        this.ad_creationList = new GROWUP_Ad_CreationList(this.cn, this.allfile, new GROWUP_OnMyCommonItem() {
            public void OnMyClick2(int i, Object obj) {
            }

            public void OnMyClick1(int i, Object obj) {
                GROWUP_CreationList gROWUP_CreationList = GROWUP_CreationList.this;
                gROWUP_CreationList.startActivity(new Intent(gROWUP_CreationList.cn, GROWUP_PreviewPage.class).putExtra("path", ((File) obj).getAbsolutePath()));
            }
        });
        this.recyclerView.setAdapter(this.ad_creationList);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name));
        file.mkdirs();
        this.allfile.clear();
        this.allfile.addAll(new ArrayList(Arrays.asList(file.listFiles())));
        GROWUP_MyUtils.sortArray(this.allfile);
        this.ad_creationList.notifyDataSetChanged();
    }

    private void init() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.cn, 2));
        this.recyclerView.addItemDecoration(new GROWUP_RVGridSpacing(2, (getResources().getDisplayMetrics().widthPixels * 48) / 1080, true));
        ImageView imageView = (ImageView) findViewById(R.id.btnback);
        GROWUP_MyUtils.setsize(this.cn, (View) imageView, 80, (Boolean) true);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GROWUP_CreationList.this.onBackPressed();
            }
        });
    }

}
