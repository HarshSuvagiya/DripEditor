package com.scorpion.dripeditor.AdapterFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.scorpion.dripeditor.GROWUP_MyUtils;
import com.scorpion.dripeditor.InterfaceFol.GROWUP_OnMyCommonItem;
import com.scorpion.dripeditor.R;

import java.io.File;
import java.util.ArrayList;

public class GROWUP_Ad_CreationList extends RecyclerView.Adapter<GROWUP_Ad_CreationList.Holder> {
    ArrayList<File> allfile = new ArrayList<>();
    Context cn;
    GROWUP_OnMyCommonItem onMyCommonItem;

    public GROWUP_Ad_CreationList(Context context, ArrayList<File> arrayList, GROWUP_OnMyCommonItem gROWUP_OnMyCommonItem) {
        this.cn = context;
        this.allfile = arrayList;
        this.onMyCommonItem = gROWUP_OnMyCommonItem;
    }

    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(this.cn).inflate(R.layout.growup_ad_creationlist, viewGroup, false));
    }

    public void onBindViewHolder(Holder holder, final int i) {
        GROWUP_MyUtils.setLSquare(this.cn, holder.laymain, 468);
        Glide.with(this.cn).load(this.allfile.get(i).getAbsolutePath()).into(holder.setthub);
        holder.laymain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GROWUP_Ad_CreationList.this.onMyCommonItem.OnMyClick1(i, GROWUP_Ad_CreationList.this.allfile.get(i));
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
