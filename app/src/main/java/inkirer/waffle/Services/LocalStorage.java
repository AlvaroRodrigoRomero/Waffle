package inkirer.waffle.Services;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private SharedPreferences sharedPreferences;
    private static final String USERID = "USERID";
    private static final String TOKEN = "TOKEN";
    private static final String STORAGE = "STORAGE";


    public LocalStorage(Context context){
        sharedPreferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public String GetUserId(){
        return sharedPreferences.getString(USERID, "");
    }

    public void SaveUserId(String userId){
        SetValue(USERID, userId);
    }

    public String GetToken(){
        return sharedPreferences.getString(TOKEN, "");
    }

    public void SaveToken(String token){
        SetValue(TOKEN, token);
    }

    private void SetValue(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
