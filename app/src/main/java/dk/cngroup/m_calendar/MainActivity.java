package dk.cngroup.m_calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @ViewById(R.id.organizedBy)
    TextView organizedBy;

    @ViewById(R.id.status)
    TextView status;

    @AfterViews
    public void init(){
        String organizator = "Abc";
        String text = getBaseContext().getResources().getString(R.string.organizedBy, organizator);
        organizedBy.setText(text);

    }



}
