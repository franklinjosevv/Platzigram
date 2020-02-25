package franklinvasquez.ufps.platzigram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import franklinvasquez.ufps.platzigram.viem.ContainerActivity;
import franklinvasquez.ufps.platzigram.viem.CreateAccountActivity;
import franklinvasquez.ufps.platzigram.viem.fragment.HomeFragment;

import static android.widget.Toast.LENGTH_LONG;
import static java.net.Proxy.Type.HTTP;

public class MainActivity extends AppCompatActivity {

    Button button;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.buttom_alarma);

        //am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        //final AlarmManager[] alarmMgr = new AlarmManager[1];
        //final PendingIntent[] alarmIntent = new PendingIntent[1];

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent in =new Intent();
                //in.setAction("franklinvasquez.ufps.platzigram.action.ALARM_RECEIVER");

                //PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0,in,0);
                //am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000,pi);
                alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmRecvr.class);
                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() +
                                5 * 1000, alarmIntent);
            }
        });

    }

    public void goCreateAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
    public void goLogin(View view){
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }
    public void goEjemplo(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void goPost(View view){
        Intent intent = new Intent(this, PostManActivity.class);
        startActivity(intent);
    }

    public void goVolley(View view){
        Intent intent = new Intent(this, VolleyActivity.class);
        startActivity(intent);
    }

    public void imagenLink(View view){
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.platzi.com"));
        startActivity(viewIntent);
    }

    public void startService(View view){
        startService(new Intent(getBaseContext(), MyService.class));
    }

    public void destroyService(View view){
        stopService(new Intent(getBaseContext(), MyService.class));
    }
    public void abrirApp(View view){

        Uri number = Uri.parse("tel:3142644081");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);

        // Map point based on address
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
        // Or map point based on latitude/longitude
        // Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);

        Uri webpage = Uri.parse("http://www.android.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 10, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(webIntent);
        }

    }
}
