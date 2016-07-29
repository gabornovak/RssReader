package hu.gabornovak.rssreader.impl.presenter;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import hu.gabornovak.rssreader.R;
import hu.gabornovak.rssreader.impl.DefaultPluginProvider;
import hu.gabornovak.rssreader.impl.gateway.DefaultRssGateway;
import hu.gabornovak.rssreader.logic.interactor.RssInteractor;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.presenter.RssPresenter;
import hu.gabornovak.rssreader.logic.view.RssListView;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssPresenterImpl implements RssPresenter {
    private final Activity activity;

    private RssListView rssListView;
    private final RssInteractor rssInteractor;

    public RssPresenterImpl(Activity activity) {
        rssInteractor = new RssInteractor();
        this.activity = activity;
    }

    @Override
    public void setView(RssListView view) {
        this.rssListView = view;
    }

    @Override
    public void fetchRssFeed() {
        rssListView.showProgress();
        getRssFeed(false);
    }

    @Override
    public void onRefresh() {
        getRssFeed(true);
    }

    @Override
    public void onRssItemClick(RssItem item) {
        String url = item.getLink();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        builder.setStartAnimations(activity, R.anim.fade_in, R.anim.fade_out);
        builder.setExitAnimations(activity, R.anim.fade_in, R.anim.fade_out);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(url));
        setItemVisited(item);
    }

    @Override
    public void onRetryClick() {
        rssListView.showProgress();
        getRssFeed(true);
    }

    @Override
    public void searchRssFeed(String searchText) {
        rssListView.showProgress();
        rssInteractor.searchRssFeed(activity, searchText, new DefaultRssGateway.OnRssLoadedListener() {
            @Override
            public void onRssLoaded(List<RssItem> items) {
                rssListView.showRssList(items);
            }

            @Override
            public void onError(Exception e) {
                rssListView.showError(resolveErrorMessage(e));
            }
        });
    }

    private void setItemVisited(RssItem item) {
        DefaultPluginProvider.INSTANCE.getPrefsPlugin(activity).setItemVisited(item);
        item.setVisited(true);
        rssListView.refreshList();
    }

    private void getRssFeed(boolean force) {
        rssInteractor.getRssFeed(activity, force, new DefaultRssGateway.OnRssLoadedListener() {
            @Override
            public void onRssLoaded(List<RssItem> items) {
                rssListView.showRssList(items);
            }

            @Override
            public void onError(Exception e) {
                rssListView.showError(resolveErrorMessage(e));
            }
        });
    }

    private String resolveErrorMessage(Exception e) {
        if (e instanceof XmlPullParserException) {
            return activity.getString(R.string.error_text_xml_parser);
        } else if (e instanceof IOException) {
            return activity.getString(R.string.error_text_no_network);
        }
        return activity.getString(R.string.error_text_default);
    }
}
