package dk.cngroup.m_calendar;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Date;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
     boolean isBooked = true;

    @ViewById (R.id.mainLayout)
    LinearLayout mainLayout;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @AfterViews
    public void fillMeeting(){
        if (isBooked){
            mainLayout.setBackground(getResources().getDrawable(R.drawable.unavailable));
        }
        else {
            mainLayout.setBackground(getResources().getDrawable(R.drawable.available));
        }


    }

    @Bean
    CalendarHelper calendarHelper;






}
