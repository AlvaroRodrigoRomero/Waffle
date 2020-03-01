package inkirer.waffle.Services;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import inkirer.waffle.Core.AlarmBroadcast;
import inkirer.waffle.Models.PendingActionsModel;
import inkirer.waffle.Repositories.PendingActionRepository;

public class PendingActionService {
    private Context _context;
    private PendingActionRepository _repo;

    public PendingActionService(Context context){
        this._context = context;
        this._repo = new PendingActionRepository(context);
    }

    public void ProcessPendingActions(){
        List<PendingActionsModel> pendingActions = _repo.GetPendingActions();

        if(pendingActions != null){
            for (PendingActionsModel pendingAction : pendingActions) {
                Intent intent = new Intent(this._context, AlarmBroadcast.class);
                intent.putExtra("time", pendingAction.Time);
                intent.putExtra("id", 120);

                _repo.Remove(pendingAction);

                AlarmBroadcast broadcast = new AlarmBroadcast();
                broadcast.onReceive(this._context, intent);
            }
        }
    }
}
