package com.scorpion.dripeditor.AdapterFolder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.scorpion.dripeditor.GROWUP_MyUtils;
import com.scorpion.dripeditor.InterfaceFol.GROWUP_OnMyCommonItem;
import com.scorpion.dripeditor.R;

import java.util.ArrayList;

public class GROWUP_Ad_bg extends RecyclerView.Adapter<GROWUP_Ad_bg.Holder> {
    ArrayList<String> allbg = new ArrayList<>();
    Context cn;
    GROWUP_OnMyCommonItem onMyCommonItem;
    private int selected = 0;

    public GROWUP_Ad_bg(Context context, ArrayList<String> arrayList, GROWUP_OnMyCommonItem gROWUP_OnMyCommonItem) {
        this.cn = context;
        this.allbg = arrayList;
        this.onMyCommonItem = gROWUP_OnMyCommonItem;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(this.cn).inflate(R.layout.growup_ad_bg, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, final int i) {
        if (i == 0) {
            Glide.with(this.cn).load("file:///android_asset/none.png").into(holder.setthumb);
        } else {
            Glide.with(this.cn).load("file:///android_asset/bg/" + this.allbg.get(i)).into(holder.setthumb);
        }
        if (this.selected == i) {
            holder.setselected.setVisibility(0);
        } else {
            holder.setselected.setVisibility(8);
        }
        GROWUP_MyUtils.setsize(this.cn, (View) holder.laymain, 188, 158);
        GROWUP_MyUtils.setmargin(this.cn, holder.laymain, 0, 11, 0, 0);
        holder.setthumb.setBackgroundColor(Color.parseColor("#000000"));
        holder.laymain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                "file:///android_asset/bg/" + GROWUP_Ad_bg.this.allbg.get(i);
                GROWUP_Ad_bg.this.onMyCommonItem.OnMyClick1(i, GROWUP_Ad_bg.this.allbg.get(i));
            }
        });
    }

    public void setselected(int i) {
        this.selected = i;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return this.allbg.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        RelativeLayout laymain;
        public ImageView setselected;
        ImageView setthumb;

        public Holder(View view) {
            super(view);
            this.laymain = (RelativeLayout) view.findViewById(R.id.laymain);
            this.setthumb = (ImageView) view.findViewById(R.id.setthumb);
            this.setselected = (ImageView) view.findViewById(R.id.setselected);
        }
    }
}
