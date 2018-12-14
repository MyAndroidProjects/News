package com.study.riseof.news.ui;

import com.study.riseof.news.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NewsSource {
    NGS(R.string.ngs_source_name, R.color.ngs_action_bar, R.color.ngs_status_bar),
    LENTA(R.string.lenta_source_name, R.color.lenta_action_bar, R.color.lenta_status_bar),
    MEDUZA(R.string.meduza_source_name, R.color.meduza_action_bar, R.color.meduza_status_bar),
    YANDEX(R.string.yandex_source_name, R.color.yandex_action_bar, R.color.yandex_status_bar),
    RIA(R.string.ria_source_name, R.color.ria_action_bar, R.color.ria_status_bar);

    final int nameId;
    final int actionBarColorId;
    final int statusBarColorId;
}
