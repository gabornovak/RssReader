package hu.gabornovak.rssreader.logic.presenter;

import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.view.RssListView;

/**
 * Created by gnovak on 7/26/2016.
 */

public interface RssPresenter {
    void setView(RssListView view);

    void fetchRssFeed();
    void onRefresh();

    void onRssItemClick(RssItem item);
    void onRetryClick();

    void searchRssFeed(String searchText);
}
