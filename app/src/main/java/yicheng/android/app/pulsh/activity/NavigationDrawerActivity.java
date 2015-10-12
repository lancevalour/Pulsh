package yicheng.android.app.pulsh.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import yicheng.android.app.pulsh.R;

/**
 * Created by ZhangY on 10/11/2015.
 */
public class NavigationDrawerActivity extends AppCompatActivity {

    DrawerLayout activity_navigation_drawer_layout;

    FrameLayout activity_navigation_drawer_content_framelayout;

    NavigationView activity_navigation_drawer_navigation_view;

    private final Handler drawerActionHandler = new Handler();

    private static final long DRAWER_CLOSE_DELAY_MS = 250;

    int curDrawerItemId;
    String DRAWER_ID = "drawer_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

   /*     if (null == savedInstanceState) {
            curDrawerItemId = R.id.drawer_market;
        } else {
            curDrawerItemId = savedInstanceState.getInt(DRAWER_ID);
        }*/


        initiateComponents();


        setComponentControl();

    }

    private void initiateComponents(){
        activity_navigation_drawer_layout = (DrawerLayout) findViewById(R.id.activity_navigation_drawer_layout);

        activity_navigation_drawer_content_framelayout = (FrameLayout) findViewById(R.id.activity_navigation_drawer_content_framelayout);

        activity_navigation_drawer_navigation_view = (NavigationView) findViewById(R.id.activity_navigation_drawer_navigation_view);

        activity_navigation_drawer_navigation_view.getMenu().findItem(curDrawerItemId).setChecked(true);

        navigate(curDrawerItemId);

    }


    private void setComponentControl(){
        setNavigationViewControl();
    }


    private void navigate(final int itemId) {
        // perform the actual navigation logic, updating the main content fragment etc
        Fragment frontFragment = null;

        switch (itemId) {
           /* case R.id.drawer_market: {
                frontFragment = new MarketFragment();
            }
            break;
            case R.id.drawer_portfolio: {
                frontFragment = new PortfolioFragment();
            }
            break;
            case R.id.drawer_favorite: {
                frontFragment = new FavoriteFragment();
            }
            break;
            case R.id.drawer_trending: {
                frontFragment = new TrendingFragment();
            }
            break;
            case R.id.drawer_settings: {
                frontFragment = new SettingsFragment();
            }
            break;*/
        }


        getFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.activity_navigation_drawer_content_framelayout,
                        frontFragment).commit();

    }

    private void setNavigationViewControl() {
        activity_navigation_drawer_navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                menuItem.setChecked(true);
                curDrawerItemId = menuItem.getItemId();

                // allow some time after closing the drawer before performing real navigation
                // so the user can see what is happening
                activity_navigation_drawer_layout.closeDrawer(GravityCompat.START);


                drawerActionHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        navigate(curDrawerItemId);
                    }
                }, DRAWER_CLOSE_DELAY_MS);
                return true;
            }
        })
        ;
    }
}
