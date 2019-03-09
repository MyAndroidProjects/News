package com.study.riseof.news.fragment.rss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.riseof.news.R;
import com.study.riseof.news.model.xml.Item;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RssRecyclerViewAdapter extends RecyclerView.Adapter<RssRecyclerViewAdapter.Holder> {
    private final LayoutInflater inflater;
    private final ArrayList<Item> rssList;
    private RssNewsClickListener rssNewsClickListener;
    private final int imageWidth;
    private final int imageHeight;
    @SuppressWarnings("FieldCanBeLocal")
    private final String EMPTY_STRING = "";

    RssRecyclerViewAdapter(Context context, ArrayList<Item> rssList) {
        this.rssList = rssList;
        imageWidth = context.getResources().getInteger(R.integer.size_xxxxxlarge_integer);
        imageHeight = context.getResources().getInteger(R.integer.size_xxxxxlarge_integer);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_recycler_view_rss, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final Item item = rssList.get(position);
        holder.newsHeader.setText(item.getTitle());
        String newsCutTextRaw = item.getDescription();
        String newsCutTextParsed;
        if (newsCutTextRaw != null) {
            newsCutTextParsed = Jsoup.parse(newsCutTextRaw).text();
        } else {
            newsCutTextParsed = EMPTY_STRING;
        }
        holder.newsCutText.setText(newsCutTextParsed);
        holder.pubDate.setText((item.getPubDate()));
        holder.getAdapterPosition();
        if (item.getEnclosureList() != null) {
            Picasso.get()
                    .load(item.getEnclosureList().get(0).getUrl())
                    .placeholder(R.drawable.icon_default_news_72)
                    .resize(imageWidth, imageHeight)
                    .centerCrop()
                    .into(holder.newsImage);
        }
        holder.rssItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rssNewsClickListener != null) {
                    rssNewsClickListener.rssNewsClick(holder.getAdapterPosition(), item.getLink());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.rss_item)
        CardView rssItem;
        @BindView(R.id.news_image)
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

    void setRssNewsClickListener(RssNewsClickListener rssNewsClickListener) {
        this.rssNewsClickListener = rssNewsClickListener;
    }

    public interface RssNewsClickListener {
        void rssNewsClick(int position, String newsUrl);
    }
}
