package hu.gabornovak.rssreader.impl;

import android.content.Context;

import hu.gabornovak.rssreader.impl.plugin.DefaultPrefsPlugin;
import hu.gabornovak.rssreader.logic.PluginProvider;
import hu.gabornovak.rssreader.logic.plugin.PrefsPlugin;

/**
 * Singleton class, to provide the default gateways for the interactors
 * <p>
 * Created by gnovak on 7/28/2016.
 */

public enum DefaultPluginProvider implements PluginProvider<Context> {
    INSTANCE;

    @Override
    public PrefsPlugin getPrefsPlugin(Context context) {
        return new DefaultPrefsPlugin(context);
    }
}
