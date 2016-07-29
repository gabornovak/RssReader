package hu.gabornovak.rssreader.impl.gateway;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hu.gabornovak.rssreader.impl.DefaultPluginProvider;
import hu.gabornovak.rssreader.logic.gateway.RssGateway;
import hu.gabornovak.rssreader.logic.gateway.RssXmlParser;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;
import hu.gabornovak.rssreader.logic.plugin.RestPlugin;

/**
 * Gateway to get every information about the RSS feed
 * <p>
 * Created by gnovak on 7/26/2016.
 */

public class DefaultRssGateway implements RssGateway {
    private final RssXmlParser rssXmlParser;
    private List<RssItem> feedCache;

    public DefaultRssGateway(RssXmlParser xmlParser) {
        rssXmlParser = xmlParser;
    }

    @Override
    public void searchRssFeed(final Context context, final String searchText, final OnRssLoadedListener onRssLoadedListener) {
        DefaultPluginProvider.INSTANCE.getRestPlugin().getInputStream("https://www.nasa.gov/rss/dyn/breaking_news.rss", new RestPlugin.OnComplete<InputStream>() {
            @Override
            public void onSuccess(InputStream inputStream) {
                List<RssItem> rssItems = rssXmlParser.parse(inputStream);
                if (rssItems != null) {
                    setVisitedItems(context, rssItems);
                    filterForSearch(rssItems, searchText);
                    onRssLoadedListener.onRssLoaded(rssItems);
                } else {
                    onRssLoadedListener.onError(new XmlPullParserException("Xml parse error"));
                }
            }

            @Override
            public void onError(Exception e) {
                onRssLoadedListener.onError(e);
            }
        });
    }

    private void filterForSearch(List<RssItem> rssItems, String searchText) {
        List<RssItem> toRemove = new ArrayList<>();
        for (RssItem rssItem : rssItems) {
            if (!containIgnoreCase(searchText, rssItem)) {
                toRemove.add(rssItem);
            }
        }
        for (RssItem rssItem : toRemove) {
            rssItems.remove(rssItem);
        }
    }

    private boolean containIgnoreCase(String searchText, RssItem rssItem) {
        return rssItem.getTitle().toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(Locale.getDefault()));
    }

    @Override
    public void getRssFeed(final Context context, boolean force, final RssGateway.OnRssLoadedListener onRssLoadedListener) {
        if (force) {
            fetchRssFeedFromServer(context, onRssLoadedListener);
        } else {
            if (feedCache != null) {
                onRssLoadedListener.onRssLoaded(feedCache);
            } else {
                fetchRssFeedFromServer(context, onRssLoadedListener);
            }
        }
    }

    private void fetchRssFeedFromServer(final Context context, final OnRssLoadedListener onRssLoadedListener) {
        DefaultPluginProvider.INSTANCE.getRestPlugin().getInputStream("https://www.nasa.gov/rss/dyn/breaking_news.rss", new RestPlugin.OnComplete<InputStream>() {
            @Override
            public void onSuccess(InputStream inputStream) {
                List<RssItem> rssItems = rssXmlParser.parse(inputStream);
                if (rssItems != null) {
                    setVisitedItems(context, rssItems);
                    feedCache = rssItems;
                    onRssLoadedListener.onRssLoaded(rssItems);
                } else {
                    onRssLoadedListener.onError(new XmlPullParserException("Xml parse error"));
                }
            }

            @Override
            public void onError(Exception e) {
                onRssLoadedListener.onError(e);
            }
        });
    }

    private void setVisitedItems(Context context, List<RssItem> rssItems) {
        PrefsPlugin prefsPlugin = DefaultPluginProvider.INSTANCE.getPrefsPlugin(context);
        for (RssItem rssItem : rssItems) {
            for (String url : prefsPlugin.getVisitedRssItems()) {
                if (url != null && url.equals(rssItem.getLink())) {
                    rssItem.setVisited(true);
                }
            }
        }
    }
}
