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
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.xuphat.db.LoaiViPhamDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;

import java.util.List;

@SuppressLint("ValidFragment")
public class XuPhatFragment extends Fragment {

    private Context context;
    private MaterialSpinner spinnerXuPhat;
    private List<List<MucXuPhat>> lstMucXuPhat;
    private FastItemAdapter<MucXuPhat> adapter;
    private RecyclerView rvXuPhat;

    public XuPhatFragment(List<List<MucXuPhat>> lstMucXuPhat) {
        this.lstMucXuPhat = lstMucXuPhat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xu_phat, container, false);
        context = getActivity();
        spinnerXuPhat = (MaterialSpinner) view.findViewById(R.id.spinnerXuPhat);
        rvXuPhat = (RecyclerView) view.findViewById(R.id.rvXuPhat);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvXuPhat.setLayoutManager(new LinearLayoutManager(context));
        rvXuPhat.setHasFixedSize(true);
        rvXuPhat.setAdapter(adapter);

        spinnerXuPhat.setItems(new LoaiViPhamDB(context).getAllAsString());
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
        adapter.add(this.lstMucXuPhat.get(index));
        if (this.lstMucXuPhat.get(index).size() == 0) {
            Toast.makeText(context, "Dữ liệu đang được cập nhật", Toast.LENGTH_SHORT).show();
        }
    }
}