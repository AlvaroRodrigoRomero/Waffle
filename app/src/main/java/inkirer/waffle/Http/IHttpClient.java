package inkirer.waffle.Http;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.StringEntity;

public interface IHttpClient {

    void get(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler);

    void post(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler);

}
