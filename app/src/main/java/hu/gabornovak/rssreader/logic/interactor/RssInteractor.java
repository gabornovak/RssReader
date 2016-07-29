package hu.gabornovak.rssreader.logic.interactor;

import android.content.Context;

import hu.gabornovak.rssreader.impl.DefaultGatewayProvider;
import hu.gabornovak.rssreader.impl.gateway.DefaultRssGateway;
import hu.gabornovak.rssreader.logic.gateway.RssGateway;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssInteractor {
    public void getRssFeed(Context context, boolean force, DefaultRssGateway.OnRssLoadedListener onRssLoadedListener) {
        DefaultGatewayProvider.INSTANCE.getRssGateway().getRssFeed(context, force, onRssLoadedListener);
    }

    public void searchRssFeed(Context context, String searchText, RssGateway.OnRssLoadedListener onRssLoadedListener) {
        DefaultGatewayProvider.INSTANCE.getRssGateway().searchRssFeed(context, searchText, onRssLoadedListener);
    }
}
