package dk.cngroup.m_calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

public class OutlookCalendar {
    private ArrayList<Meeting> meetings = new ArrayList<Meeting>();


    public OutlookCalendar(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
    }

    public ArrayList<Meeting> getMeetings() {
        Collections.sort(this.meetings);
        return meetings;
    }

    public void setMeetings(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Meeting getCurrentMeeting() {
        GregorianCalendar now = new GregorianCalendar();
        for (Meeting meeting : this.meetings) {
            if (isMeetingTakingPlace(meeting, now)) {
                return meeting;
            }
        }
        return null;
    }

    public boolean isMeetingTakingPlace(Meeting meeting, GregorianCalendar now) {
        return meeting.getBeginTime().compareTo(now) <= 0 && meeting.getEndTime().compareTo(now) > 0;
    }

    public boolean isUpcomingMeeting(Meeting meeting, GregorianCalendar now) {
        return meeting.getBeginTime().compareTo(now) > 0;
    }

    public ArrayList<Meeting> getUpcomingMeetings() {
        GregorianCalendar now = new GregorianCalendar();
        ArrayList<Meeting> upcomingMeetings = new ArrayList<Meeting>();
        for (Meeting meeting : this.meetings) {
            if (isUpcomingMeeting(meeting, now)) {
                upcomingMeetings.add(meeting);
            }
        }
        Collections.sort(upcomingMeetings);
        return upcomingMeetings;
    }

}
