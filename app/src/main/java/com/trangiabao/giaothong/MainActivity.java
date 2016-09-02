package com.trangiabao.giaothong;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.trangiabao.giaothong.sathach.SatHachFragment;
import com.trangiabao.giaothong.tracuu.TraCuuFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AccountHeader headerResult;
    private Drawer result;
    private MiniDrawer miniResult;
    private CrossfadeDrawerLayout crossfadeDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataProvider db = new DataProvider(MainActivity.this, "giaothong.db");
        db.processCopy();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.banner)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withDrawerLayout(R.layout.crossfade)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName("Giới thiệu ứng dụng").withIcon(R.drawable.ic_home),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(2).withName("Sát hạch lái xe").withIcon(R.drawable.ic_motorbike),
                        new PrimaryDrawerItem().withIdentifier(3).withName("Tra cứu thông tin").withIcon(R.drawable.ic_search),
                        new PrimaryDrawerItem().withIdentifier(4).withName("Cẩm nang giao thông").withIcon(R.drawable.ic_handbook),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(5).withName("Đánh giá ứng dụng").withIcon(R.drawable.ic_star),
                        new PrimaryDrawerItem().withIdentifier(6).withName("Chia sẽ với mọi người").withIcon(R.drawable.ic_share),
                        new PrimaryDrawerItem().withIdentifier(7).withName("Liên hệ nhà phát triển").withIcon(R.drawable.ic_contact)
                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int id = (int) drawerItem.getIdentifier();
                        switch (id) {
                            case 1:
                                startActivity(new Intent(MainActivity.this, GioiThieuActivity.class));
                                break;

                            case 2:
                                setFragment(new SatHachFragment());
                                toolbar.setTitle(((PrimaryDrawerItem) result.getDrawerItem(id)).getName() + "");
                                break;

                            case 3:
                                setFragment(new TraCuuFragment());
                                toolbar.setTitle(((PrimaryDrawerItem) result.getDrawerItem(id)).getName() + "");
                                break;

                            case 4:
                                Toast.makeText(MainActivity.this, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                                break;

                            case 5:
                                Toast.makeText(MainActivity.this, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                                break;

                            case 6:
                                Toast.makeText(MainActivity.this, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                                break;

                            case 7:
                                Toast.makeText(MainActivity.this, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, ThongTinActivity.class));
                                break;
                        }
                        return false;
                    }
                })
                .build();
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        miniResult = result.getMiniDrawer();
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(
                this,
                com.mikepenz.materialdrawer.R.attr.material_drawer_background,
                com.mikepenz.materialdrawer.R.color.material_drawer_primary_dark));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        crossfadeDrawerLayout.setDrawerTitle(0, "ABC");
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });

        if (savedInstanceState == null) {
            result.setSelection(2, true);
            toolbar.setTitle(((PrimaryDrawerItem) result.getDrawerItem(2)).getName() + "");
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
        result.closeDrawer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
