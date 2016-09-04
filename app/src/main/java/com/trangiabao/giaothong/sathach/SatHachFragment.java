package com.trangiabao.giaothong.sathach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.cauhoi.TuyChonCauHoiActivity;
import com.trangiabao.giaothong.sathach.lambaithi.TuyChonBaiThiActivity;

public class SatHachFragment extends Fragment {

    private FastItemAdapter<SatHachAdapter> adapter;
    private RecyclerView rvSatHach;

    public SatHachFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sat_hach, container, false);

        rvSatHach = (RecyclerView) view.findViewById(R.id.rvSatHach);
        rvSatHach.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSatHach.setHasFixedSize(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvSatHach.setAdapter(adapter);
        adapter.add(SatHachAdapter.createStaticData());
        adapter.withSavedInstanceState(savedInstanceState);

        addEvents();
        return view;
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
