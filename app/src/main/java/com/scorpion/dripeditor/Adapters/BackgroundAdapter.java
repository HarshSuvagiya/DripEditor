package com.scorpion.dripeditor.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import com.scorpion.dripeditor.Activity.BGchangeEditor;
import com.scorpion.dripeditor.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.ViewHolder> {

	String[] alImage;
	Context context;
	InputStream is;
	ArrayList<String> arrlst_images;
	Bitmap bitmap = null;

	public BackgroundAdapter(Context context, String[] alImage) {
		super();
		this.context = context;
		this.alImage = alImage;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.card_layout, parent, false);

		ViewHolder holder = new ViewHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder,
			final int position) {

		try {

			is = context.getAssets().open("backgrounds/" + alImage[position]);
			bitmap = BitmapFactory.decodeStream(is);
			holder.img.setImageBitmap(bitmap);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		holder.itemView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// CropImage.
				
				((BGchangeEditor) context).callIntent1(position);

				 Bundle b = new Bundle();
				
				 b.putString("position", String.valueOf(position));
				
//				 Intent i = new Intent(context, set_garden_frame.class);
//				 i.putExtras(b);
//				 context.startActivity(i);

			}
		});

	}

	public int getItemCount() {
		return alImage.length;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
//		public CardView card_receipe;
		public ImageView img;

		public ViewHolder(View itemView) {
			super(itemView);

//			card_receipe = (CardView) itemView.findViewById(R.id.cv);
			img = (ImageView) itemView.findViewById(R.id.imgwater);

		}
	}

}
