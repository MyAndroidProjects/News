package com.study.riseof.news.model.xml;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
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
@Root(name = "item")
public class Item implements Parcelable {
    @Element(name = "title", data = true, required = false)
    String title;

    @Element(name = "description", data = true, required = false)
    String description;

    @Element(name = "guid", required = false)
    String guid;

    @Element(name = "link", required = false)
    String link;

    @Element(name = "pdalink", required = false)
    String pdalink;

    @Element(name = "author", required = false)
    String author;

    @Element(name = "category", required = false)
    String category;

    @ElementList(inline = true, entry = "enclosure", required = false)
    private List<Enclosure> enclosureList;

    @Element(name = "pubDate", required = false)
    String pubDate;

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            String title = in.readString();
            String description = in.readString();
            String guid = in.readString();
            String link = in.readString();
            String pdalink = in.readString();
            String author = in.readString();
            String category = in.readString();
            List<Enclosure> list = new ArrayList<>();
            in.readTypedList(list, Enclosure.CREATOR);
            String pubDate = in.readString();
            return new Item(title, description, guid, link, pdalink, author, category, list, pubDate);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(guid);
        dest.writeString(link);
        dest.writeString(pdalink);
        dest.writeString(author);
        dest.writeString(category);
        dest.writeTypedList(enclosureList);
        dest.writeString(pubDate);
    }
}
