package dk.cngroup.m_calendar;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.upcoming_meeting)
public class UpcomingMeeting extends LinearLayout{
    @ViewById(R.id.meetingTime)
    TextView meetingTime;

    @ViewById(R.id.nameOfMeeting)
    TextView meetingName;

    @ViewById(R.id.organizator)
    TextView organizator;

    public UpcomingMeeting(Context context) {
        super(context);
    }

    public void initUpcomingMeeting(Meeting meeting){
        organizator.setText(meeting.getOrganizator().substring(0, 3));
        meetingName.setText(meeting.getName());
        meetingTime.setText(meeting.getMeetingTime());
    }


}
