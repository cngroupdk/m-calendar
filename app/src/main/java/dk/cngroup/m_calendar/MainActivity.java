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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;


import dk.cngroup.m_calendar.entity.CurrentState;
import dk.cngroup.m_calendar.entity.Meeting;

import static dk.cngroup.m_calendar.MeetingRoomStatus.BOOKED;
import static dk.cngroup.m_calendar.MeetingRoomStatus.FREE;
import static dk.cngroup.m_calendar.MeetingRoomStatus.OCCUPIED;

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
    private static final int PERIOD = 60000;
    private static final int INITIAL_DELAY = 1000;
    public static final String URL_KEY = "URL";


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void fillMeeting() {


        final String PREFS_NAME = "MyPrefsFile";
        final String IS_FIRST_RUN_KEY = "isFirstRun";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean(IS_FIRST_RUN_KEY, true)) {
            showAlert("");
            settings.edit().putBoolean(IS_FIRST_RUN_KEY, false).apply();

        }


        final Session session = Session.getInstance();
        final SessionAllOrganizersInfo sessionAllOrganizersInfo = SessionAllOrganizersInfo.getInstance();
        final DataDownloader dataDownloader = new DataDownloader(getBaseContext());
        final PersonInfoDownloader personInfoDownloader = new PersonInfoDownloader();
        dataDownloader.getCalendarFromUrl();

        if (session.getOutlookCalendar() == null) {
            setFree();
        }

        final CurrentMeetingHandler handler = new CurrentMeetingHandler();


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                handler.handleMessage(null);
                final Runnable myRunnable = new Runnable() {
                    public void run() {
                        dataDownloader.getCalendarFromUrl();
                        personInfoDownloader.getOrganizersInfo();

//                        if (session.getOutlookCalendar() != null) {
//                            sessionAllOrganizersInfo.setTotalCountOfMeetings(session.getOutlookCalendar().getAllMeetings().size());
//                            if (sessionAllOrganizersInfo.equallyLarge()) {
//                                Log.e("RUN", "setting new meetings bcs equally large");
//                                session.setOutlookCalendar(sessionAllOrganizersInfo.getNewMeetings());
//                                sessionAllOrganizersInfo.setNewMeetings(new ArrayList<Meeting>());
                        if (session.getOutlookCalendar() != null) {

                            currentMeeting = session.getOutlookCalendar().getCurrentMeeting();
                            CurrentState cs = session.getCurrentState();

                            if (cs != null && currentMeeting != null) {
                                if (cs.getCurrentMeeting() != null) {
                                    if (currentMeeting.equals(cs.getCurrentMeeting())) {
                                        Log.e("CURRENT_STATE", "meetings are same");
                                        if (cs.isStarted()) {
                                            Log.e("CURRENT_STATE", "meeting is started");
                                            session.setMeetingRoomStatus(OCCUPIED);
                                            changeLayout(session);

                                        } else if (cs.isFinished()) {
                                            Log.e("CURRENT_STATE", "meeting is finished");
                                            session.setMeetingRoomStatus(FREE);
                                            changeLayout(session);

                                        }
                                    } else {
                                        Log.e("CURRENT_STATE", "meetings are NOT same");
                                        session.setCurrentState(null);
                                    }
                                }
                            } else if (currentMeeting != null) {
                                session.setMeetingRoomStatus(BOOKED);
                                changeLayout(session);

                            } else {
                                session.setMeetingRoomStatus(FREE);
                                changeLayout(session);
                            }
                        }

                    }
                };
                handler.post(myRunnable);
            }

        };
        timer.scheduleAtFixedRate(task, INITIAL_DELAY, PERIOD);

    }


    private void changeLayout(Session session) {
        MeetingRoomStatus meetingRoomStatus = session.getMeetingRoomStatus();
        Log.e("C. STATE", "STATUS " + meetingRoomStatus.toString());
        switch (meetingRoomStatus) {
            case BOOKED:
                setBooked();
                break;
            case FREE:
                setFree();
                break;
            case OCCUPIED:
                setOccupied();
                break;

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBookedLayout() {
        mainLayout.setBackground(getResources().getDrawable(R.drawable.unavailable));
        meetingFragment.fillMeeting();
        if (meetingFragment.getView() != null) {
            meetingFragment.getView().setVisibility(View.VISIBLE);
        }
        roomStatus.setBackgroundColor(getResources().getColor(R.color.cnRed));
        upcomingMeetingsFragment.init();
    }

    private void setBooked() {
        roomStatus.setText(getResources().getString(R.string.meeting_room_status_booked));
        setBookedLayout();
    }

    private void setOccupied() {
        roomStatus.setText(getResources().getString(R.string.meeting_room_status_occupied));
        setBookedLayout();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setFree() {
        mainLayout.setBackground(getResources().getDrawable(R.drawable.available));
        if (meetingFragment.getView() != null) {
            meetingFragment.getView().setVisibility(View.GONE);
        }
        roomStatus.setBackgroundColor(getResources().getColor(R.color.cnLightBlue));
        roomStatus.setText(getResources().getString(R.string.meeting_room_status_free));
        upcomingMeetingsFragment.init();
    }

    private void saveUrl(String url) {
        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString(URL_KEY, url).commit();
    }

    private void showAlert(String inputStr) {
        String title = getResources().getString(R.string.url_dialog_title);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(inputStr);
        builder.setView(input);

        builder.setPositiveButton(getString(R.string.okButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveUrl(input.getText().toString().trim());
            }
        });

        builder.show();
    }

    public void changeSettings(View view) {
        String defaultStr = getResources().getString(R.string.url_dialog_default_url);
        String savedUrl = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString(URL_KEY, defaultStr);
        showAlert(savedUrl);
    }

    private class CurrentMeetingHandler extends android.os.Handler {
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
