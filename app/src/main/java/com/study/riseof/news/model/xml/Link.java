package com.study.riseof.news.model.xml;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

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
@Root(name = "link")
public class Link {
    @Attribute(name = "href", required = false)
    String href;

    @Attribute(name = "rel", required = false)
    String rel;

    @Attribute(name = "type", required = false)
    String type;

    @Text(required = false, data = true)
    public String link;

}
