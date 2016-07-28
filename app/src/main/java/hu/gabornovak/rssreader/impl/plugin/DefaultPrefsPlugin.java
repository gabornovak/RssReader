package hu.gabornovak.rssreader.impl.plugin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;

/**
 * Created by gnovak on 7/28/2016.
 */

public class DefaultPrefsPlugin implements PrefsPlugin {
    private static final String TAG = "Prefs";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
}
