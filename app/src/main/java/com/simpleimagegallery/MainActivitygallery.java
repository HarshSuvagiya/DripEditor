package com.simpleimagegallery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.AdSize;
import com.scorpion.dripeditor.R;
import com.simpleimagegallery.utils.MarginDecoration;
import com.simpleimagegallery.utils.PicHolder;
import com.simpleimagegallery.utils.imageFolder;
import com.simpleimagegallery.utils.itemClickListener;
import com.simpleimagegallery.utils.pictureFacer;
import com.simpleimagegallery.utils.pictureFolderAdapter;

import java.util.ArrayList;

public class MainActivitygallery extends Activity implements itemClickListener {

    RecyclerView folderRecycler;
    TextView empty;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private com.facebook.ads.AdView adViewfb;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainphoto);
        //banner ad
        adViewfb = new com.facebook.ads.AdView(MainActivitygallery.this, getString(R.string.banner_ad_unit_idfb), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adContainer.setVisibility(View.VISIBLE);
        // Add the ad view to your activity layout
        adContainer.addView(adViewfb);

        // Request an ad
        adViewfb.loadAd();
        if(ContextCompat.checkSelfPermission(MainActivitygallery.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivitygallery.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        //____________________________________________________________________________________

        empty =findViewById(R.id.empty);

        folderRecycler = findViewById(R.id.folderRecycler);
        folderRecycler.addItemDecoration(new MarginDecoration(this));
        folderRecycler.hasFixedSize();
        ArrayList<imageFolder> folds = getPicturePaths();

        if(folds.isEmpty()){
            empty.setVisibility(View.VISIBLE);
        }else{
            RecyclerView.Adapter folderAdapter = new pictureFolderAdapter(folds, MainActivitygallery.this,this);
            folderRecycler.setAdapter(folderAdapter);
        }

        changeStatusBarColor();
    }

    /**1
     * @return
     * gets all folders with pictures on the device and loads each of them in a custom object imageFolder
     * the returns an ArrayList of these custom objects
     */
    private ArrayList<imageFolder> getPicturePaths(){
        ArrayList<imageFolder> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                imageFolder folds = new imageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);//if the folder has only one picture this line helps to set it as first so as to avoid blank image in itemview
                    folds.addpics();
                    picFolders.add(folds);
                }else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0;i < picFolders.size();i++){
            Log.d("picture folders",picFolders.get(i).getFolderName()+" and path = "+picFolders.get(i).getPath()+" "+picFolders.get(i).getNumberOfPics());
        }

        //reverse order ArrayList
       /* ArrayList<imageFolder> reverseFolders = new ArrayList<>();

        for(int i = picFolders.size()-1;i > reverseFolders.size()-1;i--){
            reverseFolders.add(picFolders.get(i));
        }*/

        return picFolders;
    }


    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<pictureFacer> pics) {

    }

    /**
     * Each time an item in the RecyclerView is clicked this method from the implementation of the transitListerner
     * in this activity is executed, this is possible because this class is passed as a parameter in the creation
     * of the RecyclerView's Adapter, see the adapter class to understand better what is happening here
     * @param pictureFolderPath a String corresponding to a folder path on the device external storage
     */
    @Override
    public void onPicClicked(String pictureFolderPath,String folderName) {
        Intent move = new Intent(MainActivitygallery.this,ImageDisplay.class);
        move.putExtra("folderPath",pictureFolderPath);
        move.putExtra("folderName",folderName);

        //move.putExtra("recyclerItemSize",getCardsOptimalWidth(4));
        startActivity(move);

    }


    /**
     * Default status bar height 24dp,with code API level 24
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarColor()
    {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));

    }

}
