package dk.cngroup.m_calendar;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.Person;

public class DataParser {

    private final String BEGIN_EVENT = "BEGIN:VEVENT";
    private final String END_EVENT = "END:VEVENT";
    private final String SUMMARY = "SUMMARY";
    private final String ORGANIZER = "ORGANIZER";
    private final String BEGIN_MEETING = "DTSTART";
    private final String END_MEETING = "DTEND";
    private final String ATTENDEE = "ATTENDEE";

    public ArrayList<Meeting> getMeetingsFromString(String stringCalendar) {

        ArrayList<Meeting> meetings = new ArrayList<Meeting>();
        GregorianCalendar now = new GregorianCalendar();
        GregorianCalendar todayEvening = new GregorianCalendar();
        todayEvening.set(Calendar.HOUR,23);
        todayEvening.set(Calendar.MINUTE,59);

        InputStream is = new ByteArrayInputStream(stringCalendar.getBytes());
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String strLine = br.readLine();
            while (strLine != null) {
                strLine = br.readLine();
                if (strLine.trim().equals(BEGIN_EVENT)) {
                    Meeting meeting = new Meeting();
                    while (!strLine.trim().equals(END_EVENT)) {
                        if (strLine.startsWith(SUMMARY)) {
                            meeting.setName(getMeetingName(strLine));
                        } else if (strLine.startsWith(BEGIN_MEETING)) {
                            meeting.setBeginTime(getCalendar(strLine));
                        } else if (strLine.startsWith(END_MEETING)) {
                            meeting.setEndTime(getCalendar(strLine));
                        }
                        Log.e("SERVICE",strLine);
                        /* else if (strLine.startsWith(ORGANIZER)) {
                            meeting.setOrganizator(new Person(getNameFromString(strLine)));
                        } /*else if (strLine.startsWith(ATTENDEE)) {
                            meeting.addParticipant(new Person(getNameFromString(strLine)));
                        }*/

                        strLine = br.readLine();
                    }
                    if (meeting.getEndTime().compareTo(now)  > 0 && todayEvening.compareTo(meeting.getBeginTime())  > 0) {
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

//    public ArrayList<Meeting> getMeetingsFromFile(Context context) {
//        ArrayList<Meeting> meetings = new ArrayList<Meeting>();
//        InputStream is;
//        GregorianCalendar now = new GregorianCalendar();
//        GregorianCalendar todayEvening = new GregorianCalendar();
//        todayEvening.set(Calendar.HOUR,23);
//        todayEvening.set(Calendar.MINUTE,59);
//        try {
//            is = context.getResources().openRawResource(R.raw.calendar2);
//            BufferedReader dataIO = new BufferedReader(new InputStreamReader(is));
//            String strLine = dataIO.readLine();
//            while (strLine != null) {
//                Log.e("SERVICE", strLine.toString());
//                strLine = dataIO.readLine();
//                if (strLine.trim().equals(BEGIN_EVENT)) {
//                    Meeting meeting = new Meeting();
//                    while (!strLine.trim().equals(END_EVENT)) {
//                        if (strLine.startsWith(ORGANIZER)) {
//                            meeting.setOrganizator(new Person(getNameFromString(strLine)));
//                        }
//                        if (strLine.startsWith(ATTENDEE)) {
//                            meeting.addParticipant(new Person(getNameFromString(strLine)));
//                        } else if (strLine.startsWith(SUMMARY)) {
//                            meeting.setName(getMeetingName(strLine));
//                        } else if (strLine.startsWith(BEGIN_MEETING)) {
//                            meeting.setBeginTime(getCalendar(strLine));
//                        } else if (strLine.startsWith(END_MEETING)) {
//                            meeting.setEndTime(getCalendar(strLine));
//                        }
//                        strLine = dataIO.readLine();
//                    }
//                    if (meeting.getEndTime().compareTo(now) > 0 && todayEvening.compareTo(meeting.getBeginTime())  > 0) {
//                        meetings.add(meeting);
//                    }
//                }
//            }
//
//            dataIO.close();
//            is.close();
//        } catch (Exception e) {
//        }
//        return meetings;
//    }

    private String getNameFromString(String str) {
        String[] temp = str.split("\"");
        return temp[1];
    }

    private String getMeetingName(String str) {
        String[] temp = str.split(":");
        return temp[1];
    }

    private String getTime(String str) {
        String[] temp = str.split(":");
        return temp[1].trim();
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
