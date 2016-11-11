package com.trangiabao.giaothong.tracuu.hotline.model;

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

public class NhomHotLine extends AbstractItem<NhomHotLine, NhomHotLine.ViewHolder> {

    private Context context;
    private int id;
    private String nhom;
    private String moTa;
    private String hinh;

    public NhomHotLine(Context context, int id, String nhom, String moTa, String hinh) {
        this.context = context;
        this.id = id;
        this.nhom = nhom;
        this.moTa = moTa;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getNhom() {
        return nhom;
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
        return R.layout.item_5;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);

        viewHolder.txtSubject.setText(getNhom());
        viewHolder.txtContent.setText(Html.fromHtml(getMoTa()));
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

        private TextView txtSubject;
        private TextView txtContent;
        private ImageView imgHinh;

        public ViewHolder(View view) {
            super(view);
            this.txtSubject = (TextView) view.findViewById(R.id.txtSubject);
            this.txtContent = (TextView) view.findViewById(R.id.txtContent);
            this.imgHinh = (ImageView) view.findViewById(R.id.imgHinh);
        }
    }
}
