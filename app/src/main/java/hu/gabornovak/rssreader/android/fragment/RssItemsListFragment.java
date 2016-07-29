package hu.gabornovak.rssreader.android.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hu.gabornovak.rssreader.R;
import hu.gabornovak.rssreader.android.RssAdapter;
import hu.gabornovak.rssreader.databinding.FragmentRssItemsListBinding;
import hu.gabornovak.rssreader.impl.presenter.RssPresenterImpl;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.presenter.RssPresenter;
import hu.gabornovak.rssreader.logic.view.RssView;

/**
 * Created by gnovak on 7/29/2016.
 */

public class RssItemsListFragment extends Fragment implements RssView {
    protected RssPresenter presenter;
    private RssAdapter rssAdapter;

    private FragmentRssItemsListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rss_items_list, container, false);

        initPresenter();

        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });

        binding.errorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetryClick();
            }
        });

        return binding.getRoot();
    }

    private void initPresenter() {
        presenter = new RssPresenterImpl(getActivity());
        presenter.setView(this);
    }

    private void onRetryClick() {
        presenter.onRetryClick();
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.fetchRssFeed();
    }

    @Override
    public void showProgress() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.errorLayout.setVisibility(View.GONE);
                    binding.recycleView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showError(final String s) {
        if (getActivity() != null) {
            binding.swipeRefreshLayout.setRefreshing(false);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.errorText.setText(s);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.errorLayout.setVisibility(View.VISIBLE);
                    binding.recycleView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void showRssList(final List<RssItem> items) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
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
    }

    @Override
    public void refreshList() {
        if (rssAdapter != null) {
            rssAdapter.notifyDataSetChanged();
        }
    }

    public static Fragment newInstance() {
        return new RssItemsListFragment();
    }
}
