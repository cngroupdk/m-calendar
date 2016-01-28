package dk.cngroup.m_calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
import java.util.Date;
import java.util.Objects;

import static com.independentsoft.exchange.AppointmentPropertyPath.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    boolean isBooked = true;

    @ViewById(R.id.mainLayout)
    LinearLayout mainLayout;

    @ViewById(R.id.roomStatus)
    TextView roomStatus;

    @FragmentById(R.id.fragment2)
    MeetingFragment meetingFragment;

    @Bean
    CalendarHelper calendarHelper;

    public static ArrayList<Meeting> todayMeetings = new ArrayList<Meeting>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void fillMeeting() {




        if (isBooked) {
            mainLayout.setBackground(getResources().getDrawable(R.drawable.unavailable));
            meetingFragment.getView().setVisibility(View.VISIBLE);
            roomStatus.setBackgroundColor(getResources().getColor(R.color.cnRed));
            roomStatus.setText(getResources().getString(R.string.meeting_room_status_booked));

        } else {
            mainLayout.setBackground(getResources().getDrawable(R.drawable.available));
            meetingFragment.getView().setVisibility(View.GONE);
            roomStatus.setBackgroundColor(getResources().getColor(R.color.cnLightBlue));
            roomStatus.setText(getResources().getString(R.string.meeting_room_status_free));

        }
        //calendarHelper.init();
        //CalendarReader2.readCalendar(this.getBaseContext());
        //CalendarReader2.getEventsOfToday(getContentResolver(), null);

    }

    public static ArrayList<Meeting> getTodayMeetings(Context context){
        // readFile method - call just once
        DataParser.readFile(context, todayMeetings);
        return todayMeetings;
    }




}