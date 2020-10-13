package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.scorpion.dripeditor.Adapters.BackgroundAdapter;
import com.scorpion.dripeditor.FBInterstitial;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.R;
import com.scorpion.dripeditor.TouchFolder.MultiTouchListener;
import com.scorpion.dripeditor.photoeditor_stickerview.StickerViews;
import com.simpleimagegallery.ImageDisplay;
import com.simpleimagegallery.MainActivitygallery;
import com.xpro.camera.lite.utils.NcnnModel;
import com.xpro.camera.lite.utils.autocut;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.scorpion.dripeditor.MyUtils.selectedBit;

public class BGchangeEditor extends Activity {
    ImageView bck, imgbg, save;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    BackgroundAdapter adapter;
    String[] imagList = null;
    public static Bitmap garden_btimap;
    ArrayList<String> alImage;
    static View view;
    static int pos;
    public static ArrayList<View> mViews;
    ImageView btnbg, btn_text, btn_sticker;
    LinearLayout lltrans;
    public static ImageView img;
    Bitmap  bmp1;
    RelativeLayout sn_main_rl;
    int i = 0;
    public static Uri selectedImageUri;
    public static final int RESULT_FROM_GALLERY = 2;
    public static String s;
    public static Bitmap sticker_bitmap;
    ArrayList<StickerViews> stickerList = new ArrayList<StickerViews>();
    public static StickerViews mCurrentView;
    private ArrayList<View> mViews1;
    LinearLayout scal_ll;
    Boolean xy = true;
    LinearLayout layprogress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bgchange);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        bck = (ImageView) findViewById(R.id.sn_back);
        sn_main_rl = (RelativeLayout) findViewById(R.id.layimage);
        image = (ImageView) findViewById(R.id.setimage);
        save = (ImageView) findViewById(R.id.save);
        btn_text = (ImageView) findViewById(R.id.btn_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btn_sticker = (ImageView) findViewById(R.id.btn_sticker);
        imgbg = (ImageView) findViewById(R.id.imgbg);
        this.layprogress = (LinearLayout) findViewById(R.id.layprogress);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 1080 / 1080,
                getResources().getDisplayMetrics().heightPixels * 1080 / 1920);

        sn_main_rl.setLayoutParams(params2);

        RelativeLayout.LayoutParams params22 = new RelativeLayout.LayoutParams(
                getResources().getDisplayMetrics().widthPixels * 1080 / 1080,
                getResources().getDisplayMetrics().heightPixels * 1080 / 1920);

        imgbg.setLayoutParams(params22);

        scal_ll = (LinearLayout) findViewById(R.id.scal_layout);
        mViews = new ArrayList<View>(0);
        mViews1 = new ArrayList<View>(0);
        btnbg = (ImageView) findViewById(R.id.btnbg);

        lltrans = (LinearLayout) findViewById(R.id.seek_layout);
        recyclerView.setHasFixedSize(true);
        Utils.bmp = Utils.bitmap;
        bmp1 = Utils.bmp;
        Utils.bm[i] = bmp1;
        Utils.maskbit[i] = bmp1;
        selectedImageUri = null;
        i++;
