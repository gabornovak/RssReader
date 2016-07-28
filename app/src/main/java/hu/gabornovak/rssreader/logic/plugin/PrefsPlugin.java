package hu.gabornovak.rssreader.logic.plugin;

import java.util.Set;

import hu.gabornovak.rssreader.logic.model.RssItem;

/**
 * Created by gnovak on 7/28/2016.
 */

public interface PrefsPlugin {
    Set<String> getVisitedRssItems();
    void setItemVisited(RssItem item);
}
