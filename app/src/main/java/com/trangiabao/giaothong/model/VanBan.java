package com.trangiabao.giaothong.model;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

public class VanBan extends AbstractItem<VanBan, VanBan.ViewHolder> {

    private int id;
    private String tenVanBan;
    private String tenVietTat;
    private String moTa;
    private byte[] hinh;

    public VanBan() {
    }

    public VanBan(int id, String tenVanBan, String tenVietTat, String moTa, byte[] hinh) {
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

    public byte[] getHinh() {
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
        viewHolder.txtTenVanBan.setText(Html.fromHtml(getTenVanBan()));
        viewHolder.txtMoTa.setText(Html.fromHtml(getMoTa()));
        Bitmap bmp = BitmapFactory.decodeByteArray(getHinh(), 0, getHinh().length);
        viewHolder.imgHinh.setImageBitmap(bmp);
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
