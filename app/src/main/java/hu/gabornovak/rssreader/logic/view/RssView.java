package hu.gabornovak.rssreader.logic.view;

import java.util.List;

import hu.gabornovak.rssreader.logic.model.RssItem;

/**
 * Created by gnovak on 7/26/2016.
 */

public interface RssView {
    void showProgress();
    void showError(String s);
    void showRssList(List<RssItem> items);
}
