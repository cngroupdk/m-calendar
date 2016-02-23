package dk.cngroup.m_calendar.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.Person;
import dk.cngroup.m_calendar.R;
import dk.cngroup.m_calendar.SkypeManager;

public class MeetingDialog extends MyDialog {

    public MeetingDialog(Context context, Meeting meeting) {
        super(context);

        final Context context1 = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.current_meeting_control_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView meetingName = (TextView) this.findViewById(R.id.meetingName);
        TextView telephoneNumber = (TextView) this.findViewById(R.id.telephoneNumber);
        TextView organizedBy = (TextView) this.findViewById(R.id.organizedBy);
        hideControlButtons();


        if (meeting != null) {
            meetingName.setText(meeting.getName());
            Person organizer = meeting.getOrganizator();
            ImageButton skypeButton = (ImageButton) this.findViewById(R.id.skypeButton);
            skypeButton.setVisibility(View.GONE);

            if (organizer != null) {

                organizedBy.setText(getContext().getResources().getString(R.string.meeting_dialog_organizedBy, organizer.getName()));
                telephoneNumber.setText(getContext().getResources().getString(R.string.meeting_dialog_telephone_number,organizer.getTel_number()));
                final String organizerSkype = organizer.getSkype();
                //final String organizerSkype = "beebzb";
                if (!organizerSkype.equals("")) {
                    skypeButton.setVisibility(View.VISIBLE);
                    skypeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SkypeManager(context1, organizerSkype);
                        }
                    });
                }
            }
            else {
                organizedBy.setText(getContext().getResources().getString(R.string.meeting_dialog_organizedBy, "Unknown"));
                telephoneNumber.setText(getContext().getResources().getString(R.string.meeting_dialog_telephone_number, "Unknown"));


            }
        }

        this.show();
    }

    private void hideControlButtons() {
        View separator = this.findViewById(R.id.separator);
        Button startButton = (Button) this.findViewById(R.id.startbtn);
        Button finishButton = (Button) this.findViewById(R.id.finish);
        separator.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        finishButton.setVisibility(View.GONE);
    }
}
