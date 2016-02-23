package dk.cngroup.m_calendar;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.Person;

public class DataParser {
    public DataParser() {
    }

    private static final String BEGIN_EVENT = "BEGIN:VEVENT";
    private static final String END_EVENT = "END:VEVENT";
    private static final String SUMMARY = "SUMMARY";
    private static final String ORGANIZER = "ORGANIZER";
    private static final String BEGIN_MEETING = "DTSTART";
    private static final String END_MEETING = "DTEND";
    private static final  String ATTENDEE = "ATTENDEE";

    public ArrayList<Meeting> getMeetingsFromString(String stringCalendar) {
        ArrayList<Meeting> meetings = new ArrayList<Meeting>();
        GregorianCalendar now = new GregorianCalendar();
        InputStream is = new ByteArrayInputStream(stringCalendar.getBytes());
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String strLine = br.readLine();
            while (strLine != null) {
                strLine = br.readLine();
                if (strLine.trim().equals(BEGIN_EVENT)) {
                    Meeting meeting = new Meeting();
                    while (!strLine.trim().equals(END_EVENT)) {
                        if (strLine.startsWith(ORGANIZER)) {
                            meeting.setOrganizator(new Person(getNameFromString(strLine)));
                        } else if (strLine.startsWith(ATTENDEE)) {
                            meeting.addParticipant(new Person(getNameFromString(strLine)));
                        } else if (strLine.startsWith(SUMMARY)) {
                            meeting.setName(getMeetingName(strLine));
                        } else if (strLine.startsWith(BEGIN_MEETING)) {
                            meeting.setBeginTime(getCalendar(strLine));
                        } else if (strLine.startsWith(END_MEETING)) {
                            meeting.setEndTime(getCalendar(strLine));
                        }

                        strLine = br.readLine();
                    }
                    if (meeting.getEndTime().compareTo(now) > 0) {
                        meetings.add(meeting);
                    }
                }
            }

            br.close();
            is.close();
        } catch (Exception e) {
        }
        return meetings;
    }

    private String getNameFromString(String str) {
        String[] temp = str.split("\"");
        return temp[1];

    }

    private String getMeetingName(String str) {
        String[] temp = str.split(":");
        return temp[1];
    }

    private String getTime(String str) {
        String[] temp = str.split("\"");
        return temp[2].substring(1);
    }

    private GregorianCalendar getCalendar(String str) {
        String time = getTime(str).trim();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(
                Integer.parseInt(time.substring(0, 4)), // year
                Integer.parseInt(time.substring(4, 6)) - 1, // month gregorian calendars months 0 - 11, outlook 1 - 12
                Integer.parseInt(time.substring(6, 8)), // day
                Integer.parseInt(time.substring(9, 11)), // hour
                Integer.parseInt(time.substring(11, 13)), //minutes
                Integer.parseInt(time.substring(13)) // secs
        );
        return gregorianCalendar;
    }


}
