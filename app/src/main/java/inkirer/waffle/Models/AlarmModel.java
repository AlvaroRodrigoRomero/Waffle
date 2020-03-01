package inkirer.waffle.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AlarmModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "hour")
    public Integer Hour;

    @ColumnInfo(name = "minute")
    public Integer Minute;

    @ColumnInfo(name = "active")
    public boolean Active;
}

