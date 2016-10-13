package com.trangiabao.giaothong.sathach.sahinh;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trangiabao.giaothong.R;

public class SaHinhActivity extends AppCompatActivity {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    public static final String DEVELOPER_KEY = "AIzaSyA3uSoKUNnffYd1wYrTu7ggscUNW4Z3I5k";
    private static final String[] lstYoutubeCode = {
            "kRYaHED6MIA",
            "g1UWKObBabA"
    };
    private static final String[] lstSpinner = {
            "A1,A2",
            "B,C,D,E"
    };
    private static final String[] lstHtml = {
            "html/sahinh/a1a2.html",
            "html/sahinh/bcde.html"
    };

    private Context context = SaHinhActivity.this;
    private Toolbar toolbar;
    private YouTubePlayerSupportFragment youtube_fragment;
    private WebView webView;
    private MaterialSpinner spinner;
    private YouTubePlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_hinh);

        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bài thi sa hình");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems(lstSpinner);
        youtube_fragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        webView = (WebView) findViewById(R.id.webView);
    }

    private void addEvents() {
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                selectSpinnerItem(position);
            }
        });

        youtube_fragment.initialize(DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    mPlayer = player;
                    selectSpinnerItem(0);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult errorReason) {
                if (errorReason.isUserRecoverableError()) {
                    errorReason.getErrorDialog((Activity) context, RECOVERY_DIALOG_REQUEST).show();
                } else {
                    String errorMessage = String.format(
                            getString(R.string.error_player), errorReason.toString());
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void selectSpinnerItem(int i) {
        if (mPlayer != null) {
            mPlayer.cueVideo(lstYoutubeCode[i]);
            mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }
        webView.loadUrl("file:///android_asset/" + lstHtml[i]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (android.R.id.home == id) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
