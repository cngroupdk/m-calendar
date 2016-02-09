package dk.cngroup.m_calendar;

import java.util.ArrayList;

public final class Session {

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


}