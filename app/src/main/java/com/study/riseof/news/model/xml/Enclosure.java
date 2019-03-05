package com.study.riseof.news.model.xml;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

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
@Root(name = "enclosure")

public class Enclosure implements Parcelable {
    @Attribute(name = "type", required = false)
    String type;

    @Attribute(name = "url", required = false)
    String url;

    @Attribute(name = "length", required = false)
    String length;

    public static final Creator<Enclosure> CREATOR = new Creator<Enclosure>() {
        @Override
        public Enclosure createFromParcel(Parcel in) {
            String type = in.readString();
            String url = in.readString();
            String length = in.readString();
            return new Enclosure(type, url, length);
        }

        @Override
        public Enclosure[] newArray(int size) {
            return new Enclosure[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(length);
    }
}
