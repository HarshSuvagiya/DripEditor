package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.scorpion.dripeditor.Adapters.StickercatAdapter;
import com.scorpion.dripeditor.BuildConfig;
import com.scorpion.dripeditor.R;

import java.util.ArrayList;

public class StickerTabActivity extends FragmentActivity
{
    TabLayout tab_layout;
    ViewPager pager;
    ImageView img_back;
    StickercatAdapter adapter;
    DisplayMetrics metrics;
    public static int screenwidth , screenheight,w;
    public static ArrayList<StickerPack> stickerPackList;
    public static final String EXTRA_STICKER_PACK_LIST_DATA = "sticker_pack_list";
    public static final int ADD_PACK = 200;
    public static int chk=0;

    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sticker_tab);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        metrics = getResources().getDisplayMetrics();
        screenheight = metrics.heightPixels;
        screenwidth = metrics.widthPixels;
        w=screenwidth/3;
        stickerPackList = getIntent().getParcelableArrayListExtra(EXTRA_STICKER_PACK_LIST_DATA);

        img_back = (ImageView) findViewById(R.id.img_back);
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new StickercatAdapter(getSupportFragmentManager(), StickerTabActivity.this);
        pager.setAdapter(adapter);

        tab_layout.setupWithViewPager(pager);

        tab_layout.getTabAt(0).select();

        pager.setCurrentItem(0);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        img_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i_back = new Intent(StickerTabActivity.this , BGchangeEditor.class);
                startActivity(i_back);
                finish();
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrollStateChanged(int arg0)
            {
                System.gc();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
                System.gc();
            }

            @Override
            public void onPageSelected(int i)
            {
                System.gc();
                TabLayout.Tab tab = tab_layout.getTabAt(i);
            }
        });


        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                System.gc();
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                System.gc();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                System.gc();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PACK)
        {
            if (resultCode == Activity.RESULT_CANCELED)
            {
                if (data != null)
                {
                    final String validationError = data.getStringExtra("validation_error");
                    if (validationError != null)
                    {
                        if (BuildConfig.DEBUG)
                        {
                            //validation error should be shown to developer only, not users.
//                            BaseActivity.MessageDialogFragment.newInstance(R.string.app_name, validationError).show(getSupportFragmentManager(), "validation error");
                        }
                        Log.e("AddStickerPackActivity", "Validation failed:" + validationError);
                    }
                }

            }
            else if(resultCode == Activity.RESULT_OK)
            {
            }

        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i_back = new Intent(StickerTabActivity.this , BGchangeEditor.class);
        startActivity(i_back);
        finish();

    }
}
