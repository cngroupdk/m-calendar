package dk.cngroup.m_calendar;

import android.app.Fragment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import dk.cngroup.m_calendar.layout.MeetingAdapter;


@EFragment(R.layout.fragment_upcoming_meetings)
public class UpcomingMeetingsFragment extends Fragment {

    @ViewById(R.id.listView)
    ListView listview;

    @ViewById(R.id.scrollView)
    ScrollView scrollView;

    private static final int NUMBER_OF_DISPLAYED_MEETINGS = 3;
    private static int HEIGHT_OF_ROW = 200;

    @AfterViews
    public void init() {
        Log.e("LIFE ", "UPC FRAG INIT");
        ArrayList<Meeting> todayMeetings = MainActivity_.getCurrentTodayMeetings();
        if (todayMeetings == null) {
            Log.e("C. STATE","LOADING NEW FROM FILE");

            todayMeetings = MainActivity_.getTodayMeetings(getActivity().getBaseContext());


        }

        Meeting currentMeeting = MainActivity_.getCurrentMeeting();
        if (todayMeetings.size() != 0 && currentMeeting != null && MainActivity_.isBooked()) {
            Meeting firstMeeting = todayMeetings.get(0);
            if (firstMeeting.isSame(currentMeeting)) {
                Log.e("LIFE ", "UPC REMOVING FIRST");
                todayMeetings.remove(0);
            }
        }

        // todayMeetings = MainActivity_.getTodayMeetings(getActivity().getBaseContext());
        Log.e("C. STATE","SIZE "+ todayMeetings.size());
        Meeting[] meetings1 = new Meeting[todayMeetings.size()];
        meetings1 = todayMeetings.toArray(meetings1);

        ListAdapter listAdapter = new MeetingAdapter(getActivity().getBaseContext(), Arrays.asList(meetings1));
        listview.setAdapter(listAdapter);
        setListViewHeightBasedOnChildren(listview);
    }


    @Click(R.id.upButton)
    public void scrollUp(View v){
        // scroll po jednom
        int index = listview.getFirstVisiblePosition();
        listview.setSelectionFromTop(index, HEIGHT_OF_ROW);
        Log.e("firsttvisible", String.valueOf(index));
        //listview.smoothScrollToPosition(index - 1);
    }

    @Click(R.id.downButton)
    public void scrollDown(View v){
        int index = listview.getLastVisiblePosition();
        Log.e("LAST visible", String.valueOf(index));
        //listview.setScrollY(index*heightOfRow);
        //listview.smoothScrollToPosition(index + 1);
        listview.setSelectionFromTop(index, HEIGHT_OF_ROW);
        //listview.setSelection();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = NUMBER_OF_DISPLAYED_MEETINGS * HEIGHT_OF_ROW;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }





}
