package com.trangiabao.giaothong.tracuu.xuphat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.xuphat.db.LoaiViPhamDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.LoaiViPham;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;

import java.util.List;

@SuppressLint("ValidFragment")
public class XuPhatFragment extends Fragment {

    private Context context;

    // controls
    private MaterialSpinner spinnerXuPhat;
    private FastItemAdapter<MucXuPhat> adapter;
    private RecyclerView rvXuPhat;

    // data
    private List<List<MucXuPhat>> lstMucXuPhat;
    private List<LoaiViPham> lstLoaiViPham;

    public XuPhatFragment(List<LoaiViPham> lstLoaiViPham, List<List<MucXuPhat>> lstMucXuPhat) {
        this.lstMucXuPhat = lstMucXuPhat;
        this.lstLoaiViPham = lstLoaiViPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xu_phat, container, false);
        context = getActivity();
        TextView txtCapNhat = (TextView) view.findViewById(R.id.txtCapNhat);
        spinnerXuPhat = (MaterialSpinner) view.findViewById(R.id.spinnerXuPhat);
        rvXuPhat = (RecyclerView) view.findViewById(R.id.rvXuPhat);

        if (lstMucXuPhat.size() > 0) {
            txtCapNhat.setVisibility(View.GONE);
        } else {
            txtCapNhat.setVisibility(View.VISIBLE);
        }
        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvXuPhat.setLayoutManager(new LinearLayoutManager(context));
        rvXuPhat.setHasFixedSize(true);
        rvXuPhat.setAdapter(adapter);

        if (lstLoaiViPham.size() != 0)
            spinnerXuPhat.setItems(new LoaiViPhamDB(context).getLstString(lstLoaiViPham));
        selectSpinner(0);

        addEvents();
        return view;
    }

    private void addEvents() {
        spinnerXuPhat.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                selectSpinner(position);
            }
        });
    }

    private void selectSpinner(int index) {
        adapter.clear();
        if (lstMucXuPhat.size() != 0)
            adapter.add(this.lstMucXuPhat.get(index));
    }
}