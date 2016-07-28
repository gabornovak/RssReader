package hu.gabornovak.rssreader;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import hu.gabornovak.rssreader.databinding.ActivityMainBinding;
import hu.gabornovak.rssreader.model.RssItem;
import hu.gabornovak.rssreader.presenter.RssPresenter;
import hu.gabornovak.rssreader.presenter.RssPresenterImpl;
import hu.gabornovak.rssreader.view.RssView;

public class MainActivity extends AppCompatActivity implements RssView {
    private RssPresenter presenter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        initPresenter();
    }

    private void initPresenter() {
        presenter = new RssPresenterImpl(this);
        presenter.setView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
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
    public void showRssList(final List<RssItem> items) {
        RssAdapter rssAdapter = new RssAdapter(items);
        System.out.println("items = " + items.size());
        binding.recycleView.setAdapter(rssAdapter);
    }
}
