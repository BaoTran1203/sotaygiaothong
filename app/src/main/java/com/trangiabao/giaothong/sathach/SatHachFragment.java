package com.trangiabao.giaothong.sathach;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.cauhoi.TuyChonCauHoiActivity;
import com.trangiabao.giaothong.sathach.cauhoi.TuyChonBaiThiActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SatHachFragment extends Fragment {

    private FastItemAdapter<SatHachAdapter> adapter;
    private RecyclerView rvSatHach;
    private Context context;

    public SatHachFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sat_hach, container, false);
        this.context = getActivity();

        rvSatHach = (RecyclerView) view.findViewById(R.id.rvSatHach);
        rvSatHach.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSatHach.setHasFixedSize(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvSatHach.setAdapter(adapter);
        adapter.add(createList());

        addEvents();
        return view;
    }

    private List<SatHachAdapter> createList() {
        List<SatHachAdapter> data = new ArrayList<>();
        data.add(new SatHachAdapter("Ngân hàng câu hỏi", getDrawable("image/icon/ic_cau_hoi.png")));
        data.add(new SatHachAdapter("Làm bài thi", getDrawable("image/icon/ic_lam_bai_thi.png")));
        data.add(new SatHachAdapter("Bài thi sa hình", getDrawable("image/icon/ic_sa_hinh.png")));
        data.add(new SatHachAdapter("Mẹo ghi nhớ", getDrawable("image/icon/ic_meo.png")));
        return data;
    }

    private Drawable getDrawable(String path) {
        Drawable drawable = null;
        try {
            InputStream is = this.context.getAssets().open(path);
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    private void addEvents() {
        adapter.withOnClickListener(new FastAdapter.OnClickListener<SatHachAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<SatHachAdapter> adapter, SatHachAdapter item, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), TuyChonCauHoiActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), TuyChonBaiThiActivity.class));
                        break;
                    case 2:
                        Toast.makeText(context, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(context, "Chờ cập nhật", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
}
