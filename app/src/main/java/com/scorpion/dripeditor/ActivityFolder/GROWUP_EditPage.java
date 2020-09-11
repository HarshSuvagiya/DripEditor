package com.scorpion.dripeditor.ActivityFolder;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scorpion.dripeditor.AdapterFolder.GROWUP_Ad_Drip;
import com.scorpion.dripeditor.AdapterFolder.GROWUP_Ad_bg;
import com.scorpion.dripeditor.GROWUP_MyUtils;
import com.scorpion.dripeditor.InterfaceFol.GROWUP_OnMyCommonItem;
import com.scorpion.dripeditor.R;
import com.scorpion.dripeditor.TouchFolder.GROWUP_MultiTouchListener;
import com.scorpion.dripeditor.ViewFolder.MaskableFrameLayout;
import com.xpro.camera.lite.utils.C7572C;
import com.xpro.camera.lite.utils.NcnnModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GROWUP_EditPage extends Activity {
    GROWUP_Ad_bg ad_bg;
    GROWUP_Ad_Drip ad_drip;
    ArrayList<String> allbg = new ArrayList<>();
    ArrayList<String> alldrip = new ArrayList<>();
    ImageView btnbg;
    ImageView btnsave;
    ImageView btnshape;
    Context cn = this;
    /* access modifiers changed from: private */
    public Bitmap cutBit;
    RelativeLayout layimage;
    LinearLayout layprogress;
    MaskableFrameLayout maskableFrameLayout;
    /* access modifiers changed from: private */
    public Bitmap originalCutBit;
    RecyclerView recyclerView;
    /* access modifiers changed from: private */
    public Bitmap selectedBit;
    ImageView setbg;
    ImageView setimage;
    View viewtmp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.growup_edit_page);
        init();
        resize();
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
                GROWUP_MyUtils.selectedBit = GROWUP_MyUtils.getBitmapResize(GROWUP_EditPage.this.cn, GROWUP_MyUtils.selectedBit, GROWUP_EditPage.this.setimage.getWidth(), GROWUP_EditPage.this.setimage.getHeight());
                Bitmap unused = GROWUP_EditPage.this.selectedBit = GROWUP_MyUtils.selectedBit;
                GROWUP_EditPage.this.setimage.setImageBitmap(GROWUP_EditPage.this.selectedBit);
                GROWUP_EditPage.this.cutmask();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void cutmask() {
        this.layprogress.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            public void run() {
                NcnnModel initNcnnModel = GROWUP_EditPage.this.initNcnnModel();
                int[] iArr = {0, 0, GROWUP_EditPage.this.selectedBit.getWidth(), GROWUP_EditPage.this.selectedBit.getHeight()};
                int width = GROWUP_EditPage.this.selectedBit.getWidth();
                int height = GROWUP_EditPage.this.selectedBit.getHeight();
                int i = width * height;
                int i2 = width;
                int i3 = width;
                int i4 = height;
                GROWUP_EditPage.this.selectedBit.getPixels(new int[i], 0, i2, 0, 0, i3, i4);
                int[] iArr2 = new int[i];
                initNcnnModel.GetPersonRectRefineContour(GROWUP_EditPage.this.selectedBit, iArr, iArr2);
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                createBitmap.setPixels(iArr2, 0, i2, 0, 0, i3, i4);
                GROWUP_EditPage gROWUP_EditPage = GROWUP_EditPage.this;
                Bitmap unused = gROWUP_EditPage.cutBit = GROWUP_MyUtils.getMask(gROWUP_EditPage.cn, GROWUP_EditPage.this.selectedBit, createBitmap, width, height);
                GROWUP_EditPage gROWUP_EditPage2 = GROWUP_EditPage.this;
                Bitmap unused2 = gROWUP_EditPage2.originalCutBit = Bitmap.createBitmap(gROWUP_EditPage2.cutBit);
                GROWUP_EditPage.this.runOnUiThread(new Runnable() {
                    public void run() {
                        GROWUP_EditPage.this.layimage.getLayoutParams().width = GROWUP_EditPage.this.cutBit.getWidth();
                        GROWUP_EditPage.this.layimage.getLayoutParams().height = GROWUP_EditPage.this.cutBit.getHeight();
                        GROWUP_EditPage.this.setimage.setImageBitmap(GROWUP_EditPage.this.cutBit);
                        GROWUP_EditPage.this.layprogress.setVisibility(View.GONE);
                        GROWUP_EditPage.this.maskableFrameLayout.setMask((int) R.drawable.dripbase);
                        GROWUP_EditPage.this.setimage.setOnTouchListener(new GROWUP_MultiTouchListener());
                    }
                });
            }
        }).start();
    }

    public final void mo9519b() {
        RectF rectF = new RectF();
        Bitmap bitmapFromAsset = GROWUP_MyUtils.getBitmapFromAsset(this.cn, "drip/3.png");
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
        String m26520a = C7572C.m26520a(this, "modle", new String[]{"person.bin", "person.proto"});
        Boolean.valueOf(ncnnModel.InitPerson(m26520a + "/person.proto", m26520a + "/person.bin"));
        return ncnnModel;
    }

    private void click() {
        this.btnsave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String saveBitmap = GROWUP_MyUtils.saveBitmap(GROWUP_EditPage.this.cn, GROWUP_MyUtils.getBitmapFromView(GROWUP_EditPage.this.layimage), "IMG_");
                Context context = GROWUP_EditPage.this.cn;
                GROWUP_MyUtils.Toast(context, "Saved : " + saveBitmap);
                GROWUP_EditPage gROWUP_EditPage = GROWUP_EditPage.this;
                gROWUP_EditPage.startActivity(new Intent(gROWUP_EditPage.cn, GROWUP_PreviewPage.class).putExtra("path", saveBitmap));
            }
        });
        this.layprogress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.btnbg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int parseInt = Integer.parseInt(GROWUP_EditPage.this.recyclerView.getTag().toString());
                GROWUP_EditPage.this.recyclerView.setTag(0);
                GROWUP_EditPage.this.adapterBg();
                if (parseInt != 0) {
                    GROWUP_EditPage.this.manageClick(false);
                    GROWUP_EditPage.this.recyclerView.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.viewtmp.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.maskableFrameLayout.setOnTouchListener(new GROWUP_MultiTouchListener());
                    GROWUP_EditPage.this.setimage.setOnTouchListener((View.OnTouchListener) null);
                } else if (GROWUP_EditPage.this.recyclerView.getVisibility() == View.VISIBLE) {
                    GROWUP_EditPage.this.recyclerView.setVisibility(View.INVISIBLE);
                    GROWUP_EditPage.this.viewtmp.setVisibility(View.INVISIBLE);
//                    GROWUP_EditPage.this.btnbg.setImageResource(R.drawable.background_button);
                } else {
                    GROWUP_EditPage.this.recyclerView.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.viewtmp.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.manageClick(false);
                    GROWUP_EditPage.this.maskableFrameLayout.setOnTouchListener(new GROWUP_MultiTouchListener());
                    GROWUP_EditPage.this.setimage.setOnTouchListener((View.OnTouchListener) null);
                }
            }
        });
        this.btnshape.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int parseInt = Integer.parseInt(GROWUP_EditPage.this.recyclerView.getTag().toString());
                GROWUP_EditPage.this.recyclerView.setTag(1);
                GROWUP_EditPage.this.adapterDrip();
                if (parseInt != 1) {
                    GROWUP_EditPage.this.recyclerView.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.viewtmp.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.manageClick(true);
                    GROWUP_EditPage.this.setimage.setOnTouchListener(new GROWUP_MultiTouchListener());
                    GROWUP_EditPage.this.maskableFrameLayout.setOnTouchListener((View.OnTouchListener) null);
                } else if (GROWUP_EditPage.this.recyclerView.getVisibility() == View.VISIBLE) {
                    GROWUP_EditPage.this.recyclerView.setVisibility(View.INVISIBLE);
                    GROWUP_EditPage.this.viewtmp.setVisibility(View.INVISIBLE);
//                    GROWUP_EditPage.this.btnshape.setImageResource(R.drawable.drip_button);
                } else {
                    GROWUP_EditPage.this.recyclerView.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.viewtmp.setVisibility(View.VISIBLE);
                    GROWUP_EditPage.this.manageClick(true);
                    GROWUP_EditPage.this.setimage.setOnTouchListener(new GROWUP_MultiTouchListener());
                    GROWUP_EditPage.this.maskableFrameLayout.setOnTouchListener((View.OnTouchListener) null);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void manageClick(Boolean bool) {
//        if (!bool.booleanValue()) {
//            this.btnbg.setImageResource(R.drawable.background_press_button);
//            this.btnshape.setImageResource(R.drawable.drip_button);
//            return;
//        }
//        this.btnbg.setImageResource(R.drawable.background_button);
//        this.btnshape.setImageResource(R.drawable.drip_press_button);
    }

    /* access modifiers changed from: private */
    public void adapterDrip() {
        this.ad_drip = new GROWUP_Ad_Drip(this.cn, this.alldrip, new GROWUP_OnMyCommonItem() {
            public void OnMyClick2(int i, Object obj) {
            }

            public void OnMyClick1(int i, Object obj) {
                GROWUP_EditPage.this.ad_drip.setselected(i);
                GROWUP_EditPage.this.maskableFrameLayout.setMask((Drawable) new BitmapDrawable(GROWUP_MyUtils.getBitmapFromAsset(GROWUP_EditPage.this.cn, "drip/" + ((String) obj))));
            }
        });
        this.recyclerView.setAdapter(this.ad_drip);
    }

    /* access modifiers changed from: private */
    public void adapterBg() {
        this.ad_bg = new GROWUP_Ad_bg(this.cn, this.allbg, new GROWUP_OnMyCommonItem() {
            public void OnMyClick2(int i, Object obj) {
            }

            public void OnMyClick1(int i, Object obj) {
                GROWUP_EditPage.this.ad_bg.setselected(i);
                if (i == 0) {
                    GROWUP_EditPage.this.setbg.setImageResource(0);
                    return;
                }
                GROWUP_EditPage.this.setbg.setImageBitmap(GROWUP_MyUtils.getBitmapFromAsset(GROWUP_EditPage.this.cn, "bg/" + ((String) obj)));
            }
        });
        this.recyclerView.setAdapter(this.ad_bg);
    }

    private void resize() {
        GROWUP_MyUtils.setLLayout(this.cn, this.layimage, 1080, 1128);
        GROWUP_MyUtils.setLLayout(this.cn, this.btnshape, 540, 176);
        GROWUP_MyUtils.setLLayout(this.cn, this.btnbg, 540, 176);
        GROWUP_MyUtils.setLLayout(this.cn, this.recyclerView, 1080, 180);
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
        GROWUP_MyUtils.setsize(this.cn, (View) this.btnsave, 80, (Boolean) true);
        ImageView imageView = (ImageView) findViewById(R.id.btnback);
        GROWUP_MyUtils.setsize(this.cn, (View) imageView, 80, (Boolean) true);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GROWUP_EditPage.this.onBackPressed();
            }
        });
    }
}
