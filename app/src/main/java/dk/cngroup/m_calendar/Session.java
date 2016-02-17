package dk.cngroup.m_calendar;

import java.util.ArrayList;

public final class Session {
    private static final Session INSTANCE = new Session();
    private OutlookCalendar outlookCalendar;

    private MeetingRoomStatus meetingRoomStatus = MeetingRoomStatus.BOOKED;

    private CurrentState currentState = null;

    private Session() {
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }

    public static Session getInstance() {
        return INSTANCE;
    }

    public synchronized OutlookCalendar getOutlookCalendar() {
        return outlookCalendar;
    }

    public synchronized void setOutlookCalendar(ArrayList<Meeting> meetings) {
        outlookCalendar = new OutlookCalendar(meetings);
    }

    public MeetingRoomStatus getMeetingRoomStatus(){
        return meetingRoomStatus;
    }

    public void setMeetingRoomStatus(MeetingRoomStatus meetingRoomStatus) {
        this.meetingRoomStatus = meetingRoomStatus;
    }
}