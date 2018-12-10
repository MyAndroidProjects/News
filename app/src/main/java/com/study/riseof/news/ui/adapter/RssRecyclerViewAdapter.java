package com.study.riseof.news.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.riseof.news.R;
import com.study.riseof.news.model.ngs.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RssRecyclerViewAdapter extends RecyclerView.Adapter<RssRecyclerViewAdapter.Holder> {
    private final LayoutInflater inflater;
    private final List<Item> rssList;
    private final Context context;

    public RssRecyclerViewAdapter(Context context, List<Item> rssList) {
        this.rssList = rssList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_recycler_view_rss, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Item item = rssList.get(position);
        holder.newsHeader.setText(item.getTitle());
        holder.newsCutText.setText(item.getDescription());
        holder.pubDate.setText((item.getPubDate()));
    }

    @Override
    public int getItemCount() {
        return rssList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_image2)
        ImageView newsImage;
        @BindView(R.id.news_header)
        TextView newsHeader;
        @BindView(R.id.news_cut_text)
        TextView newsCutText;
        @BindView(R.id.pub_date)
        TextView pubDate;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
