package com.trangiabao.giaothong.sathach.cauhoi.model;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.cauhoi.db.CauTraLoiDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.HinhCauHoiDB;

import java.util.ArrayList;
import java.util.List;

public class CauHoi extends AbstractItem<CauHoi, CauHoi.ViewHolder> {

    private int id;
    private String cauHoi;
    private String giaiThich;
    private List<HinhCauHoi> lstHinhCauHoi = new ArrayList<>();
    private List<CauTraLoi> lstCauTraLoi = new ArrayList<>();
    private boolean traLoi;
    private int stt;

    public CauHoi(Context context, int id, String cauHoi, String giaiThich) {
        this.id = id;
        this.cauHoi = cauHoi;
        this.giaiThich = giaiThich;
        this.lstCauTraLoi = new CauTraLoiDB(context).getListByIdCauHoi(id + "");
        this.lstHinhCauHoi = new HinhCauHoiDB(context).getByIdCauHoi(id + "");
        this.traLoi = false;
    }

    public int getId() {
        return id;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public String getGiaiThich() {
        return giaiThich;
    }

    public List<CauTraLoi> getLstCauTraLoi() {
        return lstCauTraLoi;
    }

    public List<HinhCauHoi> getLstHinhCauHoi() {
        return lstHinhCauHoi;
    }

    public boolean isTraLoi() {
        return traLoi;
    }

    public void setTraLoi(boolean traLoi) {
        this.traLoi = traLoi;
    }

    private int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    @Override
    public int getType() {
        return R.id.item_layout_ds_cau_hoi;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_ds_cau_hoi;
    }

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        viewHolder.txtSttCauHoi.setText(getStt() + "");
        if (isTraLoi()) {
            viewHolder.txtSttCauHoi.setBackgroundResource(R.drawable.custom_ripple);
        } else {
            viewHolder.txtSttCauHoi.setBackgroundResource(R.drawable.custom_ripple_2);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSttCauHoi;

        public ViewHolder(View view) {
            super(view);
            this.txtSttCauHoi = (TextView) itemView.findViewById(R.id.txtSttCauHoi);
        }
    }
}
