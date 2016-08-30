package com.trangiabao.giaothong.sathach;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.trangiabao.giaothong.R;

import java.util.ArrayList;
import java.util.List;

public class SatHachAdapter extends AbstractItem<SatHachAdapter, SatHachAdapter.ViewHolder> {

    private String title;
    private int icon;

    public SatHachAdapter(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public SatHachAdapter setTitle(String title) {
        this.title = title;
        return this;
    }

    public SatHachAdapter setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public static List<SatHachAdapter> createStaticData() {
        List<SatHachAdapter> data = new ArrayList<>();
        data.add(new SatHachAdapter("Ngân hàng câu hỏi", R.drawable.ic_question));
        data.add(new SatHachAdapter("Làm bài thi", R.drawable.ic_exam));
        data.add(new SatHachAdapter("Bài thi sa hình", R.drawable.ic_wheel));
        data.add(new SatHachAdapter("Mẹo ghi nhớ", R.drawable.ic_idea));
        return data;
    }

    @Override
    public int getType() {
        return R.id.item_layout;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_sat_hach;
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
