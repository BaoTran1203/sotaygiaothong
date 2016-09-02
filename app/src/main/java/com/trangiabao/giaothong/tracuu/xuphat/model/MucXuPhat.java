package com.trangiabao.giaothong.tracuu.xuphat.model;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

public class MucXuPhat extends AbstractItem<MucXuPhat, MucXuPhat.ViewHolder> {

    private int id;
    private int idPhuongTien;
    private int idLoaiViPham;
    private String hanhVi;
    private String mucPhat;
    private String xuPhatKhac;

    public MucXuPhat(int id, int idPhuongTien, int idLoaiViPham, String hanhVi, String mucPhat, String xuPhatKhac) {
        this.id = id;
        this.idPhuongTien = idPhuongTien;
        this.idLoaiViPham = idLoaiViPham;
        this.hanhVi = hanhVi;
        this.mucPhat = mucPhat;
        this.xuPhatKhac = xuPhatKhac;
    }

    public int getId() {
        return id;
    }

    public int getIdPhuongTien() {
        return idPhuongTien;
    }

    public int getIdLoaiViPham() {
        return idLoaiViPham;
    }

    public String getHanhVi() {
        return hanhVi;
    }

    public String getMucPhat() {
        return mucPhat;
    }

    public String getXuPhatKhac() {
        return xuPhatKhac;
    }

    @Override
    public int getType() {
        return R.id.item_layout;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_xu_phat;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.txtHanhVi.setText(Html.fromHtml(getHanhVi()));
        viewHolder.txtMucGia.setText(Html.fromHtml(getMucPhat()));
        viewHolder.txtPhatKhac.setText(Html.fromHtml(getXuPhatKhac()));
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtHanhVi, txtMucGia, txtPhatKhac;

        public ViewHolder(View view) {
            super(view);
            this.txtHanhVi = (TextView) itemView.findViewById(R.id.txtHanhVi);
            this.txtMucGia = (TextView) itemView.findViewById(R.id.txtMucGia);
            this.txtPhatKhac = (TextView) itemView.findViewById(R.id.txtPhatKhac);
        }
    }
}
