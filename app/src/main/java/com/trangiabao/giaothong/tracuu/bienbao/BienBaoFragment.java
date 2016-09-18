package com.trangiabao.giaothong.tracuu.bienbao;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.melnykov.fab.FloatingActionButton;
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
    private FloatingActionButton fab;
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
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(lstBienBao);

        rvBienBao.setLayoutManager(new LinearLayoutManager(context));
        rvHeader.attachTo(rvBienBao);
        rvBienBao.setAdapter(adapter);
        fab.attachToRecyclerView(rvBienBao);
        txtMoTa.setText(Html.fromHtml(nhomBienBao.getMoTa()));

        addEvents();
        return view;
    }

    private void addEvents() {
        rvBienBao.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (rvBienBao.computeVerticalScrollOffset() == 0 ||
                        isMaxScrollReached(rvBienBao)) {
                    fab.hide(true);
                } else
                    fab.show(true);
            }

            private boolean isMaxScrollReached(RecyclerView recyclerView) {
                int maxScroll = recyclerView.computeVerticalScrollRange();
                int currentScroll = recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent();
                return currentScroll >= maxScroll;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBienBao.smoothScrollToPosition(0);
            }
        });
    }
}
