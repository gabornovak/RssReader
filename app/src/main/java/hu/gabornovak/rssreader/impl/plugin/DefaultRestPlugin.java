package hu.gabornovak.rssreader.impl.plugin;

import java.io.IOException;
import java.io.InputStream;

import hu.gabornovak.rssreader.logic.plugin.RestPlugin;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gnovak on 7/29/2016.
 */

public class DefaultRestPlugin implements RestPlugin {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void getInputStream(String url, final OnComplete<InputStream> onComplete) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onComplete.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                onComplete.onSuccess(in);
                response.body().close();
            }
        });
    }
}
