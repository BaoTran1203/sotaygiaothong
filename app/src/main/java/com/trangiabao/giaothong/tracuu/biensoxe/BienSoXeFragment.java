package com.trangiabao.giaothong.tracuu.biensoxe;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.biensoxe.model.KiHieu;
import com.trangiabao.giaothong.tracuu.biensoxe.model.NhomBienSoXe;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SuppressLint("ValidFragment")
public class BienSoXeFragment extends Fragment {

    private Context context;
    private List<KiHieu> lstKiHieu;
    private NhomBienSoXe nhomBienSoXe;
    private FastItemAdapter<KiHieu> adapter;
    private RecyclerView rvBienSoXe;

    public BienSoXeFragment(List<KiHieu> lstKiHieu, NhomBienSoXe nhomBienSoXe) {
        this.lstKiHieu = lstKiHieu;
        this.nhomBienSoXe = nhomBienSoXe;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bien_so_xe, container, false);
        context = getActivity();
        rvBienSoXe = (RecyclerView) view.findViewById(R.id.rvBienSoXe);
        RecyclerViewHeader rvHeader = (RecyclerViewHeader) view.findViewById(R.id.rvHeader);
        TextView txtMauSac = (TextView) view.findViewById(R.id.txtMauSac);
        TextView txtMoTa = (TextView) view.findViewById(R.id.txtMoTa);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.imgHinh);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(lstKiHieu);

        rvBienSoXe.setLayoutManager(new LinearLayoutManager(context));
        rvBienSoXe.setAdapter(adapter);
        rvHeader.attachTo(rvBienSoXe);
        txtMauSac.setText(nhomBienSoXe.getMauSac());
        txtMoTa.setText(nhomBienSoXe.getMoTa());
        Drawable drawable = null;
        try {
            InputStream is = context.getAssets().open(nhomBienSoXe.getHinh());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgHinh.setImageDrawable(drawable);

        return view;
    }
}
