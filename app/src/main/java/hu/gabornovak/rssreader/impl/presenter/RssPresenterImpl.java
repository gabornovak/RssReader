package hu.gabornovak.rssreader.impl.presenter;

import android.content.Context;

import java.util.List;

import hu.gabornovak.rssreader.logic.presenter.RssPresenter;
import hu.gabornovak.rssreader.logic.interactor.RssInteractor;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.impl.gateway.DefaultRssGateway;
import hu.gabornovak.rssreader.logic.view.RssView;

/**
 * Created by gnovak on 7/26/2016.
 */

public class RssPresenterImpl implements RssPresenter {
    private Context context;

    private RssView rssView;
    private RssInteractor rssInteractor;

    public RssPresenterImpl(Context context) {
        rssInteractor = new RssInteractor();
        this.context = context;
    }

    @Override
    public void setView(RssView view) {
        this.rssView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {
        rssView.showProgress();
        rssInteractor.downloadRss(context, new DefaultRssGateway.OnRssLoadedListener() {
            @Override
            public void onRssLoaded(List<RssItem> items) {
                rssView.hideProgress();
                rssView.showRssList(items);
            }

            @Override
            public void onError(Exception e) {
                rssView.hideProgress();
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
