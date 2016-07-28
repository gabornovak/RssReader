package hu.gabornovak.rssreader.impl.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;

/**
 * Created by gnovak on 7/28/2016.
 */

public class DefaultPrefsPlugin implements PrefsPlugin {
    private static final String TAG = "Prefs";
    private static final String VISITED_ITEMS_KEY = "VISITED_ITEMS_KEY";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Set<String> cache;

    //Known issue, we call apply async
    @SuppressLint("CommitPrefEdits")
    public DefaultPrefsPlugin(Context context) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    public void saveStringSet(String key, Set<String> set) {
        editor.putStringSet(key, set).apply();
    }

    @Override
    public Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, new HashSet<String>());
    }

    @Override
    public Set<String> getVisitedRssItems() {
        if (cache != null) {
            return cache;
        } else {
            cache = getStringSet(VISITED_ITEMS_KEY);
        }
        return cache;
    }

    //FIXME: We should remove the unused items
    @Override
    public void setItemVisited(RssItem item) {
        if (cache != null) {
            cache.add(item.getLink());
        }
        saveItemVisited(item);
    }

    private void saveItemVisited(RssItem item) {
        Set<String> urls;
        Set<String> set = getStringSet(VISITED_ITEMS_KEY);
        if (set == null) {
            urls = new HashSet<>();
        } else {
            urls = set;
        }
        urls.add(item.getLink());
        saveStringSet(VISITED_ITEMS_KEY, urls);
    }
}
