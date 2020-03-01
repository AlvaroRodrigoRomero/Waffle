package inkirer.waffle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import inkirer.waffle.Models.AlarmModel;
import inkirer.waffle.Repositories.AlarmRepository;
import inkirer.waffle.Services.AlarmService;

public class AlarmCreate extends AppCompatActivity {

    private TimePicker tpStart, tpFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_create);

        tpStart = findViewById(R.id.tpStart);
        tpFinish = findViewById(R.id.tpFinish);
        tpStart.setIs24HourView(true);
        tpFinish.setIs24HourView(true);

    }

    public void CreateAlarm(View view){

        AlarmModel alarmStart = new AlarmModel();
        AlarmModel alarmFinish = new AlarmModel();
        AlarmRepository repo = new AlarmRepository(getApplicationContext());

        alarmStart.Hour = tpStart.getHour();
        alarmStart.Minute = tpStart.getMinute();
        alarmFinish.Hour = tpFinish.getHour();
        alarmFinish.Minute = tpFinish.getMinute();
        alarmStart.Active = true;
        alarmFinish.Active = true;

        long idStart = repo.InsertAlarm(alarmStart);
        long idFinish = repo.InsertAlarm(alarmFinish);

        AlarmService.CreateAlarm(getApplicationContext(), tpStart.getHour(), tpStart.getMinute(), (int)idStart);
        AlarmService.CreateAlarm(getApplicationContext(), tpFinish.getHour(), tpFinish.getMinute(), (int)idFinish);

        Cancel(view);
    }

    public void Cancel(View view){
        this.finish();
    }
}
