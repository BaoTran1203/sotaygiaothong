package com.trangiabao.giaothong.tracuu.luat.model;

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

public class VanBan extends AbstractItem<VanBan, VanBan.ViewHolder> {

    private int id;
    private String tenVanBan;
    private String tenVietTat;
    private String moTa;
    private String hinh;
    private Context context;

    public VanBan() {
    }

    public VanBan(Context context, int id, String tenVanBan, String tenVietTat, String moTa, String hinh) {
        this.context = context;
        this.id = id;
        this.tenVanBan = tenVanBan;
        this.tenVietTat = tenVietTat;
        this.moTa = moTa;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getTenVanBan() {
        return tenVanBan;
    }

    public String getTenVietTat() {
        return tenVietTat;
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
        return R.layout.item_van_ban;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.txtTenVanBan.setText(getTenVanBan());
        viewHolder.txtMoTa.setText(getMoTa());
        try {
            InputStream is = context.getAssets().open(getHinh());
            Drawable drawable = Drawable.createFromStream(is, null);
            viewHolder.imgHinh.setImageDrawable(drawable);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtTenVanBan;
        protected TextView txtMoTa;
        protected ImageView imgHinh;

        public ViewHolder(View view) {
            super(view);
            this.txtTenVanBan = (TextView) view.findViewById(R.id.txtTenVanBan);
            this.txtMoTa = (TextView) view.findViewById(R.id.txtMoTa);
            this.imgHinh = (ImageView) view.findViewById(R.id.imgHinh);
        }
    }
}