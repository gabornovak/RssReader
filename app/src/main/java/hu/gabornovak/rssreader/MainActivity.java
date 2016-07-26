package hu.gabornovak.rssreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import hu.gabornovak.rssreader.model.RssItem;
import hu.gabornovak.rssreader.presenter.RssPresenter;
import hu.gabornovak.rssreader.presenter.RssPresenterImpl;
import hu.gabornovak.rssreader.view.RssView;

public class MainActivity extends AppCompatActivity implements RssView {
    private RssPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new RssPresenterImpl(this);
        presenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showRssList(List<RssItem> items) {

    }
}
