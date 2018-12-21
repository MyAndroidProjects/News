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
@Root(name = "channel")
public class Channel {
    @Element(name = "title", required = false)
    String title;

    @Element(name = "description", required = false)
    String description;

    @Element(name = "language", required = false)
    String language;

    @Element(name = "image", required = false)
    RssImage rssImage;

    @Element(name = "lastBuildDate", required = false)
    String lastBuildDate;

    @Element(name = "pubDate", required = false)
    String pubDate;

    @ElementList(inline = true, entry = "link", required = false)
    List<Link> linkList;

    @ElementList(inline = true, entry = "item", required = false)
    private List<Item> itemList;
}
