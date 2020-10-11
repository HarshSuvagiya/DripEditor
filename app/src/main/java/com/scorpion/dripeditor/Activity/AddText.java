package com.scorpion.dripeditor.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.scorpion.dripeditor.Adapters.FontStyleAdapter;
import com.scorpion.dripeditor.R;

import java.io.IOException;

public class AddText extends Activity {
    ImageView txt_done, cancel;
    RelativeLayout add_text1;
    EditText edt;
    ImageView clr, bld, itlc, undrln;
    RecyclerView recycler_viewstyle;
    FontStyleAdapter adapter1;
    String[] liststyle = null;
    TextView temp;
    InputMethodManager mInputMethodManager;
    RecyclerView.LayoutManager mLayoutManager;
    Typeface typeface;
    int c;
    SeekBar sb_size;
    Boolean b = false, i = false, u = false;
    public static int prog = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_text);
        txt_done = (ImageView) findViewById(R.id.txt_done);
        add_text1 = (RelativeLayout) findViewById(R.id.addtext);
        temp = (TextView) findViewById(R.id.temp);
        edt = (EditText) findViewById(R.id.edt);
        cancel = (ImageView) findViewById(R.id.txtcancel);
        edt.setTextColor(Color.parseColor("#ffffff"));
        clr = (ImageView) findViewById(R.id.clr);
        bld = (ImageView) findViewById(R.id.bld);
        itlc = (ImageView) findViewById(R.id.italic);
        undrln = (ImageView) findViewById(R.id.underlin);
        sb_size = (SeekBar) findViewById(R.id.sbsize);
        recycler_viewstyle = (RecyclerView) findViewById(R.id.recycler_viewstyle);
        recycler_viewstyle.setHasFixedSize(true);

        // The number of Columns
        try {
            //listimg = getAssets().list("Font_Images");
            liststyle = getAssets().list("font");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                onBackPressed();
            }
        });

        adapter1 = new FontStyleAdapter(AddText.this,
                liststyle);
        recycler_viewstyle.setAdapter(adapter1);
        mLayoutManager = new GridLayoutManager(AddText.this, 1,
                LinearLayoutManager.HORIZONTAL, false);
        recycler_viewstyle.setLayoutManager(mLayoutManager);

        add_text1.setVisibility(View.VISIBLE);
        edt.setFocusable(true);
        edt.requestFocus();
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.showSoftInput(edt, InputMethodManager.SHOW_FORCED);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        typeface = Typeface.createFromAsset(getAssets(), "font/"
                + "font1.ttf");
        edt.setTypeface(typeface);

        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        sb_size.setMax(50);
        sb_size.setProgress(30);
        sb_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 10)
                    edt.setTextSize(i);
                prog = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogBuilder
                        .with(AddText.this)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorChangedListener(new OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int selectedColor) {
                                // Handle on color change
                                Log.d("ColorPicker",
                                        "onColorChanged: 0x"
                                                + Integer
                                                .toHexString(selectedColor));
                            }
                        })
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                // toast("onColorSelected: 0x" +
                                // Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int selectedColor, Integer[] allColors) {
                                c = selectedColor;
                                edt.setTextColor(selectedColor);
                                if (allColors != null) {
                                    StringBuilder sb = null;

                                    for (Integer color : allColors) {
                                        if (color == null)
                                            continue;

                                    }

                                    if (sb != null)
                                        Toast.makeText(getApplicationContext(),
                                                sb.toString(), Toast.LENGTH_SHORT)
                                                .show();
                                }
                            }
                        })
                        .setNegativeButton("cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                })
                        // .showColorEdit(true)
                        .build().show();
            }
        });

        bld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!b) {
                    b = true;
                    if (i)
                        edt.setTypeface(typeface, Typeface.BOLD | Typeface.ITALIC);
                    else
                        edt.setTypeface(typeface, Typeface.BOLD);
                } else {
                    b = false;
                    if (i)
                        edt.setTypeface(typeface, Typeface.ITALIC);
                    else
                        edt.setTypeface(typeface, Typeface.NORMAL);
                }

            }
        });

        itlc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!i) {
                    i = true;
                    if (b)
                        edt.setTypeface(typeface, Typeface.BOLD | Typeface.ITALIC);
                    else
                        edt.setTypeface(typeface, Typeface.ITALIC);
                } else {
                    i = false;
                    if (b)
                        edt.setTypeface(typeface, Typeface.BOLD);
                    else
                        edt.setTypeface(typeface, Typeface.NORMAL);
                }

            }
        });

        undrln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u) {
                    edt.setPaintFlags(edt.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                    u = false;
                } else {
                    edt.setPaintFlags(edt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    u = true;
                }
            }
        });
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                for (UnderlineSpan span : s.getSpans(0, s.length(), UnderlineSpan.class)) {
                    s.removeSpan(span);
                }


            }
        });
        edt.requestFocus();
        txt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.setText(edt.getText().toString());
//                Toast.makeText(getApplication(), "." + edt.getText().toString(), Toast.LENGTH_LONG).show();
                edt.setCursorVisible(false);
                if (edt.getText().toString().length() != 0) {
                    int h = edt.getHeight();
                    int w = edt.getWidth();
                    Bitmap testB;
//                    testB = textAsBitmap(edt.getText().toString(), 200, c, typeface);
                    testB = getViewBitmap(edt);
                    BGchangeEditor.sticker_bitmap = testB;
//                    WSTRNTSUDBLA_start_frame.sticker_bitmap=testB;
//                    WSTRNTSUDBLA_end_frame.sticker_bitmap=testB;
                } else {

                    Toast.makeText(getApplication(), "Please Enter Text...", Toast.LENGTH_LONG).show();
                }

                mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                setResult(RESULT_OK);
                finish();
            }
        });


        edt.setTextColor(Color.parseColor("#ffffff"));
    }

    public void StyleFont(int position) {
        typeface = Typeface.createFromAsset(getAssets(), "font/"
                + liststyle[position]);
        edt.setTypeface(typeface);
        temp.setTypeface(typeface);
    }

    @Deprecated
    public static Bitmap createBitmapFromView(View view) {
        Bitmap bitmap;
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap src = view.getDrawingCache();
        bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight());
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        src.recycle();
        src = null;
        return bitmap;
    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() + prog, bitmap.getHeight() + prog, false);
        return resizedBitmap;
    }
}
