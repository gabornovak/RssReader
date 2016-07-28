package hu.gabornovak.rssreader.model;

import android.content.Context;

import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssParser {
    public interface OnRssLoadedListener {
        void onRssLoaded(List<RssItem> items);

        void onError(Exception e);
    }

    public void parseRssFeed(Context context, final OnRssLoadedListener onRssLoadedListener) {
        //load feeds
        //you can also pass multiple urls
        String[] urlArr = {"https://www.nasa.gov/rss/dyn/breaking_news.rss"};
        new RssReader(context)
                .showDialog(false)
                .urls(urlArr)
                .parse(new OnRssLoadListener() {
                    @Override
                    public void onSuccess(List<com.crazyhitty.chdev.ks.rssmanager.RssItem> rssItems) {
                        System.out.println("rssItems.size() = " + rssItems.size());
                        onRssLoadedListener.onRssLoaded(convertRssItems(rssItems));
                    }

                    @Override
                    public void onFailure(String message) {
                        //TODO proper error handling
                        onRssLoadedListener.onError(new Exception(message));
                    }
                });
    }

    private List<RssItem> convertRssItems(List<com.crazyhitty.chdev.ks.rssmanager.RssItem> rssItems) {
        List<RssItem> items = new ArrayList<>();
        for (com.crazyhitty.chdev.ks.rssmanager.RssItem rssItem : rssItems) {
            RssItem item = new RssItem();
            item.setDescription(rssItem.getDescription());
            item.setTitle(rssItem.getTitle());
            item.setLink(rssItem.getLink());
            //TODO: Resolve image url
            items.add(item);
        }
        return items;
    }
}
