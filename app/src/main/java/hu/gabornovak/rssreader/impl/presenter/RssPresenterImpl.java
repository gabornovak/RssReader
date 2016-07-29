package hu.gabornovak.rssreader.impl.presenter;

import android.app.Activity;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import java.util.List;

import hu.gabornovak.rssreader.R;
import hu.gabornovak.rssreader.impl.DefaultPluginProvider;
import hu.gabornovak.rssreader.impl.gateway.DefaultRssGateway;
import hu.gabornovak.rssreader.logic.interactor.RssInteractor;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.presenter.RssPresenter;
import hu.gabornovak.rssreader.logic.view.RssView;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssPresenterImpl implements RssPresenter {
    private final Activity activity;

    private RssView rssView;
    private final RssInteractor rssInteractor;

    public RssPresenterImpl(Activity activity) {
        rssInteractor = new RssInteractor();
        this.activity = activity;
    }

    @Override
    public void setView(RssView view) {
        this.rssView = view;
    }

    @Override
    public void fetchRssFeed() {
        rssView.showProgress();
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
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(url));
    }

    @Override
    public void onRetryClick() {
        rssView.showProgress();
        getRssFeed(true);
    }

    @Override
    public void searchRssFeed(String searchText) {
        rssView.showProgress();
        rssInteractor.searchRssFeed(activity, searchText, new DefaultRssGateway.OnRssLoadedListener() {
            @Override
            public void onRssLoaded(List<RssItem> items) {
                rssView.showRssList(items);
            }

            @Override
            public void onError(Exception e) {
                rssView.showError("TODO: Error msg");
            }
        });
    }

    private void setItemVisited(RssItem item) {
        DefaultPluginProvider.INSTANCE.getPrefsPlugin(activity).setItemVisited(item);
        item.setVisited(true);
        rssView.refreshList();
    }

    private void getRssFeed(boolean force) {
        rssInteractor.getRssFeed(activity, force, new DefaultRssGateway.OnRssLoadedListener() {
            @Override
            public void onRssLoaded(List<RssItem> items) {
                rssView.showRssList(items);
            }

            @Override
            public void onError(Exception e) {
                rssView.showError("TODO: Error msg");
            }
        });
    }
}
