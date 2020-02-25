package franklinvasquez.ufps.platzigram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmRecvr extends BroadcastReceiver {

    public AlarmRecvr() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarma! !",Toast.LENGTH_LONG).show();
    }
}
