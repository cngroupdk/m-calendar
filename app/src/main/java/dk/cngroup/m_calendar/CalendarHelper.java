package dk.cngroup.m_calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EBean
public class CalendarHelper{
    @RootContext
    Context mContext;

    public static String[] FIELDS = { CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars._ID
    };
    public static Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/calendars");


    public static ContentResolver contentResolver;
    public static Set<String> calendars = new HashSet<String>();
    public static Set<String> calendarsIDs = new HashSet<String>();

    @AfterInject
    public void init() {
        contentResolver = mContext.getContentResolver();
        //Log.e("CALENDAR",getCalendarsIDs().toString());
        //getCalendarEvent();
        //CalendarReader2.getEventsOfToday(contentResolver,CALENDAR_URI);
    }

    public static Set<String> getCalendarsIDs() {
        Cursor cursor = contentResolver.query(CALENDAR_URI, FIELDS, null, null, null);
        try {
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String displayName = cursor.getString(1);
                    String color = cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR));
                    Boolean selected = !cursor.getString(3).equals("0");
                    String id = cursor.getString(4);
                    calendars.add(displayName);
                    calendarsIDs.add(id);
                }
            }
        } catch (AssertionError ex) {
        }

        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        HashSet <String> nameOfEvent = new HashSet<String>();
        nameOfEvent.clear();
        /*startDates.clear();
        endDates.clear();
        descriptions.clear();*/
        for (int i = 0; i < CNames.length; i++) {

            nameOfEvent.add(cursor.getString(1));
            /*startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            descriptions.add(cursor.getString(2));*/
            CNames[i] = cursor.getString(1);
            final String begin = getDate(cursor.getLong(1));
            Log.e("BEGIN DATE",begin);
            cursor.moveToNext();

        }
        Log.e("NAME OF EVENT",nameOfEvent.toString());


        return calendarsIDs;
    }



    public final Uri CALENDAR_EVENT_URI = Uri.parse("content://com.android.calendar/calendars/events");
    public void getCalendarEvent(){
        int id = 3;
        // Uri.Builder builder = Uri.parse("content://calendar/instances/when").buildUpon();
        Uri.Builder builder = CALENDAR_EVENT_URI.buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
        ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);

        Cursor eventCursor = contentResolver.query(builder.build(),
                new String[] { "title", "begin", "end", "allDay"}, "Calendars._id=" + id,
                null, "startDay ASC, startMinute ASC");

        while (eventCursor.moveToNext()) {
            final String title = eventCursor.getString(0);
            final String begin = getDate(eventCursor.getLong(1));
            Log.e("BEGIN DATE",begin);
            final Date end = new Date(eventCursor.getLong(2));
            final Boolean allDay = !eventCursor.getString(3).equals("0");

            System.out.println("Title: " + title + " Begin: " + begin + " End: " + end +
                    " All Day: " + allDay);
        }


//--------------------------------------------
        //      int id = 3;
        //Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
        //      String[] getValue = {"title"};
        //   Cursor eventCursor = contentResolver.query(CALENDAR_URI, getValue, "Calendars._id="+id , null, null);
        //     Cursor eventCursor = contentResolver.query(CALENDAR_URI, getValue, "Calendars._id= \"3\"" , null, null);

        //     while (eventCursor.moveToNext()) {
        //      String x = eventCursor.getString(0);
        //    Log.e("event","EVenT: " + x);
        //    }
        Log.e("event","EVenT: ");
    }


    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }




}