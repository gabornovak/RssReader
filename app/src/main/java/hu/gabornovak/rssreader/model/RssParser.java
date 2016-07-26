package hu.gabornovak.rssreader.model;

import android.content.Context;

import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.util.List;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssParser {
    public interface OnRssLoadedListener {
        void onRssLoaded(List<RssItem> items);

        void onError(Exception e);
    }

    public void parseRssFeed(Context context, OnRssLoadedListener onRssLoadedListener) {
        //load feeds
        //you can also pass multiple urls
        String[] urlArr = {"https://www.nasa.gov/rss/dyn/breaking_news.rss"};
        new RssReader(context)
                .showDialog(false)
                .urls(urlArr)
                .parse(new OnRssLoadListener() {
                    @Override
                    public void onSuccess(List<com.crazyhitty.chdev.ks.rssmanager.RssItem> rssItems) {
                    }

                    @Override
                    public void onFailure(String message) {
                        System.out.println(message);
                    }
                });
    }
}
