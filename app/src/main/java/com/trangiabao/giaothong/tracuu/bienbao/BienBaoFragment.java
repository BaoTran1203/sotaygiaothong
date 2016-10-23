package com.trangiabao.giaothong.tracuu.bienbao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.bienbao.model.BienBao;
import com.trangiabao.giaothong.tracuu.bienbao.model.NhomBienBao;

import java.util.List;

@SuppressLint("ValidFragment")
public class BienBaoFragment extends Fragment {

    private Context context;
    private List<BienBao> lstBienBao;
    private NhomBienBao nhomBienBao;
    private FastItemAdapter<BienBao> adapter;
    private RecyclerView rvBienBao;

    public BienBaoFragment(List<BienBao> lstBienBao, NhomBienBao nhomBienBao) {
        this.lstBienBao = lstBienBao;
        this.nhomBienBao = nhomBienBao;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bien_bao, container, false);
        context = getActivity();

        rvBienBao = (RecyclerView) view.findViewById(R.id.rvBienBao);
        RecyclerViewHeader rvHeader = (RecyclerViewHeader) view.findViewById(R.id.rvHeader);
        TextView txtMoTa = (TextView) view.findViewById(R.id.txtMoTa);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(lstBienBao);

        rvBienBao.setLayoutManager(new LinearLayoutManager(context));
        rvBienBao.setHasFixedSize(true);
        rvHeader.attachTo(rvBienBao);
        rvBienBao.setAdapter(adapter);
        txtMoTa.setText(Html.fromHtml(nhomBienBao.getMoTa()));
        return view;
    }

}
