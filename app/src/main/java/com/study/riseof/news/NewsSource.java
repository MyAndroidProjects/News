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
    YANDEX(R.string.yandex_source_name, R.color.yandex_action_bar, R.color.yandex_status_bar, XmlConverterRetrofit.YANDEX.getRetrofitApi()),
    RBC(R.string.rbc_source_name, R.color.rbc_action_bar, R.color.rbc_status_bar, XmlConverterRetrofit.RBC.getRetrofitApi());

    final int nameId;
    final int actionBarColorId;
    final int statusBarColorId;
    final RetrofitApi.Xml xmlApi;
}
