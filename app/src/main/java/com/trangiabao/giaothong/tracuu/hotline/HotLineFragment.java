package com.trangiabao.giaothong.tracuu.hotline;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.hotline.model.HotLine;
import com.trangiabao.giaothong.tracuu.hotline.model.NhomHotLine;

import java.util.List;

@SuppressLint("ValidFragment")
public class HotLineFragment extends Fragment {

    private Context context;
    private List<HotLine> lstHotLine;
    private NhomHotLine nhomHotLine;
    private FastItemAdapter<HotLine> adapter;
    private RecyclerView rvHotLine;

    public HotLineFragment(List<HotLine> lstHotLine, NhomHotLine nhomHotLine) {
        this.lstHotLine = lstHotLine;
        this.nhomHotLine = nhomHotLine;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_line, container, false);
        context = getActivity();

        rvHotLine = (RecyclerView) view.findViewById(R.id.rvHotLine);
        RecyclerViewHeader rvHeader = (RecyclerViewHeader) view.findViewById(R.id.rvHeader);
        TextView txtMoTa = (TextView) view.findViewById(R.id.txtMoTa);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(lstHotLine);

        rvHotLine.setLayoutManager(new LinearLayoutManager(context));
        rvHeader.attachTo(rvHotLine);
        rvHotLine.setAdapter(adapter);
        txtMoTa.setText(Html.fromHtml(nhomHotLine.getMoTa()));

        addEvents();
        return view;
    }

    private void addEvents() {
        adapter.withOnClickListener(new FastAdapter.OnClickListener<HotLine>() {
            @Override
            public boolean onClick(View v, IAdapter<HotLine> adapter, HotLine item, int position) {
                Uri number = Uri.parse("tel:" + item.getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                return false;
            }
        });
    }
}
