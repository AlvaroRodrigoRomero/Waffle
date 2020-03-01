package inkirer.waffle.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import inkirer.waffle.Models.AlarmModel;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM AlarmModel")
    List<AlarmModel> getAll();

    @Query("SELECT * FROM AlarmModel WHERE uid IN (:id)")
    AlarmModel getById(int id);

    @Insert
    long Insert(AlarmModel alarm);

    @Delete
    void delete(AlarmModel alarm);
}