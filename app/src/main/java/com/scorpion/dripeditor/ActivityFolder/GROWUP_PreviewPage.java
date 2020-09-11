package com.scorpion.dripeditor.ActivityFolder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.scorpion.dripeditor.GROWUP_MyUtils;
import com.scorpion.dripeditor.R;

public class GROWUP_PreviewPage extends Activity {
    ImageView btndelete;
    ImageView btnshare;
    Context cn = this;
    String path;
    ImageView setimage;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(1024);
        setContentView((int) R.layout.growup_preview_page);
        this.path = getIntent().getStringExtra("path");
        init();
        resize();
        click();
        Glide.with(this.cn).load(this.path).into(this.setimage);
    }

    private void click() {
        this.btnshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new GROWUP_MyUtils(GROWUP_PreviewPage.this.cn);
                GROWUP_MyUtils.shareAnyFile(GROWUP_PreviewPage.this.cn, GROWUP_PreviewPage.this.path);
            }
        });
        this.btndelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final AlertDialog create = new AlertDialog.Builder(GROWUP_PreviewPage.this.cn).create();
                create.setTitle("Delete");
                create.setMessage("Are You Sure ?");
                create.setButton(-2, (CharSequence) "NO", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        create.dismiss();
                    }
                });
                create.setButton(-1, (CharSequence) "YES", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        create.dismiss();
                        GROWUP_MyUtils.deleteFile(GROWUP_PreviewPage.this.cn, GROWUP_PreviewPage.this.path);
                        GROWUP_PreviewPage.this.finish();
                    }
                });
                create.show();
            }
        });
    }

    private void resize() {
        GROWUP_MyUtils.setsize(this.cn, (View) this.btnshare, 462, 192);
        GROWUP_MyUtils.setsize(this.cn, (View) this.btndelete, 462, 192);
    }

    private void init() {
        this.setimage = (ImageView) findViewById(R.id.setimage);
        this.btnshare = (ImageView) findViewById(R.id.btnshare);
        this.btndelete = (ImageView) findViewById(R.id.btndelete);
        ImageView imageView = (ImageView) findViewById(R.id.btnback);
        GROWUP_MyUtils.setsize(this.cn, (View) imageView, 80, (Boolean) true);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GROWUP_PreviewPage.this.onBackPressed();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
