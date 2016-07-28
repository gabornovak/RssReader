package hu.gabornovak.rssreader.logic;

import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;

/**
 * Created by gnovak on 7/28/2016.
 */
public interface PluginProvider<Context> {
    PrefsPlugin getPrefsPlugin(Context context);
}
