package dk.cngroup.m_calendar.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.R;
import dk.cngroup.m_calendar.UpcomingMeeting;
import dk.cngroup.m_calendar.UpcomingMeeting_;


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

        upcomingMeeting.initUpcomingMeeting(getItem(position), position);
        return upcomingMeeting;
    }
}
