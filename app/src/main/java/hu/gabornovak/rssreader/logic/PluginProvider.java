package hu.gabornovak.rssreader.logic;

import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;
import hu.gabornovak.rssreader.logic.plugin.RestPlugin;

/**
 * Created by gnovak on 7/28/2016.
 */
public interface PluginProvider<Context> {
    PrefsPlugin getPrefsPlugin(Context context);

    RestPlugin getRestPlugin();
}
