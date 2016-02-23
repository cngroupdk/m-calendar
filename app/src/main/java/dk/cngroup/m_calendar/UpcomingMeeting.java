package dk.cngroup.m_calendar;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import dk.cngroup.m_calendar.entity.Meeting;

@EViewGroup(R.layout.upcoming_meeting)
public class UpcomingMeeting extends LinearLayout{
    @ViewById(R.id.meetingTime)
    TextView meetingTime;

    @ViewById(R.id.nameOfMeeting)
    TextView meetingName;

    @ViewById(R.id.organizer)
    TextView organizator;

    public UpcomingMeeting(Context context) {
        super(context);
    }

    public void initUpcomingMeeting(Meeting meeting){
        if  (meeting.getOrganizator() != null){
            if (meeting.getOrganizator().getAbbreviation().length() < 0) {
                organizator.setText(meeting.getOrganizator().getAbbreviation());
            }
        }
        meetingName.setText(meeting.getName());
        meetingTime.setText(meeting.getMeetingTime());
    }


}
