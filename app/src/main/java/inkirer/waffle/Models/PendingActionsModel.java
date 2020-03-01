package inkirer.waffle.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PendingActionsModel {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "Time")
    public String Time;
}
