package com.trangiabao.giaothong.chiase;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.trangiabao.giaothong.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class ChiaSeFragment extends Fragment {

    private EditText txtSubject;
    private EditText txtContent;
    private Button btnGui;
    private Context context;

    private final static String[] lstPackageName = new String[]{
            "com.asus.message",
            "com.google.android.apps.plus",
            "com.google.android.gm",
            "com.facebook.lite",
            "com.viber.voip",
            "com.facebook.katana",
            "com.facebook.orca",
            "com.zing.zalo",
            "com.android.twiter.composer",
            "com.skype.raider",
    };

    public ChiaSeFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chia_se, container, false);
        txtSubject = (EditText) view.findViewById(R.id.txtSubject);
        txtContent = (EditText) view.findViewById(R.id.txtContent);
        btnGui = (Button) view.findViewById(R.id.btnGui);

        addEvents();
        return view;
    }

    private void addEvents() {
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(txtSubject.getText().toString(), txtContent.getText().toString());
            }
        });
    }

    private void share(String subject, String body) {
        List<Intent> lstIntent = new ArrayList<>();
        for (String packageName : lstPackageName) {
            if (isPackageExisted(packageName)) {
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
                shareIntent.setPackage(packageName);
                lstIntent.add(shareIntent);
            }
        }
        Intent chooserIntent = Intent.createChooser(lstIntent.remove(0), "Chọn ứng dụng...");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, lstIntent.toArray(new Parcelable[lstIntent.size()]));
        startActivity(chooserIntent);
    }

    public boolean isPackageExisted(String packageName) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
}
