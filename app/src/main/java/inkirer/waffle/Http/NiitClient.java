package inkirer.waffle.Http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.entity.StringEntity;

public class NiitClient implements IHttpClient {

    private static AsyncHttpClient _client;
    private static String _token;
    private static final String BASE_URL = "https://niit.woffu.com";


    public NiitClient(String token) {
        _client = new AsyncHttpClient();
        _client.addHeader("Content-Type", "application/json");
        _client.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    public void get(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        _client.get(getAbsoluteUrl(url), responseHandler);
    }

    @Override
    public void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        _client.post(null, getAbsoluteUrl(url), entity,"application/json", responseHandler);
    }

    private static  String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
