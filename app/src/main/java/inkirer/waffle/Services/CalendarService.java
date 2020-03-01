package inkirer.waffle.Services;

import java.util.Calendar;

public class CalendarService {

    public static Calendar GetCalendar(Integer hour, Integer minute){

        Integer am_pm = 0;
        if(hour >= 12)
            am_pm = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.AM_PM, am_pm);

        return calendar;
    }
}
