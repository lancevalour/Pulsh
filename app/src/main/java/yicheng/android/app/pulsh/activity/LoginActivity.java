package yicheng.android.app.pulsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.nvanbenschoten.motion.ParallaxImageView;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import org.kohsuke.github.GitHub;

import java.io.IOException;

import mehdi.sakout.fancybuttons.FancyButton;
import yicheng.android.app.pulsh.R;
import yicheng.android.app.pulsh.model.HawkPersistence;

/**
 * Created by ZhangY on 10/11/2015.
 */
public class LoginActivity extends Activity {
    ParallaxImageView login_imageView;


    FancyButton login_login_button;
    TextInputLayout login_username_layout, login_password_layout;

    GitHub github;

    Handler handler;

    String backgroundImageURL =
            "http://www.bluthemes.com/assets/img/blog/12/rocket-night.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setHandlerControl();

        initiateComponents();


        setComponentControl();
    }

    private void saveLocalUserLogin() {
        Hawk.init(this).setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSharedPrefStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();

        Hawk.chain().put(HawkPersistence.KEY_IS_LOGGED_IN, true)
                .put(HawkPersistence.KEY_USERNAME, login_username_layout.getEditText().getText().toString().trim())
                .put(HawkPersistence.KEY_PASSWORD, login_password_layout.getEditText().getText().toString().trim())
                .commit();
    }

    private void setHandlerControl() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1: {
                        if (isAuthenticated) {
                            saveLocalUserLogin();

                            goToNavigationDrawerActivity();
                        }
                    }
                    break;
                }
            }
        };
    }

    private void initiateComponents() {
        login_imageView = (ParallaxImageView) findViewById(R.id.login_imageView);
      /*  Picasso.with(getBaseContext())
                .load(R.drawable.favorite)
                .into(login_imageView);*/

        login_imageView.registerSensorManager();

        login_login_button = (FancyButton) findViewById(R.id.login_login_button);
        login_username_layout = (TextInputLayout) findViewById(R.id.login_username_layout);
        login_password_layout = (TextInputLayout) findViewById(R.id.login_password_layout);


    }

    private void setComponentControl() {
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                authenticateUser();

            }
        });
    }


    private void startShareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/lancevalour/momentum");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    private void goToNavigationDrawerActivity() {
        Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
        startActivity(intent);
    }

    boolean isAuthenticated = false;

    private boolean authenticateUser() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            github = GitHub.connectUsingPassword(login_username_layout.getEditText().getText().toString().trim(),
                                    login_password_layout.getEditText().getText().toString().trim());

                            isAuthenticated = github.isCredentialValid();

                            System.out.println(isAuthenticated);

                            Message msg = Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        login_imageView.unregisterSensorManager();
    }
}
