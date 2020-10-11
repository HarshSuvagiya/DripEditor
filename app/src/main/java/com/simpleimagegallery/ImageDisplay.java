package com.simpleimagegallery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scorpion.dripeditor.Activity.BGchangeEditor;
import com.scorpion.dripeditor.Activity.CropActivity;
import com.scorpion.dripeditor.Activity.DripEditorActivity;
import com.scorpion.dripeditor.Activity.Utils;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.R;
import com.simpleimagegallery.utils.MarginDecoration;
import com.simpleimagegallery.utils.PicHolder;
import com.simpleimagegallery.utils.itemClickListener;
import com.simpleimagegallery.utils.pictureFacer;
import com.simpleimagegallery.utils.picture_Adapter;
import java.util.ArrayList;
import static com.scorpion.dripeditor.MyUtils.selectedBit;


public class ImageDisplay extends Activity implements itemClickListener {

    RecyclerView imageRecycler;
    ArrayList<pictureFacer> allpictures;
    ProgressBar load;
    String foldePath;
    TextView folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        folderName = findViewById(R.id.foldername);
        folderName.setText(getIntent().getStringExtra("folderName"));

        foldePath =  getIntent().getStringExtra("folderPath");
        allpictures = new ArrayList<>();
        imageRecycler = findViewById(R.id.recycler);
        imageRecycler.addItemDecoration(new MarginDecoration(this));
        imageRecycler.hasFixedSize();
        load = findViewById(R.id.loader);


        if(allpictures.isEmpty()){
            load.setVisibility(View.VISIBLE);
            allpictures = getAllImagesByFolder(foldePath);
            imageRecycler.setAdapter(new picture_Adapter(allpictures,ImageDisplay.this,this));
            load.setVisibility(View.GONE);
        }else{

        }
    }

    /**
     *
     * @param holder The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     * @param pics An ArrayList of all the items in the Adapter
     */
    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

            String realPathFromURI =  pics.get(position).getPicturePath();
            if (realPathFromURI != null) {
                Log.e("AAA", "Path : " + realPathFromURI);
                startActivityForResult(new Intent(this, CropActivity.class).putExtra("path", realPathFromURI), 11);
                return;
            }
            MyUtils.Toast(this, getResources().getString(R.string.select_another_image));
            return;

    }
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 10 && i2 == -1 && intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                String realPathFromURI = MyUtils.getRealPathFromURI(this, data);
                if (realPathFromURI != null) {
                    Log.e("AAA", "Path : " + realPathFromURI);
                    startActivityForResult(new Intent(this, CropActivity.class).putExtra("path", realPathFromURI), 11);
                    finish();
                    return;
                }
                MyUtils.Toast(this, getResources().getString(R.string.select_another_image));
                return;
            }
            MyUtils.Toast(this, getResources().getString(R.string.select_another_image));
        } else if (i != 11 || i2 != -1) {
        } else {
            if(MyUtils.dripeffect) {
                if (selectedBit != null) {
                    startActivity(new Intent(this, DripEditorActivity.class));
                    finish();
                } else {
                    MyUtils.Toast(this, getResources().getString(R.string.select_another_image));
                }
            }
            else
            {

                if (selectedBit != null) {
                    Utils.bitmap=selectedBit;
                    startActivity(new Intent(this, BGchangeEditor.class));
                    finish();
                } else {
                    MyUtils.Toast(this, getResources().getString(R.string.select_another_image));
                }
            }
        }
    }

    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {

    }

    /**
     * This Method gets all the images in the folder paths passed as a String to the method and returns
     * and ArrayList of pictureFacer a custom object that holds data of a given image
     * @param path a String corresponding to a folder path on the device external storage
     */
    public ArrayList<pictureFacer> getAllImagesByFolder(String path){
        ArrayList<pictureFacer> images = new ArrayList<>();
        Uri allVideosuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = ImageDisplay.this.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                pictureFacer pic = new pictureFacer();

                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                images.add(pic);
            }while(cursor.moveToNext());
            cursor.close();
            ArrayList<pictureFacer> reSelection = new ArrayList<>();
            for(int i = images.size()-1;i > -1;i--){
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

}
