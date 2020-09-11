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

public class GROWUP_MyUtils {
    static Context cn;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences pref;
    public static Bitmap selectedBit;

    public interface OnRefreshComplete {
        void Complete();
    }

    public GROWUP_MyUtils(Context context) {
        cn = context;
        pref = context.getSharedPreferences(context.getPackageName(), 0);
        editor = pref.edit();
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
    }

    public static Bitmap getBitmapwithAlpha(Bitmap bitmap, int i) {
        if (!bitmap.isMutable()) {
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        new Canvas(bitmap).drawColor((i & 255) << 24, PorterDuff.Mode.DST_IN);
        return bitmap;
    }

    public static Bitmap gettextbitmap(String str, int i, Typeface typeface, int i2, int i3, int i4) {
        TextPaint textPaint = new TextPaint(65);
        textPaint.setStyle(Paint.Style.FILL);
        if (typeface != null) {
            textPaint.setTypeface(typeface);
        }
        textPaint.setColor(i);
        textPaint.setTextSize(80.0f);
        if (i2 >= 0 && i3 >= 0) {
            textPaint.setShadowLayer((float) i4, (float) i2, (float) i3, Color.parseColor("#000000"));
        }
        int measureText = (int) textPaint.measureText(str);
        StaticLayout staticLayout = new StaticLayout(str, textPaint, measureText, Layout.Alignment.ALIGN_CENTER, 1.0f, 4.0f, true);
        Bitmap createBitmap = Bitmap.createBitmap(measureText, staticLayout.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(65);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0);
        canvas.drawPaint(paint);
        canvas.save();
        canvas.translate(0.0f, 0.0f);
        staticLayout.draw(canvas);
        canvas.restore();
        return createBitmap;
    }

    public static void openVideo(Context context, String str) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(str), "video/*");
            context.startActivity(intent);
        } catch (Exception unused) {
            Toast(context, "can't play from here");
        }
    }

    public static void openFile(Context context, String str) {
        MimeTypeMap singleton = MimeTypeMap.getSingleton();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(str)), singleton.getMimeTypeFromExtension(str.substring(str.lastIndexOf(".") + 1, str.length())));
        intent.setFlags(268435456);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast(context, "No handler for this type of file.");
        }
    }

    public static int getgridEqualSpace(Context context, int i, int i2) {
        int i3 = context.getResources().getDisplayMetrics().widthPixels;
        return (i3 - (((i * i3) / 1080) * i2)) / (i2 + 1);
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

    public static Bitmap getBitmapFromText(String str, float f, int i, Typeface typeface) {
        Paint paint = new Paint(1);
        paint.setTextSize(f);
        paint.setColor(i);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        float f2 = -paint.ascent();
        Bitmap createBitmap = Bitmap.createBitmap((int) (paint.measureText(str) + 0.5f), (int) (paint.descent() + f2 + 0.5f), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawText(str, 0.0f, f2, paint);
        return createBitmap;
    }

    public static Bitmap getBitmapFromText(String str, float f, int i, Typeface typeface, int i2) {
        Paint paint = new Paint(1);
        paint.setTextSize(f);
        paint.setColor(i);
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setShadowLayer(12.0f, 0.0f, 0.0f, i2);
        float f2 = -paint.ascent();
        Bitmap createBitmap = Bitmap.createBitmap((int) (paint.measureText(str) + 0.5f), (int) (paint.descent() + f2 + 0.5f), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawText(str, 0.0f, f2, paint);
        return createBitmap;
    }

    public static String getmilliSecondsToTimer(long j) {
        String str;
        String str2;
        int i = (int) (j / 3600000);
        long j2 = j % 3600000;
        int i2 = ((int) j2) / 60000;
        int i3 = (int) ((j2 % 60000) / 1000);
        if (i > 0) {
            String str3 = i + ":";
            if (i < 10) {
                str = "0" + i + ":";
            } else {
                str = str3;
            }
        } else {
            str = "";
        }
        if (i3 < 10) {
            str2 = "0" + i3;
        } else {
            str2 = "" + i3;
        }
        String str4 = "" + i2;
        if (i2 < 10) {
            str4 = "0" + i2;
        }
        return str + str4 + ":" + str2;
    }

    public static boolean isAccessibilitySettingsOn(Context context) {
        int i;
        String string;
        try {
            i = Settings.Secure.getInt(context.getContentResolver(), "accessibility_enabled");
        } catch (Settings.SettingNotFoundException unused) {
            i = 0;
        }
        if (i != 1 || (string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services")) == null) {
            return false;
        }
        return string.toLowerCase().contains(context.getPackageName().toLowerCase());
    }

    public static void getPermissionAccessibilty(Activity activity, int i) {
        new Intent().setFlags(268435456);
        activity.startActivityForResult(new Intent("android.settings.ACCESSIBILITY_SETTINGS"), i);
    }

    public static void getPermissionAccessibiltyonoff(Context context, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        Settings.Secure.putString(contentResolver, "enabled_accessibility_services", context.getPackageName() + "/Myaccesibility");
        Settings.Secure.putString(context.getContentResolver(), "accessibility_enabled", str);
    }

    public static Bitmap getBitmapfromPath(String str) {
        File file = new File(str);
        return BitmapFactory.decodeFile(file.getAbsolutePath(), new BitmapFactory.Options());
    }

    public static Bitmap getBitmapfromPath(String str, int i, int i2) {
        File file = new File(str);
        return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), new BitmapFactory.Options()), i, i2, true);
    }

    public static void pickImagefromGallery(Activity activity, int i) {
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, i);
    }

    public static void pickVideofromGallery(Activity activity, int i) {
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        activity.startActivityForResult(intent, i);
    }

    public static void pickFilefromType(Activity activity, int i, String str) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType(str);
        activity.startActivityForResult(Intent.createChooser(intent, "ChooseFile"), i);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    public static void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(activity);
        }
        inputMethodManager.showSoftInputFromInputMethod(currentFocus.getWindowToken(), 0);
    }

    public static void getOverlayPermission(Activity activity, int i) {
        Boolean.valueOf(true);
        Context applicationContext = activity.getApplicationContext();
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (!Settings.canDrawOverlays(applicationContext)) {
            Boolean.valueOf(false);
            activity.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + applicationContext.getPackageName())), i);
            return;
        }
        Boolean.valueOf(true);
    }

    public static Boolean checkoverlay(Activity activity) {
        Context applicationContext = activity.getApplicationContext();
        if (Build.VERSION.SDK_INT >= 23) {
            return Boolean.valueOf(Settings.canDrawOverlays(applicationContext));
        }
        return true;
    }

    public static boolean checkSystemWritePermission(Context context, int i) {
        boolean canWrite = Build.VERSION.SDK_INT >= 23 ? Settings.System.canWrite(context) : true;
        if (i >= 0 && !canWrite) {
            permissionSystemSettings((Activity) context, i);
        }
        return canWrite;
    }

    public static void permissionSystemSettings(Activity activity, int i) {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, i);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static void addContact(Context context, String str, String str2, Uri uri) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue("account_type", (Object) null).withValue("account_name", (Object) null).build());
        if (str != null) {
            arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/name").withValue("data1", str).build());
        }
        if (str2 != null) {
            arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/phone_v2").withValue("data1", str2).withValue("data2", 2).build());
        }
        if (uri != null) {
            try {
                arrayList.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference("raw_contact_id", 0).withValue("mimetype", "vnd.android.cursor.item/photo").withValue("data15", getBytes(context.getContentResolver().openInputStream(uri))).build());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            context.getContentResolver().applyBatch("com.android.contacts", arrayList);
        } catch (Exception e3) {
            e3.printStackTrace();
            Toast.makeText(context, "Exception: " + e3.getMessage(), 0).show();
        }
    }

    public static void makeCall(Context context, String str) {
        Intent intent = new Intent("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + str));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static void makeSms(Context context, String str) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.fromParts("sms", str, (String) null)));
    }

    public static void setsize(Context context, View view, int i, int i2) {
        view.getLayoutParams().height = getHeight(context, i2);
        view.getLayoutParams().width = getWidth(context, i);
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

    public static String saveBitmappath(Context context, Bitmap bitmap, String str) {
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
            return file3.getAbsolutePath();
        } catch (Exception unused) {
            Toast(context, "Failed to Save");
            return null;
        }
    }

    public static ArrayList<Bitmap> getImageFromAsset(Context context, String str) {
        ArrayList<Bitmap> arrayList = new ArrayList<>();
        String[] strArr = new String[0];
        try {
            strArr = context.getAssets().list(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Arrays.asList(strArr).size(); i++) {
            arrayList.add(getBitmapFromAsset(context, str + "/" + ((String) Arrays.asList(strArr).get(i))));
        }
        return arrayList;
    }

    public static Bitmap getColorBit(String str) {
        int parseColor = Color.parseColor(str);
        Bitmap createBitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawColor(parseColor);
        return createBitmap;
    }

    public static Bitmap getBitmapFromAsset(Context context, String str) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(str));
        } catch (IOException unused) {
            Log.e("", "");
            return null;
        }
    }

    public static Bitmap bitmapOverlay(Bitmap bitmap, Bitmap bitmap2, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, new Matrix(), (Paint) null);
        canvas.drawBitmap(bitmap2, (float) (i - (bitmap2.getWidth() / 2)), (float) (i2 - (bitmap2.getHeight() / 2)), (Paint) null);
        return createBitmap;
    }

    public static Bitmap getRounndCroppedBitmap(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawCircle((float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2), (float) (bitmap.getWidth() / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static Uri getImageUri(Context context, Bitmap bitmap) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
        try {
            return Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", (String) null));
        } catch (Exception e) {
            Log.e("AAA", e.toString());
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

    public static Bitmap RationHeightWidth(Context context, Bitmap bitmap, int i, int i2) {
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

    public static boolean checknotificationaccess(Context context) {
        boolean z;
        try {
            z = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners").contains(context.getPackageName());
        } catch (Exception unused) {
            z = false;
        }
        if (!z) {
            String string = context.getString(R.string.app_name);
            Toast(context, string + " Select and Give Permission", 1);
            context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        return z;
    }

    public static String dtformt(Long l) {
        return time_msg(l.longValue(), System.currentTimeMillis(), 1000).toString();
    }

    private static CharSequence time_msg(long j, long j2, long j3) {
        StringBuilder sb = new StringBuilder();
        Resources.getSystem();
        int i = j2 >= j ? 1 : null;
        long abs = Math.abs(j2 - j);
        if (abs < 60000 && j3 < 60000) {
            long j4 = abs / 1000;
            if (j4 <= 10) {
                sb.append("Just Now");
            } else if (i != 0) {
                sb.append(j4);
                sb.append("s");
            } else {
                sb.append("Just Now");
            }
        } else if (abs < 3600000 && j3 < 3600000) {
            long j5 = abs / 60000;
            if (i != 0) {
                sb.append(j5);
                sb.append("m");
            } else {
                sb.append("Just Now");
            }
        } else if (abs < 86400000 && j3 < 86400000) {
            long j6 = abs / 3600000;
            if (i != 0) {
                sb.append(j6);
                sb.append("h");
            } else {
                sb.append("Just Now");
            }
        } else if (abs >= 604800000 || j3 >= 604800000) {
            sb.append(DateUtils.formatDateRange((Context) null, j, j, 0));
        } else {
            sb.append(DateUtils.getRelativeTimeSpanString(j, j2, j3));
        }
        return sb.toString();
    }

    public static String getContactName(Context context, String str) {
        Cursor query = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{"display_name"}, (String) null, (String[]) null, (String) null);
        if (query == null) {
            return str;
        }
        if (query.moveToFirst()) {
            str = query.getString(query.getColumnIndex("display_name"));
        }
        if (query != null && !query.isClosed()) {
            query.close();
        }
        return str;
    }

    public static boolean generatetxt(Context context, String str, String str2, String str3) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileWriter fileWriter = new FileWriter(new File(file, str2));
            fileWriter.append(str3);
            fileWriter.flush();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sharetextonWhatsApp(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.TEXT", str);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast(context, "Whatsapp have not been installed.");
        }
    }

    public static void shareImage(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + str));
        context.startActivity(Intent.createChooser(intent, "Share Image"));
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

    public static void shareMyMp3(Context context, File file) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("music/mp3");
            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
            context.startActivity(Intent.createChooser(intent, "Share Mp3"));
        } catch (Exception e) {
            Log.e("Err", e.toString());
        }
    }

    public static void shareGif(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/gif");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(str)));
        context.startActivity(Intent.createChooser(intent, "Share Emoji"));
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

    public static void share(Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        context.startActivity(Intent.createChooser(intent, "Share via"));
    }

    public static void rate(Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public static RelativeLayout.LayoutParams getParamsR(Context context, int i, int i2) {
        return new RelativeLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().heightPixels * i2) / 1920);
    }

    public static LinearLayout.LayoutParams getParamsL(Context context, int i, int i2) {
        return new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().heightPixels * i2) / 1920);
    }

    public static LinearLayout.LayoutParams getParamsLSquare(Context context, int i) {
        return new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().widthPixels * i) / 1080);
    }

    public static LinearLayout.LayoutParams getParamsLSquareH(Context context, int i) {
        return new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().heightPixels * i) / 1920, (context.getResources().getDisplayMetrics().heightPixels * i) / 1920);
    }

    public static RelativeLayout.LayoutParams getParamsRSquare(Context context, int i) {
        return new RelativeLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().widthPixels * i) / 1080);
    }

    public static void copyClipboard(Context context, String str) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("copy", str));
        Toast(context, "Copy Message...");
    }

    public static String getNumbers(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    public static Bitmap getMask(Context context, Bitmap bitmap, int i, View view) {
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), i);
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(decodeResource, width, height, true);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap2.getWidth(), createScaledBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode((Xfermode) null);
        return createBitmap;
    }

    public static Bitmap getMask(Context context, Bitmap bitmap, Bitmap bitmap2, View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        Bitmap createScaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, width, height, true);
        Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap2.getWidth(), createScaledBitmap2.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(createScaledBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawBitmap(createScaledBitmap2, 0.0f, 0.0f, paint);
        paint.setXfermode((Xfermode) null);
        return createBitmap;
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

    public static String downloadFile(String str, String str2) {
        try {
            URL url = new URL(str);
            int contentLength = url.openConnection().getContentLength();
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            byte[] bArr = new byte[contentLength];
            dataInputStream.readFully(bArr);
            dataInputStream.close();
            File file = new File(Environment.getExternalStorageDirectory() + "/" + str2);
            if (!file.exists()) {
                file.mkdir();
            }
            File file2 = new File(file, "vdo_" + System.currentTimeMillis());
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file2));
            dataOutputStream.write(bArr);
            dataOutputStream.flush();
            dataOutputStream.close();
            return file2.getAbsolutePath();
        } catch (IOException unused) {
            return null;
        }
    }

    public static void copyRAWtoSDCard(Context context, String str, int i) throws IOException {
        String str2 = Environment.getExternalStorageDirectory() + "/" + str;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(str2 + "/vdo_" + System.currentTimeMillis() + ".mp4");
        InputStream openRawResource = context.getResources().openRawResource(i);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = openRawResource.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            } finally {
                openRawResource.close();
                fileOutputStream.close();
            }
        }
    }

    public static String copyRAWtoSDCard(Context context, String str, String str2, String str3) throws IOException {
        String str4 = Environment.getExternalStorageDirectory() + "/" + str;
        File file = new File(str4);
        if (!file.exists()) {
            file.mkdir();
        }
        String str5 = str4 + "/" + str2 + ".mp4";
        File file2 = new File(str5);
        InputStream openRawResource = context.getResources().openRawResource(Integer.parseInt(str3.substring(str3.lastIndexOf("/") + 1, str3.length())));
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = openRawResource.read(bArr);
                if (read <= 0) {
                    return str5;
                }
                fileOutputStream.write(bArr, 0, read);
            } finally {
                openRawResource.close();
                fileOutputStream.close();
            }
        }
    }

