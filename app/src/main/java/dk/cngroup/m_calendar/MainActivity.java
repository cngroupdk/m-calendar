package dk.cngroup.m_calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.independentsoft.exchange.And;
import com.independentsoft.exchange.Appointment;
import com.independentsoft.exchange.AppointmentPropertyPath;
import com.independentsoft.exchange.FindItemResponse;
import com.independentsoft.exchange.IsGreaterThanOrEqualTo;
import com.independentsoft.exchange.IsLessThanOrEqualTo;
import com.independentsoft.exchange.Service;
import com.independentsoft.exchange.ServiceException;
import com.independentsoft.exchange.StandardFolder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.independentsoft.exchange.AppointmentPropertyPath.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    public static boolean isBooked = true;
    public final String DEBUG_LIFE = "LIFE";

    @ViewById(R.id.mainLayout)
    LinearLayout mainLayout;

    @ViewById(R.id.roomStatus)
    TextView roomStatus;

    @FragmentById(R.id.fragment2)
    MeetingFragment meetingFragment;

    @FragmentById(R.id.fragment3)
    UpcomingMeetingsFragment upcomingMeetingsFragment;

    @Bean
    CalendarHelper calendarHelper;

    public static ArrayList<Meeting> todayMeetings = new ArrayList<Meeting>();
    public static Meeting currentMeeting = null;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void fillMeeting() {

        if (todayMeetings.size() == 0 && currentMeeting == null){
            setFree();
        }
        else {
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
                            String cur =  (currentMeeting == null ) ? "null" : currentMeeting.toString();
                            Log.e("C. STATE","CURRENT " + cur);



                            if (todayMeetings.size() == 0 /*&& currentMeeting == null*/){
                                Log.e("LIFE ", "NULA todaysMeetings");
                                if (!isMeetingTakingPlace(currentMeeting,now) || currentMeeting == null) {
                                    setFree();
                                    currentMeeting = null;
                                }
                                else {
                                    if (isMeetingTakingPlace(currentMeeting, now)){
                                        setBooked();
                                    }
                                }
                            }

                             else {
                                Meeting firstMeeting = todayMeetings.get(0);
                                Log.e("C. STATE","FIRST " + firstMeeting.toString());

                                if (isMeetingTakingPlace(firstMeeting, now) && currentMeeting == null){
                                    currentMeeting = firstMeeting;
                                    setBooked();

                                }
                                if (currentMeeting != null && !isMeetingTakingPlace(currentMeeting, now)){
                                    currentMeeting = null;
                                    Log.e("LIFE ","set free - current not taking place anymore ");
                                }


                                if (currentMeeting == null) {
                                    Log.e("LIFE ","set free - bcs current Meeting is null");
                                    setFree();
                                }
                            }
                        }

                    };
                    handler.post(myRunnable);
                }
            };
            timer.scheduleAtFixedRate(task, initialDelay, period);

        }


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setBooked(){
        Log.e("LIFE ","setBooked");
        isBooked = true;
        mainLayout.setBackground(getResources().getDrawable(R.drawable.unavailable));
        meetingFragment.fillMeeting();
        meetingFragment.getView().setVisibility(View.VISIBLE);
        roomStatus.setBackgroundColor(getResources().getColor(R.color.cnRed));
        roomStatus.setText(getResources().getString(R.string.meeting_room_status_booked));
        upcomingMeetingsFragment.init();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setFree(){
        Log.e("LIFE ","setFree");
        isBooked = false;
        mainLayout.setBackground(getResources().getDrawable(R.drawable.available));
        meetingFragment.getView().setVisibility(View.GONE);
        roomStatus.setBackgroundColor(getResources().getColor(R.color.cnLightBlue));
        roomStatus.setText(getResources().getString(R.string.meeting_room_status_free));
        upcomingMeetingsFragment.init();
    }

    public static ArrayList<Meeting> getTodayMeetings(Context context){
        todayMeetings = new ArrayList<Meeting>();
        Log.e("LIFE ", "today m " + todayMeetings.toString());
        DataParser.readFile(context, todayMeetings);
        Collections.sort(todayMeetings);
        return todayMeetings;
    }

    public static boolean isBooked(){
        return isBooked;
    }

    public static void removeFirstMeetingFromList(){
        if (todayMeetings.size() != 0) {
            todayMeetings.remove(0);
        }
    }

    private boolean isMeetingTakingPlace(Meeting meeting, GregorianCalendar now){
        return meeting.getFromTime().compareTo(now) <= 0 && meeting.getToTime().compareTo(now) > 0;
    }

    public static ArrayList<Meeting> getCurrentTodayMeetings(){
        return todayMeetings;
    }

    public static Meeting getCurrentMeeting(){
        return currentMeeting;
    }


    private static class CurrentMeetingHandler extends android.os.Handler {
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
