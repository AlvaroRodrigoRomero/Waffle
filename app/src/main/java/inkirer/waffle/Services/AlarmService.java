package inkirer.waffle.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import inkirer.waffle.Core.AlarmBroadcast;

public class AlarmService {

    public static void CreateAlarm(Context context, Integer hour, Integer minute, Integer id){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcast.class);
        intent.putExtra("time", hour + ":" + minute);
        intent.putExtra("id", id);
        intent.setAction("inkirer.waffle.Core.AlarmBroadcast");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = CalendarService.GetCalendar(hour, minute);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public static void RemoveAlarm(Context context, Integer id, Integer hour, Integer minute) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcast.class);
        intent.putExtra("time", hour + ":" + minute);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }
}
