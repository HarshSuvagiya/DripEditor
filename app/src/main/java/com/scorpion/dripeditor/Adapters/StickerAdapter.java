package com.scorpion.dripeditor.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scorpion.dripeditor.Activity.StickerTabActivity;
import com.scorpion.dripeditor.R;

import java.io.IOException;
import java.util.ArrayList;


public class StickerAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> textureList;

    public class ViewHolder {
        ImageView ivTextureItem;
    }

    public StickerAdapter(ArrayList<String> textureList, Context mContext) {
        this.textureList = textureList;
        this.mContext = mContext;
    }

    public int getCount() {
        return this.textureList.size();
    }

    public Object getItem(int position) {
        return this.textureList.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.sticker_item, null);
        }
        viewHolder.ivTextureItem = (ImageView) convertView.findViewById(R.id.cvTextureItem);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(StickerTabActivity.w-20, StickerTabActivity.w-20);
        viewHolder.ivTextureItem.setLayoutParams(ll);
        try {
            viewHolder.ivTextureItem.setImageBitmap(BitmapFactory.decodeStream(this.mContext.getAssets().open((String) this.textureList.get(position))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
