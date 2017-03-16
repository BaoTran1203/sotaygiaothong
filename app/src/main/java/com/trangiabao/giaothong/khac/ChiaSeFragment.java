package com.trangiabao.giaothong.khac;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trangiabao.giaothong.R;

@SuppressLint("ValidFragment")
public class ChiaSeFragment extends Fragment {

    private Context context;
    private TextView txtInternet;
    private EditText txtSubject, txtContent;
    private Button btnGui;
    private AdView adView;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null) {
                txtInternet.setVisibility(View.GONE);
                txtSubject.setEnabled(true);
                txtContent.setEnabled(true);
                btnGui.setEnabled(true);
                txtSubject.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else {
                txtInternet.setVisibility(View.VISIBLE);
                txtSubject.setEnabled(false);
                txtContent.setEnabled(false);
                btnGui.setEnabled(false);
            }
        }
    };

    public ChiaSeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chia_se, container, false);
        context = getActivity();
        txtInternet = (TextView) view.findViewById(R.id.txtInternet);
        txtSubject = (EditText) view.findViewById(R.id.txtSubject);
        txtContent = (EditText) view.findViewById(R.id.txtContent);
        btnGui = (Button) view.findViewById(R.id.btnGui);

        adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(context.getString(R.string.test_device_id))
                .build();
        adView.loadAd(adRequest);

        addEvents();
        return view;
    }

    private void addEvents() {
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                adView.setVisibility(View.GONE);
            }
        });

        final String tieuDe = txtSubject.getText().toString();
        final String noiDung = txtContent.getText().toString() + "\n\nhttps://www.google.com.vn/";
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, tieuDe);
                shareIntent.putExtra(Intent.EXTRA_TEXT, noiDung);
                startActivity(Intent.createChooser(shareIntent, "Chọn ứng dụng để gửi"));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
