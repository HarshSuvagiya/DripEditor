package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdSize;
import com.scorpion.dripeditor.Adapters.MyCreationAdapter;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.RVGridSpacing;
import com.scorpion.dripeditor.Interface.OnMyCommonItem;
import com.scorpion.dripeditor.R;
import com.simpleimagegallery.ImageDisplay;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MyCreationActivity extends Activity {
    private FrameLayout adContainerView;

    MyCreationAdapter ad_creationList;
    ArrayList<File> allfile = new ArrayList<>();
    Context cn = this;
    RecyclerView recyclerView;
    private com.facebook.ads.AdView adViewfb;
    private void click() {
    }

    private void resize() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.creation_list);
        //banner ad
        adViewfb = new com.facebook.ads.AdView(MyCreationActivity.this, getString(R.string.banner_ad_unit_idfb), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.setVisibility(View.VISIBLE);
        // Add the ad view to your activity layout
        adContainer.addView(adViewfb);

        // Request an ad
        adViewfb.loadAd();
        init();
        resize();
        click();
        this.ad_creationList = new MyCreationAdapter(this.cn, this.allfile, new OnMyCommonItem() {
            public void OnMyClick2(int i, Object obj) {
            }

            public void OnMyClick1(int i, Object obj) {
                MyCreationActivity CreationList1 = MyCreationActivity.this;
                CreationList1.startActivity(new Intent(CreationList1.cn, PreviewImage.class).putExtra("path", ((File) obj).getAbsolutePath()));
            }
        });
        this.recyclerView.setAdapter(this.ad_creationList);
    }

    public void onResume() {
        super.onResume();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name));
        file.mkdirs();
        this.allfile.clear();
        this.allfile.addAll(new ArrayList(Arrays.asList(file.listFiles())));
        MyUtils.sortArray(this.allfile);
        this.ad_creationList.notifyDataSetChanged();
    }

    private void init() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.cn, 2));
        this.recyclerView.addItemDecoration(new RVGridSpacing(2, (getResources().getDisplayMetrics().widthPixels * 48) / 1080, true));
        ImageView imageView = (ImageView) findViewById(R.id.btnback);
        MyUtils.setsize(this.cn, (View) imageView, 80, (Boolean) true);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyCreationActivity.this.onBackPressed();
            }
        });
    }

}
