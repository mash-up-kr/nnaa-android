package com.mashup.nnaa;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mashup.nnaa.main.MainActivity;
import com.mashup.nnaa.util.AccountManager;
import com.mashup.nnaa.util.PermissionHelper;
import com.mashup.nnaa.util.SharedPrefHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkLogin();
    }

    // more than API 23 device check permission
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

    private void checkLogin() {
        AccountManager.getInstance().executeAutoSignIn(new AccountManager.ISignInResultListener() {
            @Override
            public void onSignInSuccess(String id, String name, String token) {
                launchMainActivity();
            }

            @Override
            public void onSignInFail() {
                //Toast.makeText(getBaseContext(), "Fail to auto sign in, please try again", Toast.LENGTH_SHORT).show();
                launchSignInActivity();
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void launchSignInActivity() {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        intent.putExtra("email",
                SharedPrefHelper.getInstance().getString(AccountManager.SHARED_PREF_LAST_ACCOUNT_EMAIL));
        startActivity(intent);
        finish();
    }
}
