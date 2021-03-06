package hu.gabornovak.rssreader.logic.gateway;

import android.content.Context;

import java.util.List;

import hu.gabornovak.rssreader.logic.model.RssItem;

/**
 * Created by gnovak on 7/28/2016.
 */

public interface RssGateway {
    interface OnRssLoadedListener {
        void onRssLoaded(List<RssItem> items);

        void onError(Exception e);
    }

    void searchRssFeed(Context context, String searchText, OnRssLoadedListener onRssLoadedListener);
    void getRssFeed(Context context, boolean force, OnRssLoadedListener onRssLoadedListener);
}
