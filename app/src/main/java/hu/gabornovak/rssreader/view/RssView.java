package hu.gabornovak.rssreader.view;

import java.util.List;

import hu.gabornovak.rssreader.model.RssItem;

/**
 * Created by gnovak on 7/26/2016.
 */

public interface RssView {
    void showProgress();
    void hideProgress();
    void showRssList(List<RssItem> items);
}
