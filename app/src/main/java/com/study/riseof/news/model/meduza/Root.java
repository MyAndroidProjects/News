package com.study.riseof.news.model.meduza;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("ALL")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Root implements Parcelable {
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


    public static final Creator<Root> CREATOR = new Creator<Root>() {
        @Override
        public Root createFromParcel(Parcel in) {

            String secondTitle = in.readString();
            List<Object> authors = null;
//            in.readTypedList(null, null);
            String documentType = in.readString();
            Source source = null;
            in.readValue(null);
            Boolean pushed = null;
            NewsImage image = null;
            in.readValue(null);
            String shareImage = in.readString();
            String shareTitle = in.readString();
            Content content = in.readParcelable(Content.class.getClassLoader());
            Boolean withBanners = null;
            Boolean fullWidth = null;
            String description = in.readString();
            Prefs prefs = null;
            in.readValue(null);
            int version = in.readInt();
            String title = in.readString();
            String url = in.readString();
            long modifiedAt = in.readLong();
            int publishedAt = in.readInt();
            String pubDate = in.readString();
            NewsTag tag = null;
            in.readValue(null);
            Footer footer = null;
            in.readValue(null);
            Boolean full = null;

            return new Root(
                    secondTitle,
                    authors,
                    documentType,
                    source,
                    pushed,
                    image,
                    shareImage,
                    shareTitle,
                    content,
                    withBanners,
                    fullWidth,
                    description,
                    prefs,
                    version,
                    title,
                    url,
                    modifiedAt,
                    publishedAt,
                    pubDate,
                    tag,
                    footer,
                    full
            );
        }

        @Override
        public Root[] newArray(int size) {
            return new Root[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(secondTitle);
        // List<Object> authors = null;
    //    dest.writeTypedList(null);
        dest.writeString(documentType);
        // Source source;
        dest.writeValue(null);
       // dest.writeByte((byte) (pushed ? 1 : 0));
        // NewsImage image;
        dest.writeValue(null);
        dest.writeString(shareImage);
        dest.writeString(shareTitle);
        dest.writeParcelable(content, flags);
      //  dest.writeByte((byte) (withBanners ? 1 : 0));
       // dest.writeByte((byte) (fullWidth ? 1 : 0));
        dest.writeString(description);
        // Prefs prefs;
        dest.writeValue(null);
        dest.writeInt(version);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeLong(modifiedAt);
        dest.writeInt(publishedAt);
        dest.writeString(pubDate);
        // NewsTag tag;
        dest.writeValue(null);
        // Footer footer;
        dest.writeValue(null);
     //   dest.writeByte((byte) (full ? 1 : 0));
    }
}
