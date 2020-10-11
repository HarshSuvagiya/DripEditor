package com.scorpion.dripeditor.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.scorpion.dripeditor.Activity.AddText;
import com.scorpion.dripeditor.R;

import java.util.ArrayList;

public class FontStyleAdapter extends
		RecyclerView.Adapter<FontStyleAdapter.ViewHolder> {

	String[] fontimg;
	Context context;
	public static int p;
	ArrayList<String> font_images;
	Bitmap bit = null;

	public FontStyleAdapter(Context context, String[] fontimg) {
		super();
		this.context = context;
		this.fontimg = fontimg;
	}

	@Override
	public ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.fontstyle_card, parent, false);

		ViewHolder holder = new ViewHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(
			final ViewHolder holder,
			final int position) {

		AssetManager am = context.getApplicationContext().getAssets();

		Typeface custom_font = Typeface.createFromAsset(am, "font/"
				+ fontimg[position]);

		holder.txtfont.setTypeface(custom_font);

		if(position==p)
			holder.txtfont.setTextColor(Color.parseColor("#F9AC28"));
		else
			holder.txtfont.setTextColor(Color.WHITE);

		holder.itemView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				notifyDataSetChanged();
				p=position;
				((AddText) context).StyleFont(position);
			}
		});
	}

	public int getItemCount() {
		return fontimg.length;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView txtfont;

		public ViewHolder(View itemView) {
			super(itemView);

			txtfont = (TextView) itemView.findViewById(R.id.txtfont);

		}
	}

}
