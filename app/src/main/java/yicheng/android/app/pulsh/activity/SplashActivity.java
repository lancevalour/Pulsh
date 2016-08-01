package yicheng.android.app.pulsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.squareup.picasso.Picasso;

import yicheng.android.app.pulsh.R;
import yicheng.android.app.pulsh.model.HawkPersistence;

/**
 * Created by ZhangY on 10/15/2015.
 */
public class SplashActivity extends Activity {

    boolean isLoggedIn;

    String username;
    String password;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        loadLocalUserData();

    }

    private void loadLocalUserData() {
        Hawk.init(this).setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .setCallback(new HawkBuilder.Callback() {
                    @Override
                    public void onSuccess() {
                        isLoggedIn = Hawk.get(HawkPersistence.KEY_IS_LOGGED_IN, false);

                        Toast.makeText(getBaseContext(), String.valueOf(isLoggedIn), Toast.LENGTH_SHORT).show();

                        if (isLoggedIn) {
                            goToNavigationDrawerActivity();
                        } else {
                            goToLoginActivity();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        Toast.makeText(getBaseContext(), "Hawk loading Fail", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();


    }

    private void goToNavigationDrawerActivity() {
        Intent intent = new Intent(SplashActivity.this, NavigationDrawerActivity.class);
        startActivity(intent);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
