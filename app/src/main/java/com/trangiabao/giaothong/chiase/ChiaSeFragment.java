package com.trangiabao.giaothong.chiase;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
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

public class ChiaSeFragment extends Fragment {

    private EditText txtSubject;
    private EditText txtContent;
    private Button btnGui;
    private Context context;

    public ChiaSeFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chiase, container, false);
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
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        List<ResolveInfo> resInfo = this.context.getPackageManager().queryIntentActivities(intent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo resolveInfo : resInfo) {
                String packageName = resolveInfo.activityInfo.packageName;
                switch (packageName) {
                    case "com.asus.message":
                    case "com.google.android.apps.plus":
                    case "com.google.android.gm":
                    case "com.facebook.lite":
                    case "com.viber.voip":
                    case "com.facebook.katana":
                    case "com.facebook.orca":
                    case "com.zing.zalo":
                    case "com.android.twiter.composer":
                    case "com.skype.raider":
                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
                        shareIntent.setPackage(packageName);
                        lstIntent.add(shareIntent);
                        break;
                }
            }
            Intent chooserIntent = Intent.createChooser(lstIntent.remove(0), "Chọn ứng dụng...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, lstIntent.toArray(new Parcelable[lstIntent.size()]));
            startActivity(chooserIntent);
        }
    }
}
