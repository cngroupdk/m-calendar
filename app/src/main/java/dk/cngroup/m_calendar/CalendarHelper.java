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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EBean
public class CalendarHelper{
    @RootContext
    Context mContext;

    public final String[] FIELDS = { CalendarContract.Calendars.NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars._ID
    };

    public final Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/calendars");


    ContentResolver contentResolver;
    Set<String> calendars = new HashSet<String>();

    @AfterInject
    public void init() {
        contentResolver = mContext.getContentResolver();
        Log.e("CALENDAR",getCalendarsName().toString());
        getCalendarEvent();
    }

    public Set<String> getCalendarsName() {
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
                }
            }
        } catch (AssertionError ex) {

        }
        return calendars;
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
            final Date begin = new Date(eventCursor.getLong(1));
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


}