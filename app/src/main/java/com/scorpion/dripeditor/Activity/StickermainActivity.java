package com.scorpion.dripeditor.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;


import com.facebook.ads.AdSize;
import com.scorpion.dripeditor.Adapters.StickerAdapter;
import com.scorpion.dripeditor.R;

import java.io.IOException;
import java.util.ArrayList;


public class StickermainActivity extends Fragment implements OnClickListener {
    public static Bitmap stickerbitmap;
    private ImageView Back;
    private GridView cd_gridview_sticker;
    Button cat_face;
    private StickerAdapter stickadapter;
    private ArrayList<String> stickerList;
    private com.facebook.ads.AdView adViewfb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.stickermain_activity, container, false);

        //banner ad
        adViewfb = new com.facebook.ads.AdView(getActivity(), getActivity().getString(R.string.banner_ad_unit_idfb), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) rootview.findViewById(R.id.banner_container);
        adContainer.setVisibility(View.VISIBLE);
        // Add the ad view to your activity layout
        adContainer.addView(adViewfb);

        // Request an ad
        adViewfb.loadAd();

        this.Back = (ImageView) rootview.findViewById(R.id.Back);
        this.Back.setOnClickListener(this);
        this.cd_gridview_sticker = (GridView) rootview.findViewById(R.id.cd_gridview_sticker);

        this.cat_face = (Button) rootview.findViewById(R.id.cat_face);
        this.cat_face.setOnClickListener(this);


            listFiles("cat_face");
            this.stickadapter = new StickerAdapter(this.stickerList, getActivity());
            this.cd_gridview_sticker.setAdapter(this.stickadapter);
            this.cd_gridview_sticker.setVisibility(View.VISIBLE);
            this.cd_gridview_sticker.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    try {
                        StickermainActivity.stickerbitmap = BitmapFactory.decodeStream(getActivity().getAssets().open((String) StickermainActivity.this.stickerList.get(position)));
                        BGchangeEditor.sticker_bitmap = stickerbitmap;
                        getActivity().setResult(-1);
                        getActivity().finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        return rootview;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                getActivity().finish();
                return;
            case R.id.cat_face:
                listFiles("cat_face");
                this.stickadapter = new StickerAdapter(this.stickerList, getActivity());
                this.cd_gridview_sticker.setAdapter(this.stickadapter);
                this.cd_gridview_sticker.setVisibility(View.VISIBLE);
                this.cd_gridview_sticker.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        try {
                            StickermainActivity.stickerbitmap = BitmapFactory.decodeStream(getActivity().getAssets().open((String) StickermainActivity.this.stickerList.get(position)));
                            BGchangeEditor.sticker_bitmap = stickerbitmap;
                            getActivity().setResult(-1);
                            getActivity().finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return;

            default:
                return;
        }
    }

    private void listFiles(String assestsFolderName) {
        this.stickerList = new ArrayList();
        this.stickerList.clear();
        String[] strArr = new String[0];
        try {
            strArr = getResources().getAssets().list(assestsFolderName);
            if (strArr != null) {
                for (String str : strArr) {
                    this.stickerList.add(assestsFolderName + "/" + str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
