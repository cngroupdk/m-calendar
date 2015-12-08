package dk.cngroup.m_calendar;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import dk.cngroup.m_calendar.layout.MeetingAdapter;


@EFragment(R.layout.fragment_upcoming_meetings)
public class UpcomingMeetingsFragment extends Fragment {

    @ViewById(R.id.listView)
    ListView listview;

    @ViewById(R.id.scrollView)
    ScrollView scrollView;

    @AfterViews
    public void init() {

        Meeting[] meetings = {
                new Meeting(new Date(), new Date(),
                        "Info meeting", "Aaaen Westh Nielsen",
                        new ArrayList<String>(Arrays.asList(new String[]{"Stien Westh Nielsen", "Jan Cerny", "Milan Piskla"}))),
                new Meeting(new Date(), new Date(),
                "Info meeting2", "Bbbaen Westh Nielsen",
                new ArrayList<String>(Arrays.asList(new String[]{"Stien Westh Nielsen", "Jan Cerny", "Milan Piskla"}))),
                new Meeting(new Date(), new Date(),
                        "Info meeting3", "Cccen Westh Nielsen",
                        new ArrayList<String>(Arrays.asList(new String[]{"Stien Westh Nielsen", "Jan Cerny", "Milan Piskla"}))),
                new Meeting(new Date(), new Date(),
                        "Info meeting4", "Ddden Westh Nielsen",
                        new ArrayList<String>(Arrays.asList(new String[]{"Stien Westh Nielsen", "Jan Cerny", "Milan Piskla"}))),
                new Meeting(new Date(), new Date(),
                        "Info meeting5", "Eeeen Westh Nielsen",
                        new ArrayList<String>(Arrays.asList(new String[]{"Stien Westh Nielsen", "Jan Cerny", "Milan Piskla"}))),

        };

        ListAdapter listAdapter = new MeetingAdapter(getActivity().getBaseContext(),Arrays.asList(meetings));
        listview.setAdapter(listAdapter);
        //listview.smoothScrollByOffset(68);
    }


    @Click(R.id.upButton)
    public void scrollUp(View v){
        int index = listview.getFirstVisiblePosition();
        Log.e("firsttvisible", String.valueOf(index));
        //listview.smoothScrollToPosition(index - 1);
        listview.setSelectionFromTop(index , 68);
    }

    @Click(R.id.downButton)
    public void scrollDown(View v){
        int heightOfRow = listview.getChildAt(0).getHeight();
        int index = listview.getLastVisiblePosition() +  1;
        Log.e("LAST visible", String.valueOf(index));
        //listview.setScrollY(index*heightOfRow);
        //listview.smoothScrollToPosition(index + 1);
        listview.setSelectionFromTop(index, 68);
        //listview.setSelection();
    }


}
