package com.study.riseof.news.model.meduza;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    @SerializedName("body")
    @Expose
    String body;

    @SerializedName("layout_url")
    @Expose
    String layoutUrl;

}
