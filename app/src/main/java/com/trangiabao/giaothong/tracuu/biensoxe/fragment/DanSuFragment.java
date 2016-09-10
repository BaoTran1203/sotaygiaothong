package com.trangiabao.giaothong.tracuu.biensoxe.fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.biensoxe.model.Tinh;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

@SuppressLint("ValidFragment")
public class DanSuFragment extends Fragment {

    private List<Tinh> lstTinh;

    public DanSuFragment(List<Tinh> lstTinh) {
        this.lstTinh = lstTinh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dan_su, container, false);
        RecyclerView rvBienSoDanSu = (RecyclerView) view.findViewById(R.id.rvBienSoDanSu);
        rvBienSoDanSu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBienSoDanSu.setHasFixedSize(true);

        FastItemAdapter<Tinh> adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvBienSoDanSu.setAdapter(adapter);
        adapter.withSavedInstanceState(savedInstanceState);
        adapter.add(this.lstTinh);

        adapter.withOnClickListener(new FastAdapter.OnClickListener<Tinh>() {
            @Override
            public boolean onClick(View v, IAdapter<Tinh> adapter, Tinh item, int position) {
                LinearLayout layout_DiaPhuong = (LinearLayout) v.findViewById(R.id.layout_DiaPhuong);
                Animation animFadeOut = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
                Animation animFadeIn = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
                if (layout_DiaPhuong.getVisibility() == View.VISIBLE) {
                    layout_DiaPhuong.setAnimation(animFadeOut);
                    layout_DiaPhuong.setVisibility(View.GONE);
                } else {
                    layout_DiaPhuong.setVisibility(View.VISIBLE);
                    layout_DiaPhuong.setAnimation(animFadeIn);
                }
                return false;
            }
        });
        return view;
    }
}
