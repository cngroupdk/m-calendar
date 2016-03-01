package dk.cngroup.m_calendar.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import dk.cngroup.m_calendar.R;
import dk.cngroup.m_calendar.SkypeManager;
import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.Person;

public class MeetingDialog extends MyDialog {

    public TextView telephoneNumber;
    public TextView organizedBy;
    public ImageButton skypeButton;

    public MeetingDialog(Context context, Meeting meeting) {
        super(context);

        final Context context1 = context;
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.show();
        this.setContentView(R.layout.current_meeting_control_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView meetingName = (TextView) this.findViewById(R.id.meetingName);
        telephoneNumber = (TextView) this.findViewById(R.id.telephoneNumber);
        organizedBy = (TextView) this.findViewById(R.id.organizedBy);
        skypeButton = (ImageButton) this.findViewById(R.id.skypeButton);

        skypeButton.setVisibility(View.GONE);
        organizedBy.setVisibility(View.GONE);
        telephoneNumber.setVisibility(View.GONE);
        hideControlButtons();

        if (meeting != null) {
            meetingName.setText(meeting.getName());
            final Person organizer = meeting.getOrganizator();
            organizedBy.setText(getContext().getResources().getString(R.string.meeting_dialog_organizedBy, "Unknown"));
            telephoneNumber.setText(getContext().getResources().getString(R.string.meeting_dialog_telephone_number, "Unknown"));

            if (organizer != null) {
                organizedBy.setText(getContext().getResources().getString(R.string.meeting_dialog_organizedBy, organizer.getName()));
                telephoneNumber.setText(getContext().getResources().getString(R.string.meeting_dialog_telephone_number, organizer.getTel_number()));
                if (!organizer.getSkype().equals("")) {
                    skypeButton.setVisibility(View.VISIBLE);
                    skypeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SkypeManager(context1, organizer.getSkype());
                        }
                    });
                }
            }
        }
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
