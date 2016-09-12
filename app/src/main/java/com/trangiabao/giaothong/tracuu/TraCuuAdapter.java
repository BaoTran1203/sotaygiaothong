package com.trangiabao.giaothong.tracuu;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.util.ArrayList;
import java.util.List;

public class TraCuuAdapter extends AbstractItem<TraCuuAdapter, TraCuuAdapter.ViewHolder> {

    private String title;
    private int icon;


    public TraCuuAdapter(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public static List<TraCuuAdapter> createStaticData() {
        List<TraCuuAdapter> data = new ArrayList<>();
        data.add(new TraCuuAdapter("Luật giao thông", R.drawable.ic_law));
        data.add(new TraCuuAdapter("Biển báo", R.drawable.ic_sign));
        data.add(new TraCuuAdapter("Các mức phạt", R.drawable.ic_home));
        data.add(new TraCuuAdapter("Biển số xe", R.drawable.ic_home));
        return data;
    }

    @Override
    public int getType() {
        return R.id.item_layout_tra_cuu;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_tra_cuu;
    }

    @Override
    public void bindView(ViewHolder viewHolder) {
        super.bindView(viewHolder);
        viewHolder.txtTitle.setText(getTitle());
        viewHolder.imgIcon.setImageResource(getIcon());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtTitle;
        protected ImageView imgIcon;

        public ViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            this.imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        }
    }
}
