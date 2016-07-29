package hu.gabornovak.rssreader.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by gnovak on 7/29/2016.
 */

public class SearchResultListFragment extends RssItemsListFragment {
    private static final String ARG_SEARCH_TEXT = "search text";

    public static Fragment newInstance(String searchText) {
        Fragment fragment = new SearchResultListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_SEARCH_TEXT, searchText);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.searchRssFeed(getArguments().getString(ARG_SEARCH_TEXT, ""));
    }
}
