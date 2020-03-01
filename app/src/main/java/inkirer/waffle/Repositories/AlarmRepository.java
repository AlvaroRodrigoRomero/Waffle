package inkirer.waffle.Repositories;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

import inkirer.waffle.Models.AlarmModel;
import inkirer.waffle.DataBase.AlarmDataBase;

public class AlarmRepository {

    private AlarmDataBase _db;

    public AlarmRepository(Context context){
        _db = Room.databaseBuilder(context, AlarmDataBase.class, "alarms").allowMainThreadQueries().build();
    }

    public List<AlarmModel> GetAlarms(){
        return _db.AlarmRepository().getAll();
    }

    public long InsertAlarm(AlarmModel alarm){
        try{
            return _db.AlarmRepository().Insert(alarm);
        }
        catch(Exception ex){
            Log.d("error", ex.getMessage());
            return 0;
        }
    }

    public void Remove(AlarmModel alarm){
        _db.AlarmRepository().delete(alarm);
    }
}
