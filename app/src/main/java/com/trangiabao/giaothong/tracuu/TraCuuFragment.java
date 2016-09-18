package com.trangiabao.giaothong.tracuu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.bienbao.BienBaoActivity;
import com.trangiabao.giaothong.tracuu.biensoxe.BienSoXeActivity;
import com.trangiabao.giaothong.tracuu.luat.VanBanActivity;
import com.trangiabao.giaothong.tracuu.xuphat.XuPhatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TraCuuFragment extends Fragment {

    private Context context;
    private FastItemAdapter<TraCuuAdapter> adapter;
    private RecyclerView rvTraCuu;

    private static final String[] PATH = {
            "image/icon/ic_luat.png",
            "image/icon/ic_bien_bao.png",
            "image/icon/ic_phat.png",
            "image/icon/ic_bang_so_xe.png"
    };

    public TraCuuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tra_cuu, container, false);
        context = getActivity();
        rvTraCuu = (RecyclerView) view.findViewById(R.id.rvTraCuu);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(createList());

        rvTraCuu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvTraCuu.setHasFixedSize(true);
        rvTraCuu.setAdapter(adapter);

        addEvents();
        return view;
    }

    private List<TraCuuAdapter> createList() {
        List<TraCuuAdapter> data = new ArrayList<>();
        data.add(new TraCuuAdapter("Luật giao thông", getDrawable(PATH[0])));
        data.add(new TraCuuAdapter("Biển báo", getDrawable(PATH[1])));
        data.add(new TraCuuAdapter("Các mức phạt", getDrawable(PATH[2])));
        data.add(new TraCuuAdapter("Biển số xe", getDrawable(PATH[3])));
        return data;
    }

    private Drawable getDrawable(String path) {
        Drawable drawable = null;
        try {
            InputStream is = context.getAssets().open(path);
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    private void addEvents() {
        adapter.withOnClickListener(new FastAdapter.OnClickListener<TraCuuAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<TraCuuAdapter> adapter, TraCuuAdapter item, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(context, VanBanActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(context, BienBaoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, XuPhatActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, BienSoXeActivity.class));
                        break;
                }
                return false;
            }
        });
    }
}
