package hu.gabornovak.rssreader.impl.gateway;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import hu.gabornovak.rssreader.impl.DefaultPluginProvider;
import hu.gabornovak.rssreader.logic.gateway.RssGateway;
import hu.gabornovak.rssreader.logic.gateway.RssXmlParser;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gnovak on 7/26/2016.
 */

public class DefaultRssGateway implements RssGateway {
    private RssXmlParser rssXmlParser;
    private OkHttpClient client = new OkHttpClient();

    public DefaultRssGateway(RssXmlParser xmlParser) {
        rssXmlParser = xmlParser;
    }

    @Override
    public void parseRssFeed(final Context context, final RssGateway.OnRssLoadedListener onRssLoadedListener) {
        Request request = new Request.Builder().url("https://www.nasa.gov/rss/dyn/breaking_news.rss").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onRssLoadedListener.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                List<RssItem> rssItems = rssXmlParser.parse(in);
                response.body().close();
                if (rssItems != null) {
                    setVisitedItems(context, rssItems);
                    onRssLoadedListener.onRssLoaded(rssItems);
                } else {
                    onRssLoadedListener.onError(new XmlPullParserException("Xml parse error"));
                }
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
