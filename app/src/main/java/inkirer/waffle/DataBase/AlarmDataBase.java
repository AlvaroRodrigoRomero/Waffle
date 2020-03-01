package inkirer.waffle.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import inkirer.waffle.Models.AlarmModel;
import inkirer.waffle.Models.PendingActionsModel;

@Database(version = 1, entities = {AlarmModel.class, PendingActionsModel.class})
public abstract class AlarmDataBase extends RoomDatabase {

    abstract public AlarmDao AlarmRepository();
    abstract public PendingActionsDao PendingActionRepositoru();
}
