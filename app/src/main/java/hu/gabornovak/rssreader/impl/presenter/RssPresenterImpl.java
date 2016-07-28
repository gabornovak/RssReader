package hu.gabornovak.rssreader.impl.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

import hu.gabornovak.rssreader.impl.gateway.DefaultRssGateway;
import hu.gabornovak.rssreader.logic.interactor.RssInteractor;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.presenter.RssPresenter;
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
        getRssFeed();
    }

    @Override
    public void onRefresh() {
        getRssFeed();
    }

    @Override
    public void onRssItemClick(RssItem item) {
        System.out.println("item.getLink() = " + item.getLink());
        Uri uri = Uri.parse(item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //TODO Handle the case when we have no browser
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {

    }

    private void getRssFeed() {
        rssInteractor.downloadRss(context, new DefaultRssGateway.OnRssLoadedListener() {
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
