package com.trangiabao.giaothong.tracuu.biensoxe.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class NhomBienSoXe extends AbstractItem<NhomBienSoXe, NhomBienSoXe.ViewHolder> {

    private Context context;
    private int id;
    private String ten;
    private String moTa;
    private String hinh;

    public NhomBienSoXe(Context context, int id, String ten, String moTa, String hinh) {
        this.context = context;
        this.id = id;
        this.ten = ten;
        this.moTa = moTa;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getHinh() {
        return hinh;
    }

    @Override
    public int getType() {
        return R.id.item_layout;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_list;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtSubject.setText(getTen());
        viewHolder.txtContent.setText(getMoTa());
        Drawable drawable = null;
        try {
            InputStream is = this.context.getAssets().open(getHinh());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.imgHinh.setImageDrawable(drawable);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgHinh;
        private TextView txtSubject;
        private TextView txtContent;

        public ViewHolder(View view) {
            super(view);
            this.txtSubject = (TextView) view.findViewById(R.id.txtSubject);
            this.txtContent = (TextView) view.findViewById(R.id.txtContent);
            this.imgHinh = (ImageView) view.findViewById(R.id.imgHinh);
        }
    }
}
