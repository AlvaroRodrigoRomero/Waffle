package inkirer.waffle.Core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import inkirer.waffle.Models.AlarmModel;
import inkirer.waffle.R;

public class AlarmAdapter extends ArrayAdapter<AlarmModel> {
    public AlarmAdapter(Context context, List<AlarmModel> alarms) {
        super(context, 0, alarms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmModel alarm = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        TextView id = convertView.findViewById(R.id.lblId);
        TextView date = convertView.findViewById(R.id.lblDate);

        id.setText(String.valueOf(alarm.uid));

        String startText = String.valueOf(alarm.Hour) + ":" + String.valueOf(alarm.Minute);
        String finalText = startText;

        date.setText(finalText);

        return convertView;
    }
}
