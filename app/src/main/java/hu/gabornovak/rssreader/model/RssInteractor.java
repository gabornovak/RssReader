package hu.gabornovak.rssreader.model;

import android.content.Context;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssInteractor {
    private RssParser rssParser;

    public RssInteractor() {
        rssParser = new RssParser();
    }

    public void downloadRss(Context context, RssParser.OnRssLoadedListener onRssLoadedListener) {
        rssParser.parseRssFeed(context,onRssLoadedListener);
    }
}
