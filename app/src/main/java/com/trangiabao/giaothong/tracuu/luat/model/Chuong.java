package com.trangiabao.giaothong.tracuu.luat.model;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

public class Chuong extends AbstractItem<Chuong, Chuong.ViewHolder> {

    private int id;
    private int idVanBan;
    private String tenChuong;
    private String muc;

    public Chuong(int id, int idVanBan, String tenChuong, String muc) {
        this.id = id;
        this.idVanBan = idVanBan;
        this.tenChuong = tenChuong;
        this.muc = muc;
    }

    public int getId() {
        return id;
    }

    public int getIdVanBan() {
        return idVanBan;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public String getMuc() {
        return muc;
    }

    @Override
    public int getType() {
        return R.id.item_layout;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_chuong;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);

        viewHolder.txtChuong.setText(getTenChuong());

        if (getMuc() == null) {
            viewHolder.txtMuc.setVisibility(View.GONE);
        } else {
            viewHolder.txtMuc.setVisibility(View.VISIBLE);
            viewHolder.txtMuc.setText(getMuc());
        }

        if (getId() % 2 == 1) {
            viewHolder.item_layout.setBackgroundColor(Color.parseColor("#fabf8f"));
        } else {
            viewHolder.item_layout.setBackgroundColor(Color.WHITE);
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtChuong;
        protected TextView txtMuc;
        protected RelativeLayout item_layout;

        public ViewHolder(View view) {
            super(view);
            this.txtChuong = (TextView) view.findViewById(R.id.txtChuong);
            this.txtMuc = (TextView) view.findViewById(R.id.txtMuc);
            this.item_layout = (RelativeLayout) view.findViewById(R.id.item_layout);
        }
    }
}
