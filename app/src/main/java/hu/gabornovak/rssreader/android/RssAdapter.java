package hu.gabornovak.rssreader.android;

/**
 * Created by gnovak on 7/28/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import hu.gabornovak.rssreader.databinding.ListItemRssBinding;
import hu.gabornovak.rssreader.logic.model.RssItem;


public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {
    private List<RssItem> items;

    public RssAdapter(List<RssItem> items) {
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
        viewDataBinding.setRssItem(items.get(i));
    }

    static class RssViewHolder extends RecyclerView.ViewHolder {
        private ListItemRssBinding viewDataBinding;

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
