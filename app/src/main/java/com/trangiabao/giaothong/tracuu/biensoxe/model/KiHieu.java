package com.trangiabao.giaothong.tracuu.biensoxe.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public String getKiHieu() {
        return kiHieu;
    }

    public String getTenKiHieu() {
        return tenKiHieu;
    }

    private String getHinh() {
        return hinh;
    }

    public List<Seri> getLstSeri() {
        return lstSeri;
    }

    @Override
    public int getType() {
        return R.id.item_layout_bien_so_xe;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_bien_so_xe;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtTenKiHieu.setText(getTenKiHieu());
        viewHolder.txtKiHieu.setText(getKiHieu());
        String seri = "";
        if (lstSeri.size() > 0) {
            for (int i = 0; i < lstSeri.size(); i++) {
                seri += "&#8226; " + lstSeri.get(i).getMoTa() + ": " + lstSeri.get(i).getSeri() + "<br>";
            }
        }

        if (seri.equals("")) {
            viewHolder.txtSeri.setVisibility(View.GONE);
        } else {
            viewHolder.txtSeri.setVisibility(View.VISIBLE);
            seri = seri.substring(0, seri.length() - 4);
            viewHolder.txtSeri.setText(Html.fromHtml(seri));
        }

        Drawable drawable = null;
        try {
            InputStream is = context.getAssets().open(getHinh());
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.imgHinh.setImageDrawable(drawable);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTenKiHieu;
        private TextView txtKiHieu;
        private TextView txtSeri;
        private ImageView imgHinh;

        public ViewHolder(View view) {
            super(view);
            this.txtTenKiHieu = (TextView) itemView.findViewById(R.id.txtTenKiHieu);
            this.txtKiHieu = (TextView) itemView.findViewById(R.id.txtKiHieu);
            this.txtSeri = (TextView) itemView.findViewById(R.id.txtSeri);
            this.imgHinh = (ImageView) itemView.findViewById(R.id.imgHinh);
        }
    }
}
