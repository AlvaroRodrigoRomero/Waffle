package inkirer.waffle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import inkirer.waffle.Core.AlarmAdapter;
import inkirer.waffle.Core.ConnectivityReceiver;
import inkirer.waffle.Core.MyApplication;
import inkirer.waffle.Models.AlarmModel;
import inkirer.waffle.Repositories.AlarmRepository;
import inkirer.waffle.Services.AlarmService;
import inkirer.waffle.Services.LocalStorage;
import inkirer.waffle.Services.NetworkSchedulerService;

public class Home extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private AlarmRepository _alarmRepo;
    private String _userId, _token;
    private ListView listView;
    private LocalStorage localStorage;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onResume() {
        super.onResume();
        LoadList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scheduleJob();

        _alarmRepo = new AlarmRepository(getApplicationContext());
        floatingActionButton = findViewById(R.id.floating_action_button);
        listView = findViewById(R.id.list);

        localStorage = new LocalStorage(getApplicationContext());

        _userId = localStorage.GetUserId();
        _token = localStorage.GetToken();

        LoadList();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NotificationTest();
                startActivity(new Intent(Home.this, AlarmCreate.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlarmModel clickedItem = (AlarmModel) listView.getItemAtPosition(position);
                ShowConfirmationModal(clickedItem);
            }
        });
    }

    private void LoadList() {
        final List<AlarmModel> list = _alarmRepo.GetAlarms();
        AlarmAdapter adapter = new AlarmAdapter(this, list);
        listView.setAdapter(adapter);
    }

    public void ShowConfirmationModal(final AlarmModel alarm) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Are you sure you want to remove this?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                _alarmRepo.Remove(alarm);
                AlarmService.RemoveAlarm(getApplicationContext(), alarm.uid, alarm.Hour, alarm.Minute);
                LoadList();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();
    }


    private void NotificationTest(){


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        Intent startServiceIntent = new Intent(this, NetworkSchedulerService.class);
        startService(startServiceIntent);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Internet not Connected", Toast.LENGTH_SHORT).show();
        }
    }
}
