package dk.cngroup.m_calendar;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.Person;

public final class SessionAllOrganizersInfo {
    private int totalCountOfMeetings = 0;
    private ArrayList<Meeting> newMeetings = new ArrayList<Meeting>();


    private Person organizer;

    public Person getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Person organizer) {
        this.organizer = organizer;
    }

    public void setNewMeetings(ArrayList<Meeting> newMeetings) {
        this.newMeetings = newMeetings;
    }

    private static final SessionAllOrganizersInfo INSTANCE = new SessionAllOrganizersInfo();

    public static SessionAllOrganizersInfo getInstance() {
        return INSTANCE;
    }

    public synchronized void addNewMeeting(Meeting meeting) {
        if (newMeetings.size() < totalCountOfMeetings) {
            newMeetings.add(meeting);
        }
    }

    public void setTotalCountOfMeetings(int totalCountOfMeetings) {
        this.totalCountOfMeetings = totalCountOfMeetings;
    }

    public synchronized ArrayList<Meeting> getNewMeetings() {
        Collections.sort(newMeetings);
        return newMeetings;
    }

    public boolean equallyLarge() {
        Log.e("RUN", "--------------------------");
        Log.e("RUN", "new Meetings " + newMeetings.toString());
        Log.e("RUN", "total count " + totalCountOfMeetings);
        Log.e("RUN", "--------------------------");

        return totalCountOfMeetings == newMeetings.size();
    }
}
