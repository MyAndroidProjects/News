package com.study.riseof.news.model.meduza;

import android.os.Parcel;
import android.os.Parcelable;

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
public class MeduzaNews implements Parcelable {
    @SerializedName("root")
    @Expose
    public Root root;

    @SerializedName("documents")
    @Expose
    public Documents documents;

    public static final Creator<MeduzaNews> CREATOR = new Creator<MeduzaNews>() {
        @Override
        public MeduzaNews createFromParcel(Parcel in) {
            Root root = in.readParcelable(Root.class.getClassLoader());
            Documents documents = in.readParcelable(Documents.class.getClassLoader());
            return new MeduzaNews(root, documents);
        }

        @Override
        public MeduzaNews[] newArray(int size) {
            return new MeduzaNews[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(root, flags);
        dest.writeParcelable(documents, flags);
    }
}
