package hu.gabornovak.rssreader.impl.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import hu.gabornovak.rssreader.logic.model.RssItem;
import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;

/**
 * Simple plugin to store the visited RSS items' ids in the SharedPreferences
 *
 * NOTE: It should not use the SharedPref, because it's not the best way to do it
 * please refactor in the future to use DB.
 *
 * Created by gnovak on 7/28/2016.
 */

public class DefaultPrefsPlugin implements PrefsPlugin {
    private static final String TAG = "Prefs";
    private static final String VISITED_ITEMS_KEY = "VISITED_ITEMS_KEY";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    //Known issue, we call apply async
    @SuppressLint("CommitPrefEdits")
    public DefaultPrefsPlugin(Context context) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * KNOWN ISSUE: https://developer.android.com/reference/android/content/SharedPreferences.html#getStringSet%28java.lang.String,%20java.util.Set%3Cjava.lang.String%3E%29
     * SharedPref string set doesn't work properly if you want to save the same set back
     */
    @Override
    public Set<String> getVisitedRssItems() {
        return new HashSet<>(preferences.getStringSet(VISITED_ITEMS_KEY, new HashSet<String>()));
    }

    //FIXME: We should remove the unused items
    @Override
    public void setItemVisited(RssItem item) {
        Set<String> urls = getVisitedRssItems();
        urls.add(item.getLink());
        editor.putStringSet(VISITED_ITEMS_KEY, urls).apply();
    }
}
