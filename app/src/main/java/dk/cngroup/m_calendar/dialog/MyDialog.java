package dk.cngroup.m_calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;

import dk.cngroup.m_calendar.SessionAllOrganizersInfo;

public class MyDialog extends Dialog {


    private final int LIMIT = 30000;
    private final int SECOND =  1000;

    private SessionAllOrganizersInfo sessionAllOrganizersInfo = SessionAllOrganizersInfo.getInstance();


    public MyDialog(Context context) {
        super(context);

        final Dialog dialog = this;

        new CountDownTimer(LIMIT, SECOND) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                dialog.dismiss();


            }
        }.start();



    }

    @Override
    public void dismiss() {
        super.dismiss();
        sessionAllOrganizersInfo.setOrganizer(null);
    }
}
