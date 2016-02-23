package dk.cngroup.m_calendar;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@EFragment(R.layout.fragment_time_date)
public class TimeDateFragment extends Fragment {

    @ViewById(R.id.dateTextView)
    TextView dateTv;
    @ViewById(R.id.timeTextView)
    TextView timeTv;

    private MyHandler myHandler = new MyHandler();

    @AfterViews
    public void on(){
        initTime();
    }

    private void initTime(){
        int initialDelay = 1000;
        int period = 1000;
        Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            public void run() {
                myHandler.handleMessage(null);
                final Runnable myRunnable = new Runnable() {
                    public void run() {
                        dateTv.setText(myHandler.getDate());
                        timeTv.setText(myHandler.getTime());
                    }
                };
                myHandler.post(myRunnable);
            }
        };
        timer.scheduleAtFixedRate(task, initialDelay, period);

    }

    private static class MyHandler extends Handler {
        private String date;
        private String time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MM yyyy\r");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        @Override
        public void handleMessage(Message msg) {
            long currentMillis = System.currentTimeMillis();
            Date resultDate = new Date(currentMillis);
            date = dateFormat.format(resultDate);
            time = timeFormat.format(resultDate);
        }

        public String getTime() {
            return time;
        }

        public String getDate() {
            return date;
        }
    }

}