package yicheng.android.app.pulsh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.fancybuttons.FancyButton;
import yicheng.android.app.pulsh.R;

/**
 * Created by ZhangY on 10/11/2015.
 */
public class LoginActivity extends Activity{
    FancyButton login_share_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initiateComponents();

        setComponentControl();
    }

    private void initiateComponents(){
        login_share_button = (FancyButton) findViewById(R.id.login_share_button);

    }

    private void setComponentControl(){
        login_share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShareIntent();
            }
        });
    }

    private void startShareIntent(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/lancevalour/momentum");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
