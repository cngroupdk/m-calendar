package dk.cngroup.m_calendar;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@EFragment(R.layout.fragment_meeting)
public class MeetingFragment extends Fragment {

    private Meeting meeting = new Meeting(new Date(), new Date(), "Info meeting", "Steen Westh Nielsen", new ArrayList<String>(Arrays.asList(new String[]{"Stien Westh Nielsen", "Jan Cerny", "Milan Piskla","Jaromir Kohlicek", "Michal Sirica","Eva Jackova"})));
    private ArrayList<String> participants = meeting.getParticipants();
    private final int NUMBER_OF_DISPLAED_LABELS = 5;

    @ViewById(R.id.organizedBy)
    TextView organizedBy;

    @ViewById(R.id.meetingName)
    TextView meetingName;

    @ViewById(R.id.name1)
    TextView name1;

    @ViewById(R.id.name2)
    TextView name2;

    @ViewById(R.id.name3)
    TextView name3;

    @ViewById(R.id.name4)
    TextView name4;

    @ViewById(R.id.name0)
    TextView name0;

    @ViewById(R.id.allParticipantsTextView)
    TextView allParticipants;



    private final int numberOfParticipants = meeting.getNumberOfParticipants();

    @AfterViews
    public void fillMeeting(){
        int rest = numberOfParticipants - NUMBER_OF_DISPLAED_LABELS;
        String textMoreParticipants = getActivity().getBaseContext().getResources().getString(R.string.more_participants, String.valueOf(rest));
        String textOrganizator = getActivity().getBaseContext().getResources().getString(R.string.organizedBy, meeting.getOrganizator());

        organizedBy.setText(textOrganizator);
        meetingName.setText(meeting.getName());
        allParticipants.setText(textMoreParticipants);

        fillNameLabels();



    }

    @Click(R.id.allParticipantsTextView)
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
        TextView[] lables = {name0, name1, name2, name3, name4};
        TextView[] invisibleLables = {} ;

            for (int i = 0; i < lables.length; i++) {
                if (i >= numberOfParticipants){
                    lables[i].setVisibility(View.INVISIBLE);
                }
                else {
                    lables[i].setText(participants.get(i).substring(0, 3));
                }

            }


        if (NUMBER_OF_DISPLAED_LABELS >= numberOfParticipants){
            allParticipants.setVisibility(View.INVISIBLE);
        }


    }
}



