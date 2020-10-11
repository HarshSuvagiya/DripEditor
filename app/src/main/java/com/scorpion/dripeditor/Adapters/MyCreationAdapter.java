package com.scorpion.dripeditor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.Interface.OnMyCommonItem;
import com.scorpion.dripeditor.R;

import java.io.File;
import java.util.ArrayList;

public class MyCreationAdapter extends RecyclerView.Adapter<MyCreationAdapter.Holder> {
    ArrayList<File> allfile = new ArrayList<>();
    Context cn;
    OnMyCommonItem onMyCommonItem;

    public MyCreationAdapter(Context context, ArrayList<File> arrayList, OnMyCommonItem OnMyCommonItem1) {
        this.cn = context;
        this.allfile = arrayList;
        this.onMyCommonItem = OnMyCommonItem1;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(this.cn).inflate(R.layout.creationlist_item, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, final int i) {
        MyUtils.setLSquare(this.cn, holder.laymain, 468);
        Glide.with(this.cn).load(this.allfile.get(i).getAbsolutePath()).into(holder.setthub);
        holder.laymain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MyCreationAdapter.this.onMyCommonItem.OnMyClick1(i, MyCreationAdapter.this.allfile.get(i));
            }
        });
    }

    public int getItemCount() {
        return this.allfile.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout laymain;
        ImageView setthub;

        public Holder(View view) {
            super(view);
            this.laymain = (LinearLayout) view.findViewById(R.id.laymain);
            this.setthub = (ImageView) view.findViewById(R.id.setthumb);
        }
    }
}
