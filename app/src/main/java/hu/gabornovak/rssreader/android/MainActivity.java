package hu.gabornovak.rssreader.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import hu.gabornovak.rssreader.R;
import hu.gabornovak.rssreader.databinding.ActivityMainBinding;
import hu.gabornovak.rssreader.impl.presenter.RssPresenterImpl;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.presenter.RssPresenter;
import hu.gabornovak.rssreader.logic.view.RssView;

public class MainActivity extends AppCompatActivity implements RssView {
    private RssPresenter presenter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initPresenter();

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });
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
        fadeHide(binding.errorLayout, binding.recycleView);
        fadeShow(binding.progressBar);
    }

    @Override
    public void showError(String s) {
        fadeHide(binding.progressBar);
        fadeShow(binding.errorLayout, binding.recycleView);
    }

    @Override
    public void showRssList(final List<RssItem> items) {
        fadeHide(binding.progressBar, binding.errorLayout);
        fadeShow(binding.recycleView);
        final RssAdapter rssAdapter = new RssAdapter(presenter, items);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.recycleView.setAdapter(rssAdapter);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void fadeShow(final View... views) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (View view : views) {
                    view.animate().alpha(1).setDuration(100).start();
                }
            }
        });
    }

    private void fadeHide(final View... views) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (View view : views) {
                    view.animate().alpha(0).setDuration(100).start();
                }
            }
        });
    }
}
