package yicheng.android.app.pulsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

import mehdi.sakout.fancybuttons.FancyButton;
import yicheng.android.app.pulsh.R;

/**
 * Created by ZhangY on 10/11/2015.
 */
public class LoginActivity extends Activity {
    FancyButton login_login_button;
    TextInputLayout login_username_layout, login_password_layout;

    GitHub github;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setHandlerControl();

        initiateComponents();


        setComponentControl();
    }

    private void setHandlerControl() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1: {
                        if (isAuthenticated) {
                            goToNavigationDrawerActivity();
                        }
                    }
                    break;
                }
            }
        };
    }

    private void initiateComponents() {
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


}
