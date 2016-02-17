package dk.cngroup.m_calendar;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;

import dk.cngroup.m_calendar.layout.MeetingAdapter;


@EFragment(R.layout.fragment_upcoming_meetings)
public class UpcomingMeetingsFragment extends Fragment {

    @ViewById(R.id.listView)
    ListView listview;

    @ViewById(R.id.scrollView)
    ScrollView scrollView;

    @ViewById(R.id.upButton)
    ImageButton upButton;

    @ViewById(R.id.downButton)
    ImageButton downButton;

    private static final int NUMBER_OF_DISPLAYED_MEETINGS = 3;
    private static int HEIGHT_OF_ROW = 200;

    @AfterViews
    public void init() {
        Log.e("LIFE ", "UPC FRAG INIT");
        ArrayList<Meeting> upcomingMeetings = new ArrayList<Meeting>();
        Session session = Session.getInstance();
        OutlookCalendar outlookCalendar = session.getOutlookCalendar();
        if (outlookCalendar != null) {
            upcomingMeetings = outlookCalendar.getUpcomingMeetings();
            Meeting[] meetings1 = new Meeting[upcomingMeetings.size()];
            meetings1 = upcomingMeetings.toArray(meetings1);
            ListAdapter listAdapter = new MeetingAdapter(getActivity().getBaseContext(), Arrays.asList(meetings1));
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

    @Click(R.id.upButton)
    public void scrollUp(View v) {
        controlButtons();
        // scroll po jednom
        int index = listview.getFirstVisiblePosition();
        listview.setSelectionFromTop(index, HEIGHT_OF_ROW);
        //listview.smoothScrollToPosition(index - 1);
    }

    @Click(R.id.downButton)
    public void scrollDown(View v) {
        controlButtons();
        int index = listview.getLastVisiblePosition();
        //listview.setScrollY(index*heightOfRow);
        //listview.smoothScrollToPosition(index + 1);
        listview.setSelectionFromTop(index, HEIGHT_OF_ROW);
        //listview.setSelection()  setSelection(index) so the display will jump to the index you want;
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
            Log.e("SCROLL LAYOUT", "0");
        } else {
            int firstVisible = listview.getFirstVisiblePosition();
            int lastVisible = listview.getLastVisiblePosition();
            Log.e("SCROLL LAYOUT", "FIRST " + firstVisible + " LAST " + lastVisible);
            if (firstVisible == 0) {
                Log.e("SCROLL LAYOUT", "1");
                upButton.setVisibility(View.INVISIBLE);
                downButton.setVisibility(View.VISIBLE);
            } else if (lastVisible == listview.getAdapter().getCount() - 1) {
                Log.e("SCROLL LAYOUT", "2");
                upButton.setVisibility(View.VISIBLE);
                downButton.setVisibility(View.INVISIBLE);
            } else {
                Log.e("SCROLL LAYOUT", "3");
                upButton.setVisibility(View.VISIBLE);
                downButton.setVisibility(View.VISIBLE);
            }
        }
        Log.e("SCROLL LAYOUT", "-----------------------");


    }


}
