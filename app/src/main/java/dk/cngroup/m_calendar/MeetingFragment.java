package dk.cngroup.m_calendar;

import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import dk.cngroup.m_calendar.dialog.CurrentMeetingDialog;
import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.OutlookCalendar;
import dk.cngroup.m_calendar.entity.Person;

@EFragment(R.layout.fragment_meeting)
public class MeetingFragment extends Fragment {

    @ViewById(R.id.organizedBy)
    TextView organizedBy;

    @ViewById(R.id.meetingName)
    TextView meetingName;

    @ViewById(R.id.meetingTime)
    TextView meetingTime;

    @ViewById(R.id.meetingParticipants)
    TextView meetingParticipants;

    @ViewById(R.id.meetingFragmentMain)
    LinearLayout meetingFragmentLayout;

    private ArrayList<Person> attendees;

    private Meeting currentMeeting = null;

    @AfterViews
    public void fillMeeting() {
        Session session = Session.getInstance();
        OutlookCalendar outlookCalendar = session.getOutlookCalendar();

        if (outlookCalendar != null) {
            currentMeeting = outlookCalendar.getCurrentMeeting();
        }
        if (currentMeeting != null) {
            attendees = currentMeeting.getAttendees();
            int numberOfParticipants = currentMeeting.getNumberOfParticipants();
            String textMoreParticipants;
            if (numberOfParticipants != 0) {
                textMoreParticipants = getActivity().getBaseContext().getResources().getString(R.string.more_participants, String.valueOf(numberOfParticipants));
                meetingParticipants.setPaintFlags(meetingParticipants.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                meetingParticipants.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlert();
                    }
                });
                meetingParticipants.setText(textMoreParticipants);
            }
            if (currentMeeting.getOrganizator() != null) {
                organizedBy.setText(currentMeeting.getOrganizator().getName());

            }
            if (isAdded()) {
                organizedBy.setText(getActivity().getBaseContext().getResources().getString(R.string.unknown));
            }
            meetingName.setText(currentMeeting.getName());
            meetingTime.setText(currentMeeting.getMeetingTime());

            meetingFragmentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CurrentMeetingDialog(getActivity(), currentMeeting);
                }
            });
        }

    }


    void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getBaseContext().getResources().getString(R.string.attendees_dialog_title));
        builder.setItems(getAttendeesNames(), null);
        builder.setNeutralButton(R.string.okButton, null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private CharSequence[] getAttendeesNames() {
        List<String> listItems = new ArrayList<>();
        for (Person person : attendees) {
            listItems.add(person.getName());
        }
        return listItems.toArray(new CharSequence[listItems.size()]);
    }

}



