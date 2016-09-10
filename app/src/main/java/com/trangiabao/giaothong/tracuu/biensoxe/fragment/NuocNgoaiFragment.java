package com.trangiabao.giaothong.tracuu.biensoxe.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.biensoxe.model.NuocNgoai;

import java.util.List;

@SuppressLint("ValidFragment")
public class NuocNgoaiFragment extends Fragment {

    private List<NuocNgoai> lstNuocNgoai;

    public NuocNgoaiFragment(List<NuocNgoai> lstNuocNgoai) {
        this.lstNuocNgoai = lstNuocNgoai;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nuoc_ngoai, container, false);

        RecyclerView rvNuocNgoai = (RecyclerView) view.findViewById(R.id.rvNuocNgoai);
        rvNuocNgoai.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNuocNgoai.setHasFixedSize(true);

        FastItemAdapter<NuocNgoai> adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvNuocNgoai.setAdapter(adapter);
        adapter.withSavedInstanceState(savedInstanceState);
        adapter.add(this.lstNuocNgoai);

        return view;
    }

}
