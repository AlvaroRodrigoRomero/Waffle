package inkirer.waffle.Repositories;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

import inkirer.waffle.DataBase.AlarmDataBase;
import inkirer.waffle.DataBase.PendingActionsDao;
import inkirer.waffle.Models.AlarmModel;
import inkirer.waffle.Models.PendingActionsModel;

public class PendingActionRepository {

    private AlarmDataBase _db;

    public PendingActionRepository(Context context){
        _db = Room.databaseBuilder(context, AlarmDataBase.class, "pending_actions").allowMainThreadQueries().build();
    }

    public List<PendingActionsModel> GetPendingActions(){
        return _db.PendingActionRepositoru().getAll();
    }

    public long InsertPendingAction(PendingActionsModel pendingAction){
        try{
            return _db.PendingActionRepositoru().Insert(pendingAction);
        }
        catch(Exception ex){
            Log.d("error", ex.getMessage());
            return 0;
        }
    }

    public void Remove(PendingActionsModel pendingAction){
        _db.PendingActionRepositoru().delete(pendingAction);
    }
}
