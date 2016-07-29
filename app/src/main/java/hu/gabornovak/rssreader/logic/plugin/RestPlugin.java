package hu.gabornovak.rssreader.logic.plugin;

import java.io.InputStream;

/**
 * Created by gnovak on 7/29/2016.
 */

public interface RestPlugin {
    interface OnComplete<T> {
        void onSuccess(T t);

        void onError(Exception e);
    }

    void getInputStream(String url, OnComplete<InputStream> onComplete);
}
