package com.study.riseof.news;

import com.study.riseof.news.R;
import com.study.riseof.news.network.RetrofitApi;
import com.study.riseof.news.network.XmlConverterRetrofit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NewsSource {
    EMPTY(R.string.empty_source_name, R.color.default_action_bar, R.color.default_status_bar, null),
    NGS(R.string.ngs_source_name, R.color.ngs_action_bar, R.color.ngs_status_bar, XmlConverterRetrofit.NGS.getRetrofitApi()),
    LENTA(R.string.lenta_source_name, R.color.lenta_action_bar, R.color.lenta_status_bar, XmlConverterRetrofit.LENTA.getRetrofitApi()),
    MEDUZA(R.string.meduza_source_name, R.color.meduza_action_bar, R.color.meduza_status_bar, XmlConverterRetrofit.MEDUZA.getRetrofitApi()),
    WASHINGTONPOST(R.string.washingtonpost_source_name, R.color.washingtonpost_action_bar, R.color.washingtonpost_status_bar, XmlConverterRetrofit.WASHINGTONPOST.getRetrofitApi()),
    RT(R.string.rt_source_name, R.color.rt_action_bar, R.color.rt_status_bar, XmlConverterRetrofit.RT.getRetrofitApi());

    final int nameId;
    final int actionBarColorId;
    final int statusBarColorId;
    final RetrofitApi.Xml xmlApi;
}
