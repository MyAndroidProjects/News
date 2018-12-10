package com.study.riseof.news.model.ngs;

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
public class Enclosure {
    @Attribute(name = "type", required = false)
    String type;

    @Attribute(name = "url", required = false)
    String url;
}
