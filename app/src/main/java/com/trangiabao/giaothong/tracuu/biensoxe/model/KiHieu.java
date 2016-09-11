package com.trangiabao.giaothong.tracuu.biensoxe.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.biensoxe.db.SeriDB;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class KiHieu extends AbstractItem<KiHieu, KiHieu.ViewHolder> {

    private int id;
    private int idNhom;
    private String kiHieu;
    private String tenKiHieu;
    private String hinh;
    private List<Seri> lstSeri;
    private Context context;

    public KiHieu(Context context, int id, int idNhom, String kiHieu, String tenKiHieu, String hinh) {
        this.context = context;
        this.id = id;
        this.idNhom = idNhom;
        this.kiHieu = kiHieu;
        this.tenKiHieu = tenKiHieu;
        this.hinh = hinh;
        this.lstSeri = new SeriDB(context).getByIdKiHieu(id + "");
    }

    public int getId() {
        return id;
    }

    public int getIdNhom() {
        return idNhom;
    }

    public String getKiHieu() {
        return kiHieu;
    }

    public String getTenKiHieu() {
        return tenKiHieu;
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
        return R.layout.item_bien_so_xe;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.txtTenKiHieu.setText(getTenKiHieu());
        viewHolder.txtKiHieu.setText(getKiHieu());
        String seri = "";
        if (lstSeri.size() > 0) {
            for (int i = 0; i < lstSeri.size(); i++) {
                seri += lstSeri.get(i).getMoTa() + " - " + lstSeri.get(i).getSeri() + "\n";
            }
        }
        viewHolder.txtSeri.setText(seri);

        Drawable drawable = null;
        try {
            InputStream is = context.getAssets().open(getHinh());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.imgHinh.setImageDrawable(drawable);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtTenKiHieu;
        protected TextView txtKiHieu;
        protected TextView txtSeri;
        protected ImageView imgHinh;

        public ViewHolder(View view) {
            super(view);
            this.txtTenKiHieu = (TextView) itemView.findViewById(R.id.txtTenKiHieu);
            this.txtKiHieu = (TextView) itemView.findViewById(R.id.txtKiHieu);
            this.txtSeri = (TextView) itemView.findViewById(R.id.txtSeri);
            this.imgHinh = (ImageView) itemView.findViewById(R.id.imgHinh);
        }
    }
}
