package dk.cngroup.m_calendar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DataParser {
    public DataParser(){}

    private static final String BEGIN_EVENT = "BEGIN:VEVENT";
    private static final String END_EVENT = "END:VEVENT";
    private static final String SUMMARY = "SUMMARY";
    private static final String ORGANIZER = "ORGANIZER";
    private static final String BEGIN_MEETING = "DTSTART";
    private static final String END_MEETING= "DTEND";
    private static final String ATTENDEE = "ATTENDEE";

    private static String rssResult = "";

    private static URL rssUrl;



    public static void readFile(Context context, ArrayList<Meeting> meetings){
        InputStream is;
        GregorianCalendar now = new GregorianCalendar();

        try {
            is  = context.getResources().openRawResource(R.raw.calendar1);
            BufferedReader dataIO = new BufferedReader(new InputStreamReader(is));
            String strLine = dataIO.readLine();
            while (strLine != null) {
                strLine = dataIO.readLine();
                if (strLine.trim().equals(BEGIN_EVENT)){
                    Meeting meeting = new Meeting();
                    while (!strLine.trim().equals(END_EVENT)){
                        if (strLine.startsWith(ORGANIZER)){
                            meeting.setOrganizator(getNameFromString(strLine));
                        }

                        else if (strLine.startsWith(ATTENDEE)){
                            meeting.addParticipant(getNameFromString(strLine));
                        }

                        else if (strLine.startsWith(SUMMARY)) {
                            meeting.setName(getMeetingName(strLine));
                        }

                        else if (strLine.startsWith(BEGIN_MEETING)) {
                           meeting.setFromTime(getCalendar(strLine));
                        }

                        else if (strLine.startsWith(END_MEETING)) {
                            meeting.setToTime(getCalendar(strLine));
                        }

                        strLine = dataIO.readLine();
                    }
                    if (meeting.getToTime().compareTo(now) > 0) {
                        meetings.add(meeting);
                    }
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
        return meetings;

}

    private static String getNameFromString(String str){
        String[] temp = str.split("\"");
        return temp[1];

    }

    private static String getMeetingName(String str){
        String[] temp = str.split(":");
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
                Integer.parseInt(time.substring(0, 4)), // year
                Integer.parseInt(time.substring(4, 6))-1, // month gregorian calendars months 0 - 11, outlook 1 - 12
                Integer.parseInt(time.substring(6, 8)), // day
                Integer.parseInt(time.substring(9, 11)), // hour
                Integer.parseInt(time.substring(11, 13)), //minutes
                Integer.parseInt(time.substring(13)) // secs
        );
        return  gregorianCalendar;

    }

    private static String getCalendarFromUrl(){
            Log.e("SERVICE", "init");

            AsyncTask<Object, Void, String> t = new AsyncTask<Object, Void, String>() {
                @Override
                protected String doInBackground(Object... params) {
                    try {
                        Log.e("SERVICE", "do in Background");
                        rssUrl = new URL("http://davinci.fmph.uniba.sk/~bachronikov2/testing/calendar1.ics");
                        StringBuilder b = new StringBuilder();
                        BufferedReader r = new BufferedReader(new InputStreamReader(rssUrl.openStream()));

                        try {
                            for (String line; (line = r.readLine()) != null; ) {
                                b.append(line).append("\n");
                            }
                        } finally {
                            r.close();
                        }
                        rssResult = b.toString();
                        return rssResult;

                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        return e.getMessage();
                    }
                }

                @Override
                protected void onPostExecute(String mess) {
                    Log.e("SERVICE", "post execute ");
                    //rss.setText(mess);
                }
            };

            t.execute();
        return rssResult;

    }

}
