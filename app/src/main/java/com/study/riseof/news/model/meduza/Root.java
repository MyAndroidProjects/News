package com.study.riseof.news.model.meduza;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
public class Root {
    @SerializedName("second_title")
    @Expose
    String secondTitle;

    @SerializedName("authors")
    @Expose
    List<Object> authors = null;

    @SerializedName("document_type")
    @Expose
    String documentType;

    @SerializedName("source")
    @Expose
    Source source;

    @SerializedName("pushed")
    @Expose
    Boolean pushed;

    @SerializedName("image")
    @Expose
    NewsImage image;

    @SerializedName("share_image")
    @Expose
    String shareImage;

    @SerializedName("share_title")
    @Expose
    String shareTitle;

    @SerializedName("content")
    @Expose
    Content content;

    @SerializedName("with_banners")
    @Expose
    Boolean withBanners;

    @SerializedName("full_width")
    @Expose
    Boolean fullWidth;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("prefs")
    @Expose
    Prefs prefs;

    @SerializedName("version")
    @Expose
    Integer version;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("modified_at")
    @Expose
    Long modifiedAt;

    @SerializedName("published_at")
    @Expose
    Integer publishedAt;

    @SerializedName("pub_date")
    @Expose
    String pubDate;

    @SerializedName("tag")
    @Expose
    NewsTag tag;

    @SerializedName("footer")
    @Expose
    Footer footer;

    @SerializedName("full")
    @Expose
    Boolean full;
}
