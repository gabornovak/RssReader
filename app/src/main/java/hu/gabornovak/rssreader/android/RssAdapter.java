package hu.gabornovak.rssreader.android;

/**
 * Created by gnovak on 7/28/2016.
 */

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import hu.gabornovak.rssreader.databinding.ListItemRssBinding;
import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.presenter.RssPresenter;


public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {
    private final RssPresenter presenter;
    private final List<RssItem> items;

    public RssAdapter(RssPresenter presenter, List<RssItem> items) {
        this.presenter = presenter;
        this.items = items;
    }

    @Override
    public RssViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ListItemRssBinding viewDataBinding = ListItemRssBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new RssViewHolder(viewDataBinding);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RssViewHolder customViewHolder, int i) {
        ListItemRssBinding viewDataBinding = customViewHolder.getViewDataBinding();
        viewDataBinding.setPresenter(presenter);
        RssItem rssItem = items.get(i);
        viewDataBinding.setRssItem(rssItem);

        if (rssItem.isVisited()) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            viewDataBinding.image.setColorFilter(filter);
        }

        if (rssItem.getImageUrl() != null) {
            viewDataBinding.image.setImageURI(rssItem.getImageUrl());
        }
    }

    static class RssViewHolder extends RecyclerView.ViewHolder {
        private final ListItemRssBinding viewDataBinding;

        RssViewHolder(ListItemRssBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            this.viewDataBinding.executePendingBindings();
        }

        ListItemRssBinding getViewDataBinding() {
            return viewDataBinding;
        }
    }
}
