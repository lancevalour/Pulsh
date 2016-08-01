package yicheng.android.app.pulsh.activity;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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

import yicheng.android.app.pulsh.R;

/**
 * Created by ZhangY on 10/11/2015.
 */
public class NavigationDrawerActivity extends AppCompatActivity {

    FrameLayout framelayout;

    Drawer drawer;

    private final Handler drawerActionHandler = new Handler();

    private static final long DRAWER_CLOSE_DELAY_MS = 250;

    int curDrawerItemId;
    String DRAWER_ID = "drawer_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

     /*   if (null == savedInstanceState) {
            curDrawerItemId = R.id.drawer_market;
        } else {
            curDrawerItemId = savedInstanceState.getInt(DRAWER_ID);
        }
*/

        initiateComponents();


        setComponentControl();

    }

    private void initiateComponents() {
        framelayout = (FrameLayout) findViewById(R.id.activity_navigation_drawer_framelayout);

        initiateDrawer();


      //  navigate(curDrawerItemId);

    }

    private void initiateDrawer() {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

    /*
    @Override
    public Drawable placeholder(Context ctx) {
        return super.placeholder(ctx);
    }

    @Override
    public Drawable placeholder(Context ctx, String tag) {
        return super.placeholder(ctx, tag);
    }
    */
        });

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("item 1").withIcon(GoogleMaterial.Icon.gmd_wb_sunny);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("item 2").withIcon(GoogleMaterial.Icon.gmd_wb_sunny);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.favorite)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.ic_action_market))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        drawer = new DrawerBuilder().withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(item1, item2, item2, new DividerDrawerItem(), new SecondaryDrawerItem().withName("Setting"))

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        Toast.makeText(getBaseContext(), "" + i, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }).build();
    }


    private void setComponentControl() {

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
                        R.id.activity_navigation_drawer_framelayout,
                        frontFragment).commit();

    }

}
