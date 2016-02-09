package dk.cngroup.m_calendar;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.mainLayout)
    LinearLayout mainLayout;

    @ViewById(R.id.roomStatus)
    TextView roomStatus;

    @FragmentById(R.id.fragment2)
    MeetingFragment meetingFragment;

    @FragmentById(R.id.fragment3)
    UpcomingMeetingsFragment upcomingMeetingsFragment;

    private Meeting currentMeeting = null;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void fillMeeting() {

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("isFirstRun", true)) {
            Log.e("FIRST_RUN", "First timeee <3");
            //PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("URL", "UrlWasNotSet");
            showAlert("");
            settings.edit().putBoolean("isFirstRun", false).commit();
        }




        final Session session = Session.getInstance();
        final DataDownloader dd = new DataDownloader(getBaseContext());
        dd.getCalendarFromUrl();

        if (session.getOutlookCalendar() == null) {
            setFree();
        }
        int initialDelay = 1000;
        final CurrentMeetingHandler handler = new CurrentMeetingHandler();

        int period = 5000; // 5 sec
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                handler.handleMessage(null);
                final Runnable myRunnable = new Runnable() {
                    public void run() {
                        GregorianCalendar now = handler.getNow();
                        dd.getCalendarFromUrl();
                        if (session.getOutlookCalendar() != null) {
                            currentMeeting = session.getOutlookCalendar().getCurrentMeeting();
                            String cur = (currentMeeting == null) ? "null" : currentMeeting.toString();

                            if (currentMeeting == null) {
                                setFree();
                            } else {
                                setBooked();
                            }
                        }
                    }
                };
                handler.post(myRunnable);
            }
        };
        timer.scheduleAtFixedRate(task, initialDelay, period);

    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBooked() {
        mainLayout.setBackground(getResources().getDrawable(R.drawable.unavailable));
        meetingFragment.fillMeeting();
        meetingFragment.getView().setVisibility(View.VISIBLE);
        roomStatus.setBackgroundColor(getResources().getColor(R.color.cnRed));
        roomStatus.setText(getResources().getString(R.string.meeting_room_status_booked));
        upcomingMeetingsFragment.init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setFree() {
        mainLayout.setBackground(getResources().getDrawable(R.drawable.available));
        meetingFragment.getView().setVisibility(View.GONE);
        roomStatus.setBackgroundColor(getResources().getColor(R.color.cnLightBlue));
        roomStatus.setText(getResources().getString(R.string.meeting_room_status_free));
        upcomingMeetingsFragment.init();
    }

    private void saveUrl(String url){
        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("URL", url).commit();
    }

    private void showAlert(String inputStr){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set calendar's URL ");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(inputStr);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveUrl(input.getText().toString().trim());
            }
        });

        builder.show();
    }

    public void changeSettings(View view){
        String defaultStr = "UrlWasNotSet";
        String savedUrl = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("URL",defaultStr);
        showAlert(savedUrl);
    }

private  class CurrentMeetingHandler extends android.os.Handler {
    private GregorianCalendar now = new GregorianCalendar();

    @Override
    public void handleMessage(Message msg) {
        this.now = new GregorianCalendar();
    }

    public GregorianCalendar getNow() {
        return now;
    }
}


}
