package hu.gabornovak.rssreader.presenter;

import hu.gabornovak.rssreader.view.RssView;

/**
 * Created by gnovak on 7/26/2016.
 */

public interface RssPresenter {
    void setView(RssView view);

    void onResume();
    void onStart();
    void onDestroy();

}
