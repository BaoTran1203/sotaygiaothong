package com.trangiabao.giaothong.tracuu.xuphat.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PhuongTien extends AbstractItem<PhuongTien, PhuongTien.ViewHolder> {

    private Context context;
    private int id;
    private String phuongTien;
    private String doiTuong;
    private String hinh;

    public PhuongTien(Context context, int id, String phuongTien, String doiTuong, String hinh) {
        this.context = context;
        this.id = id;
        this.phuongTien = phuongTien;
        this.doiTuong = doiTuong;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getPhuongTien() {
        return phuongTien;
    }

    public String getHinh() {
        return hinh;
    }

    public String getDoiTuong() {
        return doiTuong;
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
        viewHolder.txtSubject.setText(getPhuongTien());
        viewHolder.txtContent.setText(Html.fromHtml(getDoiTuong()));
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
