package com.study.riseof.news.model.ngs;

import com.study.riseof.news.model.ngs.Channel;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Version;

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
@Root(name = "rss")
public class NgsRss {
    @Version
    double version;

    @Element(name = "channel", required = false)
    Channel channel;
}
