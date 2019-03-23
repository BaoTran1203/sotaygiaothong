package com.trangiabao.giaothong;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.trangiabao.giaothong.khac.ChiaSeFragment;
import com.trangiabao.giaothong.khac.FeedbackFragment;
import com.trangiabao.giaothong.khac.GioiThieuFragment;
import com.trangiabao.giaothong.khac.LienHeFragment;
import com.trangiabao.giaothong.sathach.SatHachFragment;
import com.trangiabao.giaothong.tracuu.TraCuuFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;
    private Toolbar toolbar;
    private Drawer result;
    private CrossfadeDrawerLayout crossfadeDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        processCopy();
        addControls();
        selectItemDrawer(1);
    }

    public void processCopy() {
        File dbFile = getDatabasePath(getString(R.string.dbname));
        if (!dbFile.exists()) {
            try {
                InputStream myInput = getAssets().open(getString(R.string.dbname));
                String outFileName = getApplicationInfo().dataDir + "/databases/" + getString(R.string.dbname);
                File f = new File(getApplicationInfo().dataDir + "/databases/");
                if (!f.exists())
                    f.mkdir();
                OutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        createDrawer();
    }

    private void createDrawer() {
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.banner)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(true)
                .withDrawerLayout(R.layout.custom_crossfade)
                .withHasStableIds(true)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName("Giới thiệu ứng dụng").withIcon(R.drawable.ic_gioi_thieu),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(2).withName("Sát hạch lái xe").withIcon(R.drawable.ic_sat_hach),
                        new PrimaryDrawerItem().withIdentifier(3).withName("Tra cứu thông tin").withIcon(R.drawable.ic_tra_cuu),
                        //new PrimaryDrawerItem().withIdentifier(4).withName("Cẩm nang giao thông").withIcon(R.drawable.ic_cam_nang),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withIdentifier(5).withName("Đánh giá ứng dụng").withIcon(R.drawable.ic_danh_gia),
                        new PrimaryDrawerItem().withIdentifier(6).withName("Chia sẽ với mọi người").withIcon(R.drawable.ic_chia_se),
                        //new PrimaryDrawerItem().withIdentifier(7).withName("Feedback").withIcon(R.drawable.ic_gop_y),
                        new PrimaryDrawerItem().withIdentifier(8).withName("Liên hệ nhà phát triển").withIcon(R.drawable.ic_lien_he)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int id = (int) drawerItem.getIdentifier();
                        switch (id) {
                            case 1:
                                setFragment(new GioiThieuFragment());
                                break;

                            case 2:
                                setFragment(new SatHachFragment());
                                break;

                            case 3:
                                setFragment(new TraCuuFragment());
                                break;

                            case 4:
                                Toast.makeText(context, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                                break;

                            case 5:
                                Toast.makeText(context, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                                break;

                            case 6:
                                setFragment(new ChiaSeFragment());
                                break;

                            case 7:
                                setFragment(new FeedbackFragment());
                                break;

                            case 8:
                                setFragment(new LienHeFragment());
                                break;
                        }
                        toolbar.setTitle(((PrimaryDrawerItem) result.getDrawerItem(id)).getName() + "");
                        return false;
                    }
                })
                .build();
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(context));

        MiniDrawer miniResult = result.getMiniDrawer();
        View view = miniResult.build(context);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(
                this,
                com.mikepenz.materialdrawer.R.attr.material_drawer_background,
                com.mikepenz.materialdrawer.R.color.material_drawer_primary_dark));
        crossfadeDrawerLayout.getSmallView().addView(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
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
    }

    private void selectItemDrawer(int id) {
        result.setSelection(id, true);
        toolbar.setTitle(((PrimaryDrawerItem) result.getDrawerItem(id)).getName() + "");
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
        result.closeDrawer();
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
