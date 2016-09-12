package com.trangiabao.giaothong.tracuu.biensoxe;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.biensoxe.model.KiHieu;
import com.trangiabao.giaothong.tracuu.biensoxe.model.NhomBienSoXe;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SuppressLint("ValidFragment")
public class BienSoXeFragment extends Fragment {

    private List<KiHieu> lstKiHieu;
    private NhomBienSoXe nhomBienSoXe;
    private FastItemAdapter<KiHieu> adapter;
    private RecyclerView rvBienSoXe;
    private FloatingActionButton fab;

    public BienSoXeFragment(List<KiHieu> lstKiHieu, NhomBienSoXe nhomBienSoXe) {
        this.lstKiHieu = lstKiHieu;
        this.nhomBienSoXe = nhomBienSoXe;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bien_so_xe, container, false);

        rvBienSoXe = (RecyclerView) view.findViewById(R.id.rvBienSoXe);
        rvBienSoXe.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerViewHeader rvHeader = (RecyclerViewHeader) view.findViewById(R.id.rvHeader);
        rvHeader.attachTo(rvBienSoXe);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(this.lstKiHieu);

        rvBienSoXe.setAdapter(adapter);
        adapter.withSavedInstanceState(savedInstanceState);

        TextView txtMauSac = (TextView) view.findViewById(R.id.txtMauSac);
        TextView txtMoTa = (TextView) view.findViewById(R.id.txtMoTa);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.imgHinh);

        txtMauSac.setText(nhomBienSoXe.getMauSac());
        txtMoTa.setText(nhomBienSoXe.getMoTa());
        Drawable drawable = null;
        try {
            InputStream is = getActivity().getAssets().open(nhomBienSoXe.getHinh());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgHinh.setImageDrawable(drawable);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(rvBienSoXe);

        addEvents();
        return view;
    }

    private void addEvents() {
        adapter.withOnClickListener(new FastAdapter.OnClickListener<KiHieu>() {
            @Override
            public boolean onClick(View v, IAdapter<KiHieu> adapter, KiHieu item, int position) {
                TextView txtSeri = (TextView) v.findViewById(R.id.txtSeri);
                if (!txtSeri.getText().equals("")) {
                    if (txtSeri.getVisibility() == View.VISIBLE)
                        txtSeri.setVisibility(View.GONE);
                    else txtSeri.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        rvBienSoXe.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (rvBienSoXe.computeVerticalScrollOffset() == 0 ||
                        isMaxScrollReached(rvBienSoXe)) {
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
                rvBienSoXe.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState = adapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
