package com.trangiabao.giaothong.tracuu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.bienbao.BienBaoActivity;
import com.trangiabao.giaothong.tracuu.luat.VanBanActivity;
import com.trangiabao.giaothong.tracuu.xuphat.XuPhatActivity;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class TraCuuFragment extends Fragment {

    private FastItemAdapter<TraCuuAdapter> adapter;
    private RecyclerView rvTraCuu;

    public TraCuuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tra_cuu, container, false);

        rvTraCuu = (RecyclerView) view.findViewById(R.id.rvTraCuu);
        rvTraCuu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvTraCuu.setHasFixedSize(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setDuration(1500);
        animationAdapter.setInterpolator(new OvershootInterpolator());
        animationAdapter.setFirstOnly(false);

        rvTraCuu.setAdapter(animationAdapter);
        adapter.add(TraCuuAdapter.createStaticData());
        adapter.withSavedInstanceState(savedInstanceState);

        addEvents();
        return view;
    }

    private void addEvents() {
        adapter.withOnClickListener(new FastAdapter.OnClickListener<TraCuuAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<TraCuuAdapter> adapter, TraCuuAdapter item, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), VanBanActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), BienBaoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), XuPhatActivity.class));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState = adapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