//    public static String copyFileUsingStream(File file, String str, String str2) throws IOException {
//        FileOutputStream fileOutputStream;
//        FileOutputStream fileOutputStream2;
//        FileInputStream fileInputStream = null;
//        try {
//            String str3 = Environment.getExternalStorageDirectory() + "/" + str;
//            File file2 = new File(str3);
//            if (!file2.exists()) {
//                file2.mkdir();
//            }
//            File file3 = new File(str3 + "/" + str2);
//            String absolutePath = file3.getAbsolutePath();
//            FileInputStream fileInputStream2 = new FileInputStream(file);
//            try {
//                fileOutputStream2 = new FileOutputStream(file3);
//            } catch (Throwable th) {
//                th = th;
//                fileOutputStream = null;
//                fileInputStream = fileInputStream2;
//                fileInputStream.close();
//                fileOutputStream.close();
//                throw th;
//            }
//            try {
//                byte[] bArr = new byte[1024];
//                while (true) {
//                    int read = fileInputStream2.read(bArr);
//                    if (read > 0) {
//                        fileOutputStream2.write(bArr, 0, read);
//                    } else {
//                        MediaScannerConnection.scanFile(cn, new String[]{file3.getAbsolutePath()}, (String[]) null, (MediaScannerConnection.OnScanCompletedListener) null);
//                        fileInputStream2.close();
//                        fileOutputStream2.close();
//                        return absolutePath;
//                    }
//                }
//            } catch (Throwable th2) {
//                fileInputStream = fileInputStream2;
//                Throwable th3 = th2;
//                fileOutputStream = fileOutputStream2;
//                th = th3;
//                fileInputStream.close();
//                fileOutputStream.close();
//                throw th;
//            }
//        } catch (Throwable th4) {
//            th = th4;
//            fileOutputStream = null;
//            fileInputStream.close();
//            fileOutputStream.close();
//            throw th;
//        }
//    }

    public static ArrayList<File> getListFiles(String str, String str2) {
        ArrayList<File> arrayList = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory() + "/" + str);
        if (!file.exists()) {
            file.mkdir();
        }
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    arrayList.addAll(getListFiles(file2));
                } else {
                    String absolutePath = file2.getAbsolutePath();
                    if (absolutePath.substring(absolutePath.lastIndexOf(".") + 1, absolutePath.length()).equalsIgnoreCase(str2)) {
                        arrayList.add(file2);
                    }
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<File> getListFiles(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    arrayList.addAll(getListFiles(file2));
                } else {
                    arrayList.add(file2);
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getListFiles(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        File file = new File(str);
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    arrayList.addAll(getListFiles(file2.getPath()));
                } else {
                    arrayList.add(file2.getPath());
                }
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getFolderfiles(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        File file = new File(str);
        if (file.exists()) {
            for (File file2 : file.listFiles()) {
                if (!file2.isDirectory()) {
                    arrayList.add(file2.getPath());
                }
            }
        }
        return arrayList;
    }

    public static String getFileExtension(String str) {
        String name = new File(str).getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        if (fileExtensionFromUrl != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
        }
        return null;
    }

    public static boolean checkPermission(Context context, String[] strArr) {
        int length = strArr.length;
        boolean z = true;
        for (int i = 0; i < length; i++) {
            z = ActivityCompat.checkSelfPermission(context, strArr[i]) == 0;
            if (!z) {
                break;
            }
        }
        if (!z) {
            ActivityCompat.requestPermissions((Activity) context, strArr, 101);
        }
        return z;
    }

    public static void ToastImage(Context context, Bitmap bitmap) {
        Toast toast = new Toast(context);
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);
        toast.setView(imageView);
        toast.show();
    }

    public static void Toast(Context context, String str) {
        Toast(context, str, 0);
    }

    public static void Toast(Context context, String str, int i) {
        Toast.makeText(context, str, i).show();
    }

    public static long gettimeskip(int i) {
        Calendar instance = Calendar.getInstance();
        instance.getTimeInMillis();
        instance.add(12, i);
        instance.set(13, 0);
        return instance.getTimeInMillis();
    }

    public static long getskipday(int i) {
        Calendar instance = Calendar.getInstance();
        instance.getTimeInMillis();
        instance.add(5, i);
        instance.set(13, 0);
        return instance.getTimeInMillis();
    }

    public static String gettimestring(long j, String str) {
        return new SimpleDateFormat(str).format(Long.valueOf(j));
    }

    public static long getdatefromstring(String str, String str2) throws ParseException {
        return new SimpleDateFormat(str).parse(str2).getTime();
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

//    public static String getRealPathFromURI(Context context, Uri contentUri) {
//        if ( contentUri.toString().indexOf("file:///") > -1 ){
//            return contentUri.getPath();
//        }
//
//        Cursor cursor = null;
//        try {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (!Boolean.valueOf(connectivityManager.getActiveNetworkInfo() != null).booleanValue()) {
            Toast(context, "Need Internet Connection");
        }
        if (connectivityManager.getActiveNetworkInfo() != null) {
            return true;
        }
        return false;
    }

    public static Uri getBitmapUri(Context context, Bitmap bitmap) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
        return Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", (String) null));
    }

    public static void setBrigtness(Context context, int i) {
        int i2 = (int) ((((float) i) * 255.0f) / 100.0f);
        ContentResolver contentResolver = context.getContentResolver();
        Window window = ((Activity) context).getWindow();
        Settings.System.putInt(contentResolver, "screen_brightness", i2);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.screenBrightness = (float) i2;
        Log.e("AAA", "Brigtness : " + i2);
        window.setAttributes(attributes);
    }

    public static void setAutoBrigtness(Context context) {
        Settings.System.putInt(context.getContentResolver(), "screen_brightness_mode", 1);
    }

    public static int getBrigtness(Context context) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Settings.System.putInt(contentResolver, "screen_brightness_mode", 0);
            return Settings.System.getInt(contentResolver, "screen_brightness");
        } catch (Settings.SettingNotFoundException e) {
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean hasWriteSettingsPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.System.canWrite(context);
        }
        return ContextCompat.checkSelfPermission(context, "android.permission.WRITE_SETTINGS") == 0;
    }

    public static void getWriteSettingsPermission(Context context, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            ((Activity) context).startActivityForResult(intent, i);
            return;
        }
        ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.WRITE_SETTINGS"}, i);
    }

    public static void setAutoRotation(Context context, Boolean bool) {
        Settings.System.putInt(context.getContentResolver(), "accelerometer_rotation", bool.booleanValue() ? 1 : 0);
    }

    public static boolean getAutoRotationOnOff(Context context) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), "accelerometer_rotation");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            i = 0;
        }
        if (i == 1) {
            return true;
        }
        return false;
    }

    public static Boolean hasGpsEnable(Context context) {
        return Boolean.valueOf(((LocationManager) context.getSystemService("location")).isProviderEnabled("gps"));
    }

    public static void getGpsManager(Context context, int i) {
        ((Activity) context).startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), i);
    }

    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < 17) {
            if (Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0) {
                return true;
            }
            return false;
        } else if (Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void setFlightnMode(Context context, int i) {
        Intent intent = new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
        intent.setFlags(268435456);
        ((Activity) context).startActivityForResult(intent, i);
    }

    public static float getBatteryTemperature() {
        Intent batteryStatusIntent = getBatteryStatusIntent(cn);
        if (batteryStatusIntent == null) {
            return 0.0f;
        }
        double intExtra = (double) batteryStatusIntent.getIntExtra("temperature", 0);
        Double.isNaN(intExtra);
        return (float) (intExtra / 10.0d);
    }

    public static int getBatteryVoltage() {
        Intent batteryStatusIntent = getBatteryStatusIntent(cn);
        if (batteryStatusIntent != null) {
            return batteryStatusIntent.getIntExtra("voltage", 0);
        }
        return 0;
    }

    public static String getBatteryChargingSource() {
        int intExtra = getBatteryStatusIntent(cn).getIntExtra("plugged", 0);
        if (intExtra == 1) {
            return "AC";
        }
        if (intExtra != 2) {
            return intExtra != 4 ? "--" : "WIRELESS";
        }
        return "USB";
    }

    public static Intent getBatteryStatusIntent(Context context) {
        return context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    public static Boolean getConnectedStats(Context context) {
        return Boolean.valueOf(context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.hardware.usb.action.USB_STATE")).getExtras().getBoolean("connected"));
    }

    public static void setScreenTimeout(Context context, int i) {
        Settings.System.putInt(context.getContentResolver(), "screen_off_timeout", i * 1000);
    }

    public static void setMobileDataEnabled(Context context, boolean z) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (z) {
            if (isMobileDataOnOff(context).booleanValue()) {
                return;
            }
        } else if (!isMobileDataOnOff(context).booleanValue()) {
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        Field declaredField = Class.forName(connectivityManager.getClass().getName()).getDeclaredField("mService");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(connectivityManager);
        Method declaredMethod = Class.forName(obj.getClass().getName()).getDeclaredMethod("setMobileDataEnabled", new Class[]{Boolean.TYPE});
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(obj, new Object[]{Boolean.valueOf(z)});
    }

    public static Boolean isMobileDataOnOff(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        boolean z = false;
        try {
            Method declaredMethod = Class.forName(connectivityManager.getClass().getName()).getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            z = ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
        } catch (Exception unused) {
        }
        return Boolean.valueOf(z);
    }

    public static Bitmap getBitmapfromUri(Context context, Uri uri) {
        String[] strArr = {"_data"};
        Cursor query = context.getContentResolver().query(uri, strArr, (String) null, (String[]) null, (String) null);
        query.moveToFirst();
        String string = query.getString(query.getColumnIndex(strArr[0]));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap decodeFile = BitmapFactory.decodeFile(string, options);
        query.close();
        return decodeFile;
    }

    public static void deleteFile(Context context, String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            MediaScannerConnection.scanFile(context, new String[]{str}, (String[]) null, (MediaScannerConnection.OnScanCompletedListener) null);
        }
    }

    public static String formatTimeUnit(long j) throws ParseException {
        return String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j)))});
    }

    public static void launchApp(Context context, String str) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context.startActivity(launchIntentForPackage);
        }
    }

    public static String getAppnameFromPckg(Context context, String str) {
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 0);
        } catch (Exception unused) {
            applicationInfo = null;
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "(unknown)");
    }

    public static int getCountfromString(String str, String str2) {
        int i = 0;
        while (Pattern.compile("[^" + str2 + "]*" + str2).matcher(str).find()) {
            i++;
        }
        return i;
    }

    public static Boolean checkAppOverUsagePermission(Activity activity, int i) {
        Boolean valueOf = Boolean.valueOf(isAccessGranted(activity));
        if (valueOf.booleanValue()) {
            return valueOf;
        }
        try {
            Toast(activity, "Need Prmission");
            Intent intent = new Intent();
            intent.setAction("android.settings.USAGE_ACCESS_SETTINGS");
            activity.startActivityForResult(intent, i);
            return valueOf;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean getstatusAppUsagePermission(Context context) {
        AppOpsManager appOpsManager;
        if (Build.VERSION.SDK_INT < 21 || (appOpsManager = (AppOpsManager) context.getSystemService("appops")) == null || appOpsManager.checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName()) != 0) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        List<UsageStats> queryUsageStats = ((UsageStatsManager) context.getSystemService("usagestats")).queryUsageStats(0, currentTimeMillis - 10000, currentTimeMillis);
        Log.e("AAA", "Count : " + queryUsageStats.size());
        Boolean valueOf = Boolean.valueOf(queryUsageStats != null && queryUsageStats.size() > 0);
        Log.e("AAA", "Permission : " + valueOf);
        if (queryUsageStats == null || queryUsageStats.size() <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isAccessGranted(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            if ((Build.VERSION.SDK_INT > 19 ? ((AppOpsManager) context.getSystemService("appops")).checkOpNoThrow("android:get_usage_stats", applicationInfo.uid, applicationInfo.packageName) : 0) == 0) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
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

    public static File makeBaseFolder(Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File file = new File(externalStorageDirectory.getAbsolutePath() + "/" + context.getResources().getString(R.string.app_name));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static void setRingtone(Context context, String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        String substring2 = substring.substring(0, substring.lastIndexOf("."));
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("title", substring2);
        contentValues.put("_size", Integer.valueOf(str.length()));
        contentValues.put("mime_type", "audio/mp3");
        contentValues.put("artist", context.getString(R.string.app_name));
        contentValues.put("is_ringtone", true);
        contentValues.put("is_notification", false);
        contentValues.put("is_alarm", false);
        contentValues.put("is_music", false);
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Uri contentUriForPath = MediaStore.Audio.Media.getContentUriForPath(str);
            contentResolver.delete(contentUriForPath, "_data=\"" + str + "\"", (String[]) null);
            Uri insert = contentResolver.insert(contentUriForPath, contentValues);
            Log.e("", "=====Enter ====" + insert);
            RingtoneManager.setActualDefaultRingtoneUri(context, 1, insert);
        } catch (Exception e) {
            Log.e("", "Error" + e.getMessage());
        }
    }

    public static String getDecimalPoint(float f, int i) {
        return String.format("%." + i + "f", new Object[]{Float.valueOf(f)});
    }

    public static void setLSquare(Context context, View view, int i) {
        view.setLayoutParams(new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().widthPixels * i) / 1080));
    }

    public static void setLLayout(Context context, View view, int i, int i2) {
        view.setLayoutParams(new LinearLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().heightPixels * i2) / 1920));
    }

    public static void setLLayoutWMATCH(Context context, View view, int i) {
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, (context.getResources().getDisplayMetrics().heightPixels * i) / 1920));
    }

    public static void setRLayout(Context context, View view, int i, int i2) {
        view.setLayoutParams(new RelativeLayout.LayoutParams((context.getResources().getDisplayMetrics().widthPixels * i) / 1080, (context.getResources().getDisplayMetrics().heightPixels * i2) / 1920));
    }

    public static void refreshPath(Context context, String str, final OnRefreshComplete onRefreshComplete) {
        MediaScannerConnection.scanFile(context, new String[]{str}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String str, Uri uri) {
                onRefreshComplete.Complete();
            }
        });
    }

    public static Boolean checkDnd(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 23) {
            return Boolean.valueOf(notificationManager.isNotificationPolicyAccessGranted());
        }
        return true;
    }

    public static void getDndPermission(Context context, int i) {
        NotificationManager notificationManager = (NotificationManager) context.getApplicationContext().getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 23 && !notificationManager.isNotificationPolicyAccessGranted()) {
            ((Activity) context).startActivityForResult(new Intent("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"), i);
        }
    }

    public static void setonoff(boolean z) {
        editor.putBoolean("onoff", z);
        editor.commit();
    }

    public static Boolean getonoff() {
        return Boolean.valueOf(pref.getBoolean("onoff", false));
    }

    public static long getFileSize(Context context, File file) {
        File[] listFiles;
        long j = 0;
        if (file != null && file.exists()) {
            if (!file.isDirectory()) {
                return file.length();
            }
            LinkedList linkedList = new LinkedList();
            linkedList.add(file);
            while (!linkedList.isEmpty()) {
                File file2 = (File) linkedList.remove(0);
                if (!(!file2.exists() || (listFiles = file2.listFiles()) == null || listFiles.length == 0)) {
                    for (File file3 : listFiles) {
                        if (checkIsVideo(context, file3.getAbsolutePath())) {
                            j += file3.length();
                            if (file3.isDirectory()) {
                                linkedList.add(file3);
                            }
                        }
                    }
                }
            }
        }
        return j;
    }

    public static boolean checkIsVideo(Context context, String str) {
        String guessContentTypeFromName = URLConnection.guessContentTypeFromName(str);
        return guessContentTypeFromName != null && guessContentTypeFromName.startsWith("video");
    }

