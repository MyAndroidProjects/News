package com.study.riseof.news.model.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

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
public class Item {
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

}
