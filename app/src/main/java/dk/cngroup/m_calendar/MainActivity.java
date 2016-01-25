package dk.cngroup.m_calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
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
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.independentsoft.exchange.AppointmentPropertyPath.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    boolean isBooked = false;

    @ViewById(R.id.mainLayout)
    LinearLayout mainLayout;

    /*@Click (R.id.button)
    void onclick(){
        Intent i = new Intent(this, RssfeedActivity_.class);
        startActivity(i);
    }*/

    @Bean
    CalendarHelper calendarHelper;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void fillMeeting() {
        if (isBooked) {
            mainLayout.setBackground(getResources().getDrawable(R.drawable.unavailable));
        } else {
            mainLayout.setBackground(getResources().getDrawable(R.drawable.available));
        }
        calendarHelper.init();
        //CalendarReader2.readCalendar(this.getBaseContext());

        //CalendarReader2.getEventsOfToday(getContentResolver(), null);

/*        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials("bachronikova@cngroup.dk", "betkabachi");
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI("https://emil.cngroup.dk/owa"));
            RuleCollection rc = service.getInboxRules();
            Toast.makeText(this, Integer.toString(rc.getCount()), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
 }*/


        /*try
        {
            final Service service = new Service("https://emil.cngroup.dk/owa/", "bachronikova@cngroup.dk", "betkabachi");
            final Context clazz = this;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = dateFormat.parse("2015-12-01 00:00:00");
            Date endTime = dateFormat.parse("2015-12-20 00:00:00");

            IsGreaterThanOrEqualTo restriction1 = new IsGreaterThanOrEqualTo(START_TIME, startTime);
            IsLessThanOrEqualTo restriction2 = new IsLessThanOrEqualTo(END_TIME, endTime);
            final And restriction3 = new And(restriction1, restriction2);

            AsyncTask<Object, Void, FindItemResponse> t = new AsyncTask<Object, Void, FindItemResponse>() {
                @Override
                protected FindItemResponse doInBackground(Object... params) {
                    try {
                        Log.e("SERVICE", "do in background");
                        return service.findItem(StandardFolder.CALENDAR, getAllPropertyPaths(), restriction3);
                    } catch (ServiceException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                protected void onPostExecute(FindItemResponse response) {
                    Toast.makeText(clazz, Integer.toString(response.getItems().size()), Toast.LENGTH_SHORT).show();
                    Log.e("SERVICE","post execute ");
                }
            };

            t.execute();

//            for (int i = 0; i < response.getItems().size(); i++)
//            {
//                if (response.getItems().get(i) instanceof Appointment)
//                {
//                    Appointment appointment = (Appointment) response.getItems().get(i);
//
//                    String str = "Subject = " + appointment.getSubject()
//                    + "\nStartTime = " + appointment.getStartTime()
//                    + "\nEndTime = " + appointment.getEndTime()
//                    + "\nBody Preview = " + appointment.getBodyPlainText();
//
//                    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
//                }
//            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/




    }
}