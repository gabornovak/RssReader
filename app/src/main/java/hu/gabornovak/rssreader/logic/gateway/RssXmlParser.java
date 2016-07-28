package hu.gabornovak.rssreader.logic.gateway;

import java.io.InputStream;
import java.util.List;

import hu.gabornovak.rssreader.logic.model.RssItem;

/**
 * Created by gnovak on 7/28/2016.
 */

public interface RssXmlParser {
    List<RssItem> parse(InputStream in);
}

