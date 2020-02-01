package com.mashup.nnaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.util.PermissionHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PermissionHelper permissionHelper = new PermissionHelper();

        if (permissionHelper.checkPermission(
                this,
                Manifest.permission.READ_CONTACTS)) {
            launchMainActivity();
        } else {
            permissionHelper.requestPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PermissionHelper.REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchMainActivity();
            } else {
                // Contact permission is not granted, terminate application
                Toast.makeText(this, R.string.splash_permission_error, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void launchMainActivity() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
