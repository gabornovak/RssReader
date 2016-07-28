package hu.gabornovak.rssreader.android;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
    private RssAdapter rssAdapter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initPresenter();

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
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
    public void showProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.errorLayout.setVisibility(View.GONE);
                binding.recycleView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showError(final String s) {
        binding.swipeRefreshLayout.setRefreshing(false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.errorText.setText(s);
                binding.progressBar.setVisibility(View.GONE);
                binding.errorLayout.setVisibility(View.VISIBLE);
                binding.recycleView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showRssList(final List<RssItem> items) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.progressBar.setVisibility(View.GONE);
                binding.errorLayout.setVisibility(View.GONE);
                binding.recycleView.setVisibility(View.VISIBLE);

                rssAdapter = new RssAdapter(presenter, items);
                binding.recycleView.setAdapter(rssAdapter);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void refreshList() {
        if (rssAdapter != null) {
            rssAdapter.notifyDataSetChanged();
        }
    }

    public void onRetryClick(View view) {
        presenter.onRetryClick();
    }
}
