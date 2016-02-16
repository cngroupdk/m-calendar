package dk.cngroup.m_calendar;

import java.util.ArrayList;

public final class Session {

    private MeetingRoomStatus meetingRoomStatus = MeetingRoomStatus.BOOKED;
    private boolean currentMeetingUntimelyFinished = false;
    private Meeting untimelyFinishedMeeting = null;

    public boolean isCurrentMeetingUntimelyFinished() {
        return currentMeetingUntimelyFinished;
    }

    public Meeting getUntimelyFinishedMeeting() {
        return untimelyFinishedMeeting;
    }

    public void setUntimelyFinishedMeeting(Meeting untimelyFinishedMeeting) {
        this.untimelyFinishedMeeting = untimelyFinishedMeeting;
    }

    public void setCurrentMeetingUntimelyFinished(boolean currentMeetingUntimelyFinished) {
        this.currentMeetingUntimelyFinished = currentMeetingUntimelyFinished;
    }

    private static final Session INSTANCE = new Session();
    private OutlookCalendar outlookCalendar;

    private Session() {
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