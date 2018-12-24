package com.study.riseof.news.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.riseof.news.R;

import java.util.ArrayList;

import butterknife.BindView;

public class NewsFromJsonFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.body)
    TextView body;
    @BindView(R.id.pub_date)
    TextView pubDate;
    @BindView(R.id.news_from_json_fragment_layout)
    LinearLayout linearLayout;

    private String titleText;
    private String descriptionText;
    private String bodyText;
    private String pubDateText;
    private ArrayList<String> imageUrlList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_json_news_view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setTextToFields();
        createAndDownloadImages();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleArgs();
    }

    protected void getBundleArgs() {
        if (this.getArguments() != null) {
            Bundle args = getArguments();
            titleText = args.getString("titleText", EMPTY_STRING);
            descriptionText = args.getString("descriptionText", EMPTY_STRING);
            bodyText = args.getString("bodyText", EMPTY_STRING);
            pubDateText = args.getString("pubDateText", EMPTY_STRING);
            imageUrlList = args.getStringArrayList("imageUrlList");
        } else {
            titleText = null;
            descriptionText = null;
            bodyText = null;
            pubDateText = null;
            imageUrlList = null;
        }
    }

    private void createAndDownloadImages() {
        if (imageUrlList == null) {
            return;
        }
        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Context context = getContext();
        for (String url :
                imageUrlList) {
            ImageView imageView = new ImageView(context);
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.icon_default_news_72)
                    .into(imageView);
            imageView.setLayoutParams(imageViewLayoutParams);
            linearLayout.addView(imageView);
        }
    }


    private void setTextToFields() {
        title.setText(titleText);
        description.setText(descriptionText);
        body.setText(bodyText);
        pubDate.setText(pubDateText);
    }
}
