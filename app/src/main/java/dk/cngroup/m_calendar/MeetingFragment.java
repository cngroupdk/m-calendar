package dk.cngroup.m_calendar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@EFragment(R.layout.fragment_meeting)
public class MeetingFragment extends Fragment {

    private Meeting meeting = new Meeting(new Date(), new Date(), "Info meeting", "Steen Westh Nielsen", new ArrayList<String>(Arrays.asList(new String[]{ "Milan Piskla","Jaromir Kohlicek", "Michal Sirica","Eva Jackova", "Lasd", "sdsdsd", "huhuhu"})));
    private ArrayList<String> participants = meeting.getParticipants();
    private static final int NUMBER_OF_DISPLAED_LABELS = 5;

    @ViewById(R.id.organizedBy)
    TextView organizedBy;

    @ViewById(R.id.meetingName)
    TextView meetingName;

    @ViewById (R.id.participantsHorizontalLayout)
    LinearLayout horizontalLayout;

    private final int numberOfParticipants = meeting.getNumberOfParticipants();

    private String textMoreParticipants;

    @AfterViews
    public void fillMeeting(){
        int rest = numberOfParticipants - NUMBER_OF_DISPLAED_LABELS;
        textMoreParticipants = getActivity().getBaseContext().getResources().getString(R.string.more_participants, String.valueOf(rest));
        String textOrganizator = getActivity().getBaseContext().getResources().getString(R.string.organizedBy, meeting.getOrganizator());
        organizedBy.setText(textOrganizator);
        meetingName.setText(meeting.getName());

        fillNameLabels();
    }

    void showAlert(){
        CharSequence[] items = participants.toArray(new CharSequence[participants.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Participants");
        builder.setItems(items, null);
        builder.setNeutralButton(R.string.okButton, null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fillNameLabels(){
        Context context = getActivity().getBaseContext();
        for (int i = 0; i < NUMBER_OF_DISPLAED_LABELS; i++) {
            if (i < numberOfParticipants){
                LayoutInflater inflater = LayoutInflater.from(context);
                TextView textView = (TextView) inflater.inflate(R.layout.participant_label, horizontalLayout, false);
                textView.setText(participants.get(i).substring(0, 3));
                horizontalLayout.addView(textView);
            }
        }

        if (NUMBER_OF_DISPLAED_LABELS < numberOfParticipants){
            TextView  label = new TextView(context,null,R.style.SmallButton);
            label.setText(textMoreParticipants);
            label.setPaintFlags(label.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
            label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlert();
                }
            });
            horizontalLayout.addView(label);
        }
    }
}



