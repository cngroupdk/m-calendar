package dk.cngroup.m_calendar;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataParser {
    public DataParser(){}

    public static void readFile(Context context, ArrayList<Meeting> meetings){
        InputStream is;
        String organizer, meetingTimeStartStr, meetingTimeEndStr, meetingNameStr;


        try {
            is  = context.getResources().openRawResource(R.raw.calendar20);
            BufferedReader dataIO = new BufferedReader(new InputStreamReader(is));
            String strLine = dataIO.readLine();
            while (strLine != null) {
                strLine = dataIO.readLine();
                if (strLine.trim().equals("BEGIN:VEVENT")){
                    Meeting meeting = new Meeting();
                    Log.e("FOUND", "BEGIN:VEVENT START");
                    while (!strLine.trim().equals("END:VEVENT")){
                        if (strLine.startsWith("ORGANIZER")){
                            organizer = getNameFromString(strLine);
                            meeting.setOrganizator(organizer);
                        }

                        else if (strLine.startsWith("ATTENDEE")){
                            meeting.addParticipant(getNameFromString(strLine));
                        }

                        else if (strLine.startsWith("SUMMARY;LANGUAGE=cs:")) {
                            meetingNameStr = strLine.substring("SUMMARY;LANGUAGE=cs:".length(),strLine.length());
                            meeting.setName(meetingNameStr);
                        }

                        else if (strLine.startsWith("DTSTART")) {
                           meeting.setFromTime(getCalendar(strLine));
                        }

                        else if (strLine.startsWith("DTEND")) {
                            meeting.setToTime(getCalendar(strLine));
                        }



                        strLine = dataIO.readLine();
                    }

                    //meeting.setFromTime(new GregorianCalendar());
                    //meeting.setToTime(new GregorianCalendar());
                    meetings.add(meeting);
                }
            }

            dataIO.close();
            is.close();
        }
        catch  (Exception e) {
        }

    }

    public static ArrayList<Meeting> getTodayMeetings(Context context){
        ArrayList<Meeting> meetings = new ArrayList<Meeting>();
        readFile(context, meetings);
        Log.e("SIZE ",meetings.size()+"");
        return meetings;

}

    private static String getNameFromString(String str){
        String[] temp = str.split("\"");
        return temp[1];

    }

    private static  String getTime(String str){
        String[] temp = str.split("\"");
        return temp[2].substring(1);
    }

    private static GregorianCalendar getCalendar(String str){
        String time = getTime(str).trim();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(
                Integer.parseInt(time.substring(0, 4)),
                Integer.parseInt(time.substring(4, 6)),
                Integer.parseInt(time.substring(6, 8)),
                Integer.parseInt(time.substring(9, 11)),
                Integer.parseInt(time.substring(11, 13)),
                Integer.parseInt(time.substring(13))
        );

        Log.e("HOUR ", "" + gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY));
        return  gregorianCalendar;

    }
}