//    public static String humanReadableByteCountBin(long j) {
//        long abs = j == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(j);
//        if (abs < 1024) {
//            return j + " B";
//        }
//        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator("KMGTPE");
//        int i = 40;
//        long j2 = abs;
//        while (i >= 0 && abs > (1152865209611504844 >> i)) {
//            j2 >>= 10;
//            stringCharacterIterator.next();
//            i -= 10;
//        }
//        double signum = (double) (j2 * ((long) Long.signum(j)));
//        Double.isNaN(signum);
//        return String.format("%.1f %cB", new Object[]{Double.valueOf(signum / 1024.0d), Character.valueOf(stringCharacterIterator.current())});
//    }
//
//    public static long getVideoDuration(Context context, File file) {
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(context, Uri.fromFile(file));
//        long parseLong = Long.parseLong(mediaMetadataRetriever.extractMetadata(9));
//        mediaMetadataRetriever.release();
//        return parseLong;
//    }
//
//    public static int getVideoWidth(String str) throws Exception {
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(str);
//        int intValue = Integer.valueOf(mediaMetadataRetriever.extractMetadata(18)).intValue();
//        mediaMetadataRetriever.release();
//        return intValue;
//    }
//
//    public static int getVideoHeight(String str) {
//        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
//        mediaMetadataRetriever.setDataSource(str);
//        int intValue = Integer.valueOf(mediaMetadataRetriever.extractMetadata(19)).intValue();
//        mediaMetadataRetriever.release();
//        return intValue;
//    }
}
