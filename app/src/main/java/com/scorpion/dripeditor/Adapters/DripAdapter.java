package com.scorpion.dripeditor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.Interface.OnMyCommonItem;
import com.scorpion.dripeditor.R;

import java.util.ArrayList;

public class DripAdapter extends RecyclerView.Adapter<DripAdapter.Holder> {
    ArrayList<String> alldrip = new ArrayList<>();
    Context cn;
    OnMyCommonItem onMyCommonItem;
    int selected = 0;

    public DripAdapter(Context context, ArrayList<String> arrayList, OnMyCommonItem OnMyCommonItem) {
        this.cn = context;
        this.alldrip = arrayList;
        this.onMyCommonItem = OnMyCommonItem;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(this.cn).inflate(R.layout.bg_item, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, final int i) {
        if (this.selected == i) {
            holder.setselected.setVisibility(View.VISIBLE);
        } else {
            holder.setselected.setVisibility(View.GONE);
        }

        MyUtils.setmargin(this.cn, holder.laymain, 0, 11, 0, 0);
        Glide.with(this.cn).load("file:///android_asset/drip/" + this.alldrip.get(i)).into(holder.setthumb);
        holder.laymain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                DripAdapter.this.onMyCommonItem.OnMyClick1(i, DripAdapter.this.alldrip.get(i));
            }
        });
    }

    public int getItemCount() {
        return this.alldrip.size();
    }

    public void setselected(int i) {
        this.selected = i;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        RelativeLayout laymain;
        ImageView setselected;
        ImageView setthumb;

        public Holder(View view) {
            super(view);
            this.laymain = (RelativeLayout) view.findViewById(R.id.laymain);
            this.setthumb = (ImageView) view.findViewById(R.id.setthumb);
            this.setselected = (ImageView) view.findViewById(R.id.setselected);
        }
    }
}
