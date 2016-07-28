package hu.gabornovak.rssreader.logic.interactor;

import android.content.Context;

import hu.gabornovak.rssreader.impl.DefaultGatewayProvider;
import hu.gabornovak.rssreader.impl.gateway.DefaultRssGateway;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssInteractor {
    public void downloadRss(Context context, DefaultRssGateway.OnRssLoadedListener onRssLoadedListener) {
        DefaultGatewayProvider.INSTANCE.getRssGateway().parseRssFeed(context,onRssLoadedListener);
    }
}