//        addImge();
        image.post(new Runnable() {
            public void run() {
                selectedBit = MyUtils.getBitmapResize(BGchangeEditor.this, selectedBit, image.getWidth(),image.getHeight());

                image.setImageBitmap(selectedBit);
                bmp1 = selectedBit;
                cutmask();
            }
        });

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            imagList = getAssets().list("backgrounds");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        alImage = new ArrayList<String>(Arrays.asList(imagList));

        adapter = new BackgroundAdapter(BGchangeEditor.this, imagList);
        recyclerView.setAdapter(adapter);
        btn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                lltrans.setVisibility(View.GONE);
                scal_ll.setVisibility(View.GONE);
                Intent i = new Intent(BGchangeEditor.this, AddText.class);
                startActivityForResult(i, 222);
            }
        });

        btnbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView.getVisibility() == View.VISIBLE)
                    recyclerView.setVisibility(View.GONE);
                else
                    recyclerView.setVisibility(View.VISIBLE);
                scal_ll.setVisibility(View.GONE);
                lltrans.setVisibility(View.GONE);
            }
        });
    imgbg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mCurrentView != null) {
                mCurrentView.setInEdit(false);
            }
        }
    });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
                Utils.finalEditedBitmapImage = getMainFrameBitmap(sn_main_rl);

                Uri u = bitmapToUriConverter(Utils.finalEditedBitmapImage);
                if (u != null) {
                    try {
                        Utils.bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(u));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Utils.bmp = Utils.bitmap;
                    s = getResources().getString(R.string.app_name);
                    SaveImage(Utils.bmp);
                    FBInterstitial.getInstance().displayFBInterstitial(BGchangeEditor.this, new FBInterstitial.FbCallback() {
                        public void callbackCall() {
                            Intent i = new Intent(BGchangeEditor.this, SavedImgActivity.class);
                            startActivity(i);
                            finish();
                        }
                    });


                }
            }
        });

        sn_main_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentView != null) {
                    mCurrentView.setInEdit(false);
                }
            }
        });
        btn_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(BGchangeEditor.this, StickerTabActivity.class), 21);

            }
        });
    }

    public Uri bitmapToUriConverter(Bitmap mBitmap) {
        Uri uri = null;
        try {
            File file = new File(getFilesDir(), "Image"
                    + new Random().nextInt() + ".png");
            FileOutputStream out = openFileOutput(file.getName(),
                    Context.MODE_PRIVATE);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            String realPath = file.getAbsolutePath();
            File f = new File(realPath);
            uri = Uri.fromFile(f);
        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
        return uri;
    }


    public void callIntent1(int position) {

        InputStream is = null;
        try {
            is = getAssets().open("backgrounds/" + imagList[position]);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        garden_btimap = BitmapFactory.decodeStream(is);
        imgbg.setImageBitmap(garden_btimap);
    }


    private Bitmap getMainFrameBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));
        return CropBitmapTransparency(bitmap);
    }



    Bitmap CropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                if (((sourceBitmap.getPixel(x, y) >> 24) & 255) > 0) {
                    if (x < minX) {
                        minX = x;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y < minY) {
                        minY = y;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }
        if (maxX < minX || maxY < minY) {
            return null;
        }
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }




    public static void SaveImage(Bitmap bmp) {
        Context context = null;
        // TODO Auto-generated method stub
        File myDir = new File("/sdcard/" + s);
        myDir.mkdirs();
        bmp = Bitmap.createScaledBitmap(bmp, Utils.finalEditedBitmapImage.getWidth(), Utils.finalEditedBitmapImage.getHeight(), false);
        String fname = "Image-" + System.currentTimeMillis() + ".jpg";
        Utils.file = new File(myDir, fname);
        if (Utils.file.exists())
            Utils.file.delete();

        try {
            FileOutputStream out = new FileOutputStream(Utils.file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     ImageView image;
    private void addImge() {
        image = new ImageView(this);


    }

    public Bitmap cutBit;
    public Bitmap originalCutBit;
    public void cutmask() {
        this.layprogress.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                NcnnModel initNcnnModel = initNcnnModel();
                int[] iArr = {0, 0, selectedBit.getWidth(), selectedBit.getHeight()};
                int width =selectedBit.getWidth();
                int height = selectedBit.getHeight();
                int i = width * height;
                int i2 = width;
                int i3 = width;
                int i4 = height;
                selectedBit.getPixels(new int[i], 0, i2, 0, 0, i3, i4);
                int[] iArr2 = new int[i];
                initNcnnModel.GetPersonRectRefineContour(selectedBit, iArr, iArr2);
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr2, 0, i2, 0, 0, i3, i4);
                BGchangeEditor EditPage = BGchangeEditor.this;
                Bitmap unused = EditPage.cutBit = MyUtils.getMask(BGchangeEditor.this, selectedBit, createBitmap, width, height);
                BGchangeEditor EditPage2 = BGchangeEditor.this;
                Bitmap unused2 = EditPage2.originalCutBit = Bitmap.createBitmap(EditPage2.cutBit);
                runOnUiThread(new Runnable() {
                    public void run() {
//                        sn_main_rl.getLayoutParams().width = cutBit.getWidth();
//                        sn_main_rl.getLayoutParams().height = cutBit.getHeight();
                        image.setImageBitmap(BGchangeEditor.this.cutBit);
                        layprogress.setVisibility(View.GONE);
//                        maskableFrameLayout.setMask((int) R.drawable.dripbase);
                        image.setOnTouchListener(new MultiTouchListener());
                    }
                });
            }
        }).start();
    }

    public NcnnModel initNcnnModel() {
        NcnnModel ncnnModel = new NcnnModel();
        String m26520a = autocut.m26520a(this, "modle", new String[]{"person.bin", "person.proto"});
        Boolean.valueOf(ncnnModel.InitPerson(m26520a + "/person.proto", m26520a + "/person.bin"));
        return ncnnModel;
    }


    private void addStickerView() {
        final StickerViews stickerView = new StickerViews(this);

        stickerView.setImageBitmap(sticker_bitmap);

        stickerView.setOperationListener(new StickerViews.OperationListener() {
            @Override
            public void onDeleteClick() {
                mViews1.remove(stickerView);
                sn_main_rl.removeView(stickerView);
            }

            @Override
            public void onEdit(StickerViews stickerView) {
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }

            @Override
            public void onTop(StickerViews stickerView) {
                int position = mViews1.indexOf(stickerView);
                if (position == mViews1.size() - 1) {
                    return;
                }
                StickerViews stickerTemp = (StickerViews) mViews1
                        .remove(position);
                mViews1.add(mViews1.size(), stickerTemp);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        sn_main_rl.addView(stickerView, lp);
        mViews1.add(stickerView);
        setCurrentEdit(stickerView);
        stickerList.add(mCurrentView);

    }
    public Bitmap replaceColor(Bitmap src, int fromColor, int targetColor) {
        if(src == null) {
            return null;
        }
        // Source image size
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        //get pixels
        src.getPixels(pixels, 0, width, 0, 0, width, height);

        for(int x = 0; x < pixels.length; ++x) {
            pixels[x] = (pixels[x] == fromColor) ? targetColor : pixels[x];
        }
        // create result bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        //set pixels
        result.setPixels(pixels, 0, width, 0, 0, width, height);

        return result;
    }


    private void setCurrentEdit(StickerViews stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RESULT_FROM_GALLERY:
                    selectedImageUri = data.getData();
                    Utils.selectedImageUri = selectedImageUri;
                    Utils.check = false;
                    try {
                        Utils.bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Utils.selectedImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent newIntent2 = new Intent(BGchangeEditor.this,
                            MainActivitygallery.class);
                    startActivityForResult(newIntent2, 555);
                    break;

                case 555:
                    selectedImageUri = null;
                    bmp1 = Utils.bmp;
                    Utils.bm[i] = bmp1;
                    Utils.maskbit[i] = bmp1;
                    i++;
                    addImge();
                    break;
                case 222:
                    addStickerView();
                    return;
                case 21:
                    addStickerView();
                    sn_main_rl.invalidate();
                    return;
            }
        }
    }


}
