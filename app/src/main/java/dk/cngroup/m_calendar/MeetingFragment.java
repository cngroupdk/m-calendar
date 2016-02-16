package dk.cngroup.m_calendar;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.GregorianCalendar;

@EFragment(R.layout.fragment_meeting)
public class MeetingFragment extends Fragment {
    Meeting meeting = new Meeting(new GregorianCalendar(), new GregorianCalendar(), "", "Unknown", new ArrayList<String>());

    private static final int NUMBER_OF_DISPLAED_LABELS = 0;

    @ViewById(R.id.organizedBy)
    TextView organizedBy;

    @ViewById(R.id.meetingName)
    TextView meetingName;

    @ViewById(R.id.meetingTime)
    TextView meetingTime;

    @ViewById (R.id.meetingParticipants)
    TextView meetingParticipants;

    @ViewById (R.id.meetingFragmentMain)
    LinearLayout meetingFragmentLayout;

    private ArrayList<String> participants;

    private String textMoreParticipants;

    private Meeting currentMeeting = null;

    @AfterViews
    public void fillMeeting(){
        Log.e("LIFE ", "MET FRAG. INIT");
        Session session = Session.getInstance();
        OutlookCalendar outlookCalendar = session.getOutlookCalendar();
        if (outlookCalendar != null){
            currentMeeting = outlookCalendar.getCurrentMeeting();
        }
        if (currentMeeting != null){
            meeting = currentMeeting;
            Log.e("LIFE ","Current " + meeting.toString());
            participants = meeting.getParticipants();
            int numberOfParticipants = meeting.getNumberOfParticipants();
            int rest = numberOfParticipants - NUMBER_OF_DISPLAED_LABELS;
            if (rest != 0) {
                textMoreParticipants = getActivity().getBaseContext().getResources().getString(R.string.more_participants, String.valueOf(rest));
                meetingParticipants.setPaintFlags(meetingParticipants.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                meetingParticipants.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlert();
                    }
                });
                meetingParticipants.setText(textMoreParticipants);
            }

            String textOrganizator = getActivity().getBaseContext().getResources().getString(R.string.organizedBy, meeting.getOrganizator());
            organizedBy.setText(meeting.getOrganizator());
            meetingName.setText(meeting.getName());
            meetingTime.setText(meeting.getMeetingTime());

            meetingFragmentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openControlDialog();
                }
            });
        }

    }

    void showAlert(){
        CharSequence[] items = participants.toArray(new CharSequence[participants.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getBaseContext().getResources().getString(R.string.attendees));
        builder.setItems(items, null);
        builder.setNeutralButton(R.string.okButton, null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void openControlDialog(){
        final Dialog dialog = new Dialog(getActivity());
        final Session session = Session.getInstance();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_meeting_control_dialog);

        TextView meetingName = (TextView) dialog.findViewById(R.id.meetingName);
        if (currentMeeting != null){
            meetingName.setText(meeting.getName());

        }


       Button startButton = (Button) dialog.findViewById(R.id.startbtn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setMeetingRoomStatus(MeetingRoomStatus.OCCUPIED);
                dialog.cancel();
            }
        });

        Button finishButton = (Button) dialog.findViewById(R.id.finish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setMeetingRoomStatus(MeetingRoomStatus.FREE);
                session.setCurrentMeetingUntimelyFinished(true);
                session.setUntimelyFinishedMeeting(meeting);
                dialog.cancel();

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }


}



