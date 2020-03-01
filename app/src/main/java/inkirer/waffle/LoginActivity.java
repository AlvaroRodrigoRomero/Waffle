package inkirer.waffle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import inkirer.waffle.Http.NiitClient;
import inkirer.waffle.Http.WoffuClient;
import inkirer.waffle.Services.LocalStorage;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private TextView lblError;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        lblError = findViewById(R.id.lblError);

        localStorage = new LocalStorage(getApplicationContext());
        if(!localStorage.GetToken().equals("")){
            GotHome();
        }

    }

    public void onLogin(View view){
        lblError.setVisibility(View.INVISIBLE);

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if(email.equals("") || password.equals("")){
            lblError.setVisibility(View.VISIBLE);
            lblError.setText("All fields are mandatory");
            return;
        }

        WoffuClient client = new WoffuClient();
        StringEntity entity = new StringEntity("grant_type=password&username=" + email + "&password=" + password, "UTF-8");
        client.post("/token", entity, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String token = response.getString("access_token");
                    Log.d("token", response.getString("access_token"));
                    GetUserId(token);
                } catch (JSONException e) {
                    lblError.setVisibility(View.VISIBLE);
                    lblError.setText("There was an error, try again later.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable exception, JSONObject response) {
                Log.d("error", exception.getMessage());
                lblError.setVisibility(View.VISIBLE);
                lblError.setText("There was an error, try again later.");
            }
        });
    }

    private void GetUserId(final String token){
        NiitClient client = new NiitClient(token);
        client.get("/api/users", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String userId = response.getString("UserId");
                    Log.d("UserId", response.getString("UserId"));
                    SaveData(token, userId);
                    GotHome();
                } catch (JSONException e) {
                    lblError.setVisibility(View.VISIBLE);
                    lblError.setText("There was an error, try again later.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("error", throwable.getMessage());
                lblError.setVisibility(View.VISIBLE);
                lblError.setText("There was an error, try again later.");
            }
        });
    }

    private void SaveData(String token, String userId){
        localStorage.SaveToken(token);
        localStorage.SaveUserId(userId);
    }

    private void GotHome(){
        startActivity(new Intent(LoginActivity.this, Home.class));
    }
}
