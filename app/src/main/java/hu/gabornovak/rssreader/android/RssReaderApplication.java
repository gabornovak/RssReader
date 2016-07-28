package hu.gabornovak.rssreader.android;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by gnovak on 7/28/2016.
 */

public class RssReaderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
