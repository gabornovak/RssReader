package hu.gabornovak.rssreader.android.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hu.gabornovak.rssreader.R;
import hu.gabornovak.rssreader.android.fragment.SearchResultListFragment;


public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            setTitle(getString(R.string.search_activity_title, query));

            getSupportFragmentManager().beginTransaction().add(R.id.container, SearchResultListFragment.newInstance(query)).commit();
        }
    }
}
