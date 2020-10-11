package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scorpion.dripeditor.Adapters.DripAdapter;
import com.scorpion.dripeditor.Adapters.DripBgAdapter;
import com.scorpion.dripeditor.MyUtils;
import com.scorpion.dripeditor.Interface.OnMyCommonItem;
import com.scorpion.dripeditor.R;
import com.scorpion.dripeditor.TouchFolder.MultiTouchListener;
import com.scorpion.dripeditor.ViewFolder.MaskableFrameLayout;
import com.xpro.camera.lite.utils.autocut;
import com.xpro.camera.lite.utils.NcnnModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DripEditorActivity extends Activity {
    DripBgAdapter ad_bg;
    DripAdapter ad_drip;
    ArrayList<String> allbg = new ArrayList<>();
    ArrayList<String> alldrip = new ArrayList<>();
    ImageView btnbg;
    ImageView btnsave;
    ImageView btnshape;
    Context cn = this;
    public Bitmap cutBit;
    RelativeLayout layimage;
    LinearLayout layprogress;
    MaskableFrameLayout maskableFrameLayout;
    public Bitmap originalCutBit;
    RecyclerView recyclerView;
    public Bitmap selectedBit;
    ImageView setbg;
    ImageView setimage;
    View viewtmp;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.drip_editor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init();
        click();
        try {
            this.allbg = new ArrayList<>(Arrays.asList(getAssets().list("bg")));
            this.allbg.add(0, "none");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.alldrip = new ArrayList<>(Arrays.asList(getAssets().list("drip")));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        adapterBg();
        this.setimage.post(new Runnable() {
            public void run() {
                MyUtils.selectedBit = MyUtils.getBitmapResize(DripEditorActivity.this.cn, MyUtils.selectedBit, DripEditorActivity.this.setimage.getWidth(), DripEditorActivity.this.setimage.getHeight());
                Bitmap unused = DripEditorActivity.this.selectedBit = MyUtils.selectedBit;
                DripEditorActivity.this.setimage.setImageBitmap(DripEditorActivity.this.selectedBit);
                DripEditorActivity.this.cutmask();
            }
        });
    }

    public void cutmask() {
        this.layprogress.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                NcnnModel initNcnnModel = DripEditorActivity.this.initNcnnModel();
                int[] iArr = {0, 0, DripEditorActivity.this.selectedBit.getWidth(), DripEditorActivity.this.selectedBit.getHeight()};
                int width = DripEditorActivity.this.selectedBit.getWidth();
                int height = DripEditorActivity.this.selectedBit.getHeight();
                int i = width * height;
                int i2 = width;
                int i3 = width;
                int i4 = height;
                DripEditorActivity.this.selectedBit.getPixels(new int[i], 0, i2, 0, 0, i3, i4);
                int[] iArr2 = new int[i];
                initNcnnModel.GetPersonRectRefineContour(DripEditorActivity.this.selectedBit, iArr, iArr2);
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr2, 0, i2, 0, 0, i3, i4);
                DripEditorActivity EditPage1 = DripEditorActivity.this;
                Bitmap unused = EditPage1.cutBit = MyUtils.getMask(EditPage1.cn, DripEditorActivity.this.selectedBit, createBitmap, width, height);
                DripEditorActivity EditPage11 = DripEditorActivity.this;
                Bitmap unused2 = EditPage11.originalCutBit = Bitmap.createBitmap(EditPage1.cutBit);
                DripEditorActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        DripEditorActivity.this.layimage.getLayoutParams().width = DripEditorActivity.this.cutBit.getWidth();
                        DripEditorActivity.this.layimage.getLayoutParams().height = DripEditorActivity.this.cutBit.getHeight();
                        DripEditorActivity.this.setimage.setImageBitmap(DripEditorActivity.this.cutBit);
                        DripEditorActivity.this.layprogress.setVisibility(View.GONE);
                        DripEditorActivity.this.maskableFrameLayout.setMask((int) R.drawable.dripbase);
                        DripEditorActivity.this.setimage.setOnTouchListener(new MultiTouchListener());
                    }
                });
            }
        }).start();
    }

    public final void mo9519b() {
        RectF rectF = new RectF();
        Bitmap bitmapFromAsset = MyUtils.getBitmapFromAsset(this.cn, "drip/3.png");
        rectF.set(0.0f, 0.0f, bitmapFromAsset != null ? (float) bitmapFromAsset.getWidth() : 0.0f, bitmapFromAsset != null ? (float) bitmapFromAsset.getHeight() : 0.0f);
        RectF rectF2 = new RectF();
        float min = Math.min(rectF2.width() / rectF.width(), rectF2.height() / rectF.height());
        Matrix matrix = new Matrix();
        matrix.setScale(min, min);
        matrix.postTranslate((rectF2.width() - (rectF.width() * min)) / 2.0f, (rectF2.height() - (rectF.height() * min)) / 2.0f);
        matrix.mapRect(new RectF(), rectF);
    }

    public NcnnModel initNcnnModel() {
        NcnnModel ncnnModel = new NcnnModel();
        String m26520a = autocut.m26520a(this, "modle", new String[]{"person.bin", "person.proto"});
        Boolean.valueOf(ncnnModel.InitPerson(m26520a + "/person.proto", m26520a + "/person.bin"));
        return ncnnModel;
    }

    private void click() {
        this.btnsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String saveBitmap = MyUtils.saveBitmap(DripEditorActivity.this.cn, MyUtils.getBitmapFromView(DripEditorActivity.this.layimage), "IMG_");
                Context context = DripEditorActivity.this.cn;
//                MyUtils.Toast(context, "Saved : " + saveBitmap);
                DripEditorActivity EditPage1 = DripEditorActivity.this;
                EditPage1.startActivity(new Intent(EditPage1.cn, PreviewImage.class).putExtra("path", saveBitmap));
            }
        });
        this.layprogress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.btnbg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int parseInt = Integer.parseInt(DripEditorActivity.this.recyclerView.getTag().toString());
                DripEditorActivity.this.recyclerView.setTag(0);
                DripEditorActivity.this.adapterBg();
                if (parseInt != 0) {
                    DripEditorActivity.this.manageClick(false);
                    DripEditorActivity.this.recyclerView.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.viewtmp.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.maskableFrameLayout.setOnTouchListener(new MultiTouchListener());
                    DripEditorActivity.this.setimage.setOnTouchListener((View.OnTouchListener) null);
                } else if (DripEditorActivity.this.recyclerView.getVisibility() == View.VISIBLE) {
                    DripEditorActivity.this.recyclerView.setVisibility(View.INVISIBLE);
                    DripEditorActivity.this.viewtmp.setVisibility(View.INVISIBLE);

                } else {
                    DripEditorActivity.this.recyclerView.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.viewtmp.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.manageClick(false);
                    DripEditorActivity.this.maskableFrameLayout.setOnTouchListener(new MultiTouchListener());
                    DripEditorActivity.this.setimage.setOnTouchListener((View.OnTouchListener) null);
                }
            }
        });
        this.btnshape.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int parseInt = Integer.parseInt(DripEditorActivity.this.recyclerView.getTag().toString());
                DripEditorActivity.this.recyclerView.setTag(1);
                DripEditorActivity.this.adapterDrip();
                if (parseInt != 1) {
                    DripEditorActivity.this.recyclerView.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.viewtmp.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.manageClick(true);
                    DripEditorActivity.this.setimage.setOnTouchListener(new MultiTouchListener());
                    DripEditorActivity.this.maskableFrameLayout.setOnTouchListener((View.OnTouchListener) null);
                } else if (DripEditorActivity.this.recyclerView.getVisibility() == View.VISIBLE) {
                    DripEditorActivity.this.recyclerView.setVisibility(View.INVISIBLE);
                    DripEditorActivity.this.viewtmp.setVisibility(View.INVISIBLE);

                } else {
                    DripEditorActivity.this.recyclerView.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.viewtmp.setVisibility(View.VISIBLE);
                    DripEditorActivity.this.manageClick(true);
                    DripEditorActivity.this.setimage.setOnTouchListener(new MultiTouchListener());
                    DripEditorActivity.this.maskableFrameLayout.setOnTouchListener((View.OnTouchListener) null);
                }
            }
        });
    }

    public void manageClick(Boolean bool) {
//        if (!bool.booleanValue()) {
//            this.btnbg.setImageResource(R.drawable.background_press_button);
//            this.btnshape.setImageResource(R.drawable.drip_button);
//            return;
//        }
//        this.btnbg.setImageResource(R.drawable.background_button);
//        this.btnshape.setImageResource(R.drawable.drip_press_button);
    }

    public void adapterDrip() {
        this.ad_drip = new DripAdapter(this.cn, this.alldrip, new OnMyCommonItem() {
            public void OnMyClick2(int i, Object obj) {
            }

            public void OnMyClick1(int i, Object obj) {
                DripEditorActivity.this.ad_drip.setselected(i);
                DripEditorActivity.this.maskableFrameLayout.setMask((Drawable) new BitmapDrawable(MyUtils.getBitmapFromAsset(DripEditorActivity.this.cn, "drip/" + ((String) obj))));
            }
        });
        this.recyclerView.setAdapter(this.ad_drip);
    }

    public void adapterBg() {
        this.ad_bg = new DripBgAdapter(this.cn, this.allbg, new OnMyCommonItem() {
            public void OnMyClick2(int i, Object obj) {
            }

            public void OnMyClick1(int i, Object obj) {
                DripEditorActivity.this.ad_bg.setselected(i);
                if (i == 0) {
                    DripEditorActivity.this.setbg.setImageResource(0);
                    return;
                }
                DripEditorActivity.this.setbg.setImageBitmap(MyUtils.getBitmapFromAsset(DripEditorActivity.this.cn, "bg/" + ((String) obj)));
            }
        });
        this.recyclerView.setAdapter(this.ad_bg);
    }



    private void init() {
        this.setbg = (ImageView) findViewById(R.id.setbg);
        this.setimage = (ImageView) findViewById(R.id.setimage);
        this.layimage = (RelativeLayout) findViewById(R.id.layimage);
        this.layprogress = (LinearLayout) findViewById(R.id.layprogress);
        this.viewtmp = findViewById(R.id.viewtmp);
        this.maskableFrameLayout = (MaskableFrameLayout) findViewById(R.id.frm_mask_animated);
        this.btnbg = (ImageView) findViewById(R.id.btnbg);
        this.btnshape = (ImageView) findViewById(R.id.btnshape);
        this.btnsave = (ImageView) findViewById(R.id.btnsave);
        this.btnsave.setVisibility(View.VISIBLE);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView.setTag(0);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.cn, RecyclerView.HORIZONTAL, false));
        MyUtils.setsize(this.cn, (View) this.btnsave, 80, (Boolean) true);
        ImageView imageView = (ImageView) findViewById(R.id.btnback);
        MyUtils.setsize(this.cn, (View) imageView, 80, (Boolean) true);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DripEditorActivity.this.onBackPressed();
            }
        });
    }
}
