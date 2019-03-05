package com.study.riseof.news.model.meduza;

import android.os.Parcel;
import android.os.Parcelable;

public class Documents implements Parcelable {
    public static final Creator<Documents> CREATOR = new Creator<Documents>() {
        @Override
        public Documents createFromParcel(Parcel in) {
            return new Documents();
        }

        @Override
        public Documents[] newArray(int size) {
            return new Documents[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
