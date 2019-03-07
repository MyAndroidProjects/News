package com.study.riseof.news.fragment.newsFromJson;

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
import com.study.riseof.news.fragment.BaseFragment;
import com.study.riseof.news.model.meduza.MeduzaNews;
import com.study.riseof.news.network.JsonConverterRetrofit;

import org.jsoup.Jsoup;

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
    @BindView(R.id.image_news)
    ImageView imageNews;

    @BindView(R.id.news_from_json_fragment_layout)
    LinearLayout linearLayout;


    private MeduzaNews meduzaNews = null;
    private String meduzaNewsVarName = "meduzaNews";
    private String imageUrl = null;
    private String imageUrlVarName = "imageUrl";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_json_news_view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (savedInstanceState != null) {
            meduzaNews = savedInstanceState.getParcelable(meduzaNewsVarName);
        }
        setTextToFields();
        createAndDownloadImages();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleArgs(savedInstanceState);
    }

    private void getBundleArgs(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            meduzaNews = args.getParcelable(meduzaNewsVarName);
            if(meduzaNews ==null){
                meduzaNews = savedInstanceState.getParcelable(meduzaNewsVarName);            }
        }
        if (savedInstanceState != null) {
            imageUrl = savedInstanceState.getString(imageUrlVarName, null);
        } else {
            imageUrl = null;
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(imageUrlVarName, imageUrl);
        outState.putParcelable(meduzaNewsVarName,meduzaNews);
    }

    private void createAndDownloadImages() {
        if (imageUrl == null) {
            imageUrl = JsonConverterRetrofit.MEDUZA.getMainUrl() + meduzaNews.getRoot().getShareImage();
        }
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.icon_default_news_72)
                .into(imageNews);
    }

    private void setTextToFields() {
        if (meduzaNews != null) {
            String titleText = meduzaNews.getRoot().getTitle();
            String descriptionText = meduzaNews.getRoot().getDescription();
            String bodyTextHtml = meduzaNews.getRoot().getContent().getBody();
            String bodyText;
            if (bodyTextHtml != null) {
                bodyText = Jsoup.parse(bodyTextHtml).text();
            } else {
                bodyText = EMPTY_STRING;
            }
            String pubDateText = meduzaNews.getRoot().getPubDate();

            title.setText(titleText);
            description.setText(descriptionText);
            body.setText(bodyText);
            pubDate.setText(pubDateText);
        }
    }

    @Override
    public void nullifyPresenter() {

    }

    @Override
    public void setPresenter() {

    }
}
