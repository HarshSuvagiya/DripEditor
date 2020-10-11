package com.scorpion.dripeditor.Activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;

import java.io.File;

public class Utils {
     public static Bitmap bitmap;
    public static boolean ratechk=true;
    public static Bitmap bmp;
    public static String chkdripbg="drip";
    public static Bitmap finalEditedBitmapImage;
    public static File file;
    public static int getScreenWidth() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }
    public static boolean check;

    public static Uri selectedImageUri;
    public static Bitmap[] bm = new Bitmap[100];
    public static Bitmap[] maskbit = new Bitmap[100];

    public static Integer[] seek = new Integer[100];
    public static Integer[] seek1 = new Integer[100];

}
