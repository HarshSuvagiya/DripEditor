package com.scorpion.dripeditor;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

public class MyUtils {
    static Context cn;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref;
    public static Bitmap selectedBit;
    public static boolean dripeffect=true;

    public MyUtils(Context context) {
        cn = context;
        pref = context.getSharedPreferences(context.getPackageName(), 0);
        editor = pref.edit();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
    }




    public static Bitmap getBitmapFromView(View view) {
        try {
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            return createBitmap;
        } catch (Exception unused) {
            return null;
        }
    }


    public static void setsize(Context context, View view, int i, Boolean bool) {
        if (bool.booleanValue()) {
            view.getLayoutParams().height = getWidth(context, i);
            view.getLayoutParams().width = getWidth(context, i);
            return;
        }
        view.getLayoutParams().height = getHeight(context, i);
        view.getLayoutParams().width = getHeight(context, i);
    }

    public static int getHeight(Context context, int i) {
        return (context.getResources().getDisplayMetrics().heightPixels * i) / 1920;
    }

    public static int getWidth(Context context, int i) {
        return (context.getResources().getDisplayMetrics().widthPixels * i) / 1080;
    }

    public static void setmargin(Context context, View view, int i, int i2, int i3, int i4) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(getWidth(context, i), getHeight(context, i2), getWidth(context, i3), getHeight(context, i4));
    }

    public static String saveBitmap(Context context, Bitmap bitmap, String str) {
        try {
            String file = Environment.getExternalStorageDirectory().toString();
            Integer.valueOf(0);
            File file2 = new File(file, context.getString(R.string.app_name));
            if (!file2.exists()) {
                file2.mkdir();
            }
            String str2 = gettimestring(System.currentTimeMillis(), "dd_MM_yy_ss");
            File file3 = new File(file, context.getString(R.string.app_name) + "/" + str + str2 + ".png");
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast(context, "Saved Successfully");
            String absolutePath = file3.getAbsolutePath();
            MediaScannerConnection.scanFile(context, new String[]{file3.getPath()}, new String[]{"image/png"}, (MediaScannerConnection.OnScanCompletedListener) null);
            return absolutePath;
        } catch (Exception unused) {
            Toast(context, "Failed to Save");
            return null;
        }
    }


    public static Bitmap getBitmapFromAsset(Context context, String str) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(str));
        } catch (IOException unused) {
            Log.e("", "");
            return null;
        }
    }

    public static Bitmap getBitmapResize(Context context, Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width >= height) {
            int i3 = (height * i) / width;
            if (i3 > i2) {
                i = (i * i2) / i3;
            } else {
                i2 = i3;
            }
        } else {
            int i4 = (width * i2) / height;
            if (i4 > i) {
                i2 = (i2 * i) / i4;
            } else {
                i = i4;
            }
        }
        return Bitmap.createScaledBitmap(bitmap, i, i2, true);
    }





    public static void shareMyMp4(Context context, File file) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("video/mp4");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
            context.startActivity(Intent.createChooser(intent, "Share Video"));
        } catch (Exception e) {
            Log.e("Err", e.toString());
        }
    }


    public static void shareAnyFile(Context context, String str) {
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        Intent intent = new Intent("android.intent.action.SEND");
        String substring = str.substring(str.lastIndexOf(".") + 1, str.length());
        String mimeTypeFromExtension = singleton.getMimeTypeFromExtension(substring);
        if (substring.equalsIgnoreCase("pdf")) {
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(str)));
            intent.setType(mimeTypeFromExtension);
            intent.setFlags(268435456);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Toast(context, "No handler for this type of file.");
            }
        } else if (substring.equalsIgnoreCase("png") || substring.equalsIgnoreCase("jpg")) {
            intent.setType(mimeTypeFromExtension);
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(str)));
            intent.setFlags(268435456);
            context.startActivity(Intent.createChooser(intent, "Share Image"));
        } else {
            shareMyMp4(context, new File(str));
        }
    }




    public static Bitmap getMask(Context context, Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i2, true);
        Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, i, i2, true);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap2.getWidth(), createScaledBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode((Xfermode) null);
        return createBitmap;
    }




    public static void Toast(Context context, String str) {
        Toast(context, str, 0);
    }

    public static void Toast(Context context, String str, int i) {
        Toast.makeText(context, str, i).show();
    }


    public static String gettimestring(long j, String str) {
        return new SimpleDateFormat(str).format(Long.valueOf(j));
    }


    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e(TAG, "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



    public static void deleteFile(Context context, String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            MediaScannerConnection.scanFile(context, new String[]{str}, (String[]) null, (MediaScannerConnection.OnScanCompletedListener) null);
        }
    }



    public static void sortArray(ArrayList<File> arrayList) {
        Collections.sort(arrayList, new Comparator<File>() {
            public int compare(File file, File file2) {
                long lastModified = file.lastModified() - file2.lastModified();
                if (lastModified > 0) {
                    return -1;
                }
                return lastModified == 0 ? 0 : 1;
            }
        });
    }

    public static void setLSquare(Context context, View view, int i) {
        view.setLayoutParams(new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().widthPixels * i) / 1080));
    }

}
