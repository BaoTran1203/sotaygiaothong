package com.trangiabao.giaothong.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.database.DanhMucHinhDB;

public class BienBao extends AbstractItem<BienBao, BienBao.ViewHolder> {

    private int id;
    private int idNhomBienBao;
    private String maBienBao;
    private String tenBienBao;
    private String noiDungBienBao;
    private int idHinh;
    private byte[] hinh;
    private Context context;

    public BienBao(Context context, int id, int idNhomBienBao, String maBienBao, String tenBienBao, String noiDungBienBao, int idHinh) {
        this.context = context;
        this.id = id;
        this.idNhomBienBao = idNhomBienBao;
        this.maBienBao = maBienBao;
        this.tenBienBao = tenBienBao;
        this.noiDungBienBao = noiDungBienBao;
        this.idHinh = idHinh;
        this.hinh = new DanhMucHinhDB(this.context).getById(idHinh).getHinh();
    }

    public int getId() {
        return id;
    }

    public int getIdNhomBienBao() {
        return idNhomBienBao;
    }

    public String getMaBienBao() {
        return maBienBao;
    }

    public String getTenBienBao() {
        return tenBienBao;
    }

    public String getNoiDungBienBao() {
        return noiDungBienBao;
    }

    public int getIdHinh() {
        return idHinh;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    @Override
    public int getType() {
        return R.id.item_layout;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_bien_bao;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.txtMaBienBao.setText(Html.fromHtml("Mã: " + getMaBienBao()));
        viewHolder.txtTenBienBao.setText(Html.fromHtml(getTenBienBao()));
        viewHolder.txtNoiDungBienBao.setText(Html.fromHtml(getNoiDungBienBao()));
        Bitmap bmp = BitmapFactory.decodeByteArray(getHinh(), 0, getHinh().length);
        viewHolder.imgHinh.setImageBitmap(bmp);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imgHinh;
        protected TextView txtMaBienBao;
        protected TextView txtTenBienBao;
        protected TextView txtNoiDungBienBao;

        public ViewHolder(View view) {
            super(view);
            this.txtMaBienBao = (TextView) itemView.findViewById(R.id.txtMaBienBao);
            this.txtTenBienBao = (TextView) itemView.findViewById(R.id.txtTenBienBao);
            this.txtNoiDungBienBao = (TextView) itemView.findViewById(R.id.txtNoiDungBienBao);
            this.imgHinh = (ImageView) itemView.findViewById(R.id.imgHinh);
        }
    }
}
