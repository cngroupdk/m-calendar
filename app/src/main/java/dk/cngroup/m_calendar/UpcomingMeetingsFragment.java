package dk.cngroup.m_calendar;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;

import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.OutlookCalendar;
import dk.cngroup.m_calendar.layout.MeetingAdapter;


@EFragment(R.layout.fragment_upcoming_meetings)
public class UpcomingMeetingsFragment extends Fragment {

    @ViewById(R.id.listView)
    ListView listview;

    @ViewById(R.id.upButton)
    ImageButton upButton;

    @ViewById(R.id.downButton)
    ImageButton downButton;

    private static final int NUMBER_OF_DISPLAYED_MEETINGS = 3;
    private static int HEIGHT_OF_ROW = 200;

    @AfterViews
    public void init() {
        ArrayList<Meeting> upcomingMeetings = new ArrayList<Meeting>();
        Session session = Session.getInstance();
        OutlookCalendar outlookCalendar = session.getOutlookCalendar();
        if (outlookCalendar != null) {
            upcomingMeetings = outlookCalendar.getUpcomingMeetings();
            Meeting[] meetings1 = new Meeting[upcomingMeetings.size()];
            meetings1 = upcomingMeetings.toArray(meetings1);
            Activity activity = getActivity();
            if (activity != null) {
                ListAdapter listAdapter = new MeetingAdapter(activity.getBaseContext(), Arrays.asList(meetings1));
                listview.setAdapter(listAdapter);

                setListViewHeightBasedOnChildren(listview);
                controlButtons();

                listview.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        controlButtons();
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    }
                });
            }
        }
    }

    @Click(R.id.upButton)
    public void scrollUp() {
        controlButtons();
        int index = listview.getFirstVisiblePosition();
        listview.setSelectionFromTop(index, HEIGHT_OF_ROW);
    }

    @Click(R.id.downButton)
    public void scrollDown() {
        controlButtons();
        int index = listview.getLastVisiblePosition();
        listview.setSelectionFromTop(index, HEIGHT_OF_ROW);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = NUMBER_OF_DISPLAYED_MEETINGS * HEIGHT_OF_ROW;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void controlButtons() {
        if (listview.getAdapter().getCount() <= NUMBER_OF_DISPLAYED_MEETINGS) {
            upButton.setVisibility(View.INVISIBLE);
            downButton.setVisibility(View.INVISIBLE);
        } else {
            int firstVisible = listview.getFirstVisiblePosition();
            int lastVisible = listview.getLastVisiblePosition();
            if (firstVisible == 0) {
                upButton.setVisibility(View.INVISIBLE);
                downButton.setVisibility(View.VISIBLE);
            } else if (lastVisible == listview.getAdapter().getCount() - 1) {
                upButton.setVisibility(View.VISIBLE);
                downButton.setVisibility(View.INVISIBLE);
            } else {
                upButton.setVisibility(View.VISIBLE);
                downButton.setVisibility(View.VISIBLE);
            }
        }

    }


}
