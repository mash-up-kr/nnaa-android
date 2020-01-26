package com.mashup.nnaa.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionHelper {
    public static final int REQUEST_READ_CONTACTS = 79;

    public void requestPermissions(Activity resultActivity) {
        ActivityCompat.requestPermissions(resultActivity,
                new String[]{android.Manifest.permission.READ_CONTACTS},
                REQUEST_READ_CONTACTS);
    }

    public boolean checkPermission(Context context, String permissionStr) {
        return ActivityCompat.checkSelfPermission(context, permissionStr) == PackageManager.PERMISSION_GRANTED;
    }
}
