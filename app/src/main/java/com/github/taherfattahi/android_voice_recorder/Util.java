package com.github.taherfattahi.android_voice_recorder;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @author taher fattahi
 */
public class Util {

    public static void requestPermission(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

}