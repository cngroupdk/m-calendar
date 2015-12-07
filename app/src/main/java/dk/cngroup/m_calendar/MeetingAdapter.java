package dk.cngroup.m_calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class MeetingAdapter extends ArrayAdapter<Meeting> {

    Context mContext;

    public MeetingAdapter(Context context, List<Meeting> meetings) {
        super(context, R.layout.upcoming_meeting, meetings);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UpcomingMeeting upcomingMeeting;
        if(convertView == null){
            upcomingMeeting = UpcomingMeeting_.build(mContext);
        }else{
            upcomingMeeting = (UpcomingMeeting) convertView;
        }

        upcomingMeeting.initUpcomingMeeting(getItem(position));
        return upcomingMeeting;
    }
}
