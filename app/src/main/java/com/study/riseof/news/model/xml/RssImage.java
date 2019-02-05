package com.study.riseof.news.model.xml;

import org.simpleframework.xml.Element;
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
@Root(name = "image")
public class RssImage {

    @Element(name = "url", required = false)
    String imageUrl;

    @Element(name = "title", required = false)
    String title;

    @Element(name = "link", required = false)
    String link;
    @Element(name = "width", required = false)
    int width;
    @Element(name = "height", required = false)
    int height;

}
