package inkirer.waffle.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import inkirer.waffle.Models.PendingActionsModel;

@Dao
public interface PendingActionsDao {
    @Query("SELECT * FROM PendingActionsModel")
    List<PendingActionsModel> getAll();

    @Insert
    long Insert(PendingActionsModel pendingAction);

    @Delete
    void delete(PendingActionsModel pendingAction);
}
