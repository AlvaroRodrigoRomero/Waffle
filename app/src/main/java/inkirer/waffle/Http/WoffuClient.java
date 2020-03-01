package inkirer.waffle.Http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.StringEntity;

public class WoffuClient implements IHttpClient {

    private static AsyncHttpClient _client;
    private static final String BASE_URL = "https://app.woffu.com";


    public WoffuClient() {
        _client = new AsyncHttpClient();
        _client.addHeader("Content-Type", "application/json");
    }

    @Override
    public void get(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        _client.get(null, getAbsoluteUrl(url), entity, "text/plain", responseHandler);
    }

    @Override
    public void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        _client.post(null, getAbsoluteUrl(url), entity,"text/plain", responseHandler);
    }

    private static  String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
