package hu.gabornovak.rssreader.logic.plugin;

import java.util.Set;

/**
 * Created by gnovak on 7/28/2016.
 */

public interface PrefsPlugin {
    void saveStringSet(String key, Set<String> set);
    Set<String> getStringSet(String key);
}
