package com.xpro.camera.lite.utils;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class C7572C {
    private static void m26521a(String str, String str2) {
    }

    public static String m26520a(Context context, String str, String[] strArr) {
        String str2 = context.getFilesDir().getAbsolutePath() + "/" + str;
        File file = new File(str2);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        try {
            for (String str3 : strArr) {
                String str4 = str2 + "/" + str3;
                if (!new File(str4).exists()) {
                    InputStream open = context.getAssets().open("modle/" + str3);
                    FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + str3);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = open.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    fileOutputStream.close();
                    open.close();
                    if (str4.contains(".7z")) {
                        m26521a(str2 + File.separator + str3, str2 + File.separator);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str2;
    }
}
