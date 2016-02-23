package dk.cngroup.m_calendar.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import dk.cngroup.m_calendar.entity.CurrentState;
import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.R;
import dk.cngroup.m_calendar.Session;

public class CurrentMeetingDialog extends MeetingDialog {
    public CurrentMeetingDialog(final Context context, Meeting meeting) {
        super(context, meeting);

        final CurrentMeetingDialog dialog = this;
        final Meeting currentMeeting = meeting;
        final Session session = Session.getInstance();

        View separator = this.findViewById(R.id.separator);
        separator.setVisibility(View.VISIBLE);

        Button startButton = (Button) this.findViewById(R.id.startbtn);
        startButton.setVisibility(View.VISIBLE);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setCurrentState(new CurrentState(currentMeeting));
                session.getCurrentState().setIsStarted(true);
                dialog.cancel();
            }
        });

        Button finishButton = (Button) this.findViewById(R.id.finish);
        finishButton.setVisibility(View.VISIBLE);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setCurrentState(new CurrentState(currentMeeting));
                session.getCurrentState().setIsFinished(true);
                dialog.cancel();

            }
        });

    }

}
