package inkirer.waffle.Core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import inkirer.waffle.Http.NiitClient;
import inkirer.waffle.Models.PendingActionsModel;
import inkirer.waffle.Repositories.PendingActionRepository;
import inkirer.waffle.Services.LocalStorage;

public class AlarmBroadcast extends BroadcastReceiver {

    private Context myContext;
    private int id;
    private String time;
    private PendingActionRepository _pendingRepo;

    @Override
    public void onReceive(Context context, Intent intent) {

        myContext = context;
        _pendingRepo = new PendingActionRepository(context);

        //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //TODO Handle device restart
        //}

        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return;
        }

        id = intent.getIntExtra("id", 0);
        time = intent.getStringExtra("time");

        LocalStorage storage = new LocalStorage(context);
        String token = storage.GetToken();
        String userId = storage.GetUserId();

        NiitClient client = new NiitClient(token);
        StringEntity entity;
        try {
            entity = new StringEntity("{\"UserId\":" + userId + ",\"TimezoneOffset\":-60,\"StartDate\":\"2019-12-15T09:30:18+01:00\",\"EndDate\":\"2019-12-15T09:30:18+01:00\",\"DeviceId\":\"WebApp\"}");
        } catch (UnsupportedEncodingException e) {
            Log.d("error", e.getMessage());
            entity = null;
        }

        client.post("/api/svc/signs/signs", entity, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Notifications.SendNotification(myContext, id, time);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable exception, JSONObject response){
                super.onFailure(statusCode, headers, exception, response);
                Log.i("Error", exception.getMessage());
                PendingActionsModel pendingActionsModel = new PendingActionsModel();
                pendingActionsModel.Time = time;

                _pendingRepo.InsertPendingAction(pendingActionsModel);
            }
        });


    }
}
