package hu.gabornovak.rssreader.logic.presenter;

import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.view.RssView;

/**
 * Created by gnovak on 7/26/2016.
 */

public interface RssPresenter {
    void setView(RssView view);

    void onResume();
    void onStart();
    void onDestroy();
    void onRefresh();
    void onRssItemClick(RssItem item);
}
