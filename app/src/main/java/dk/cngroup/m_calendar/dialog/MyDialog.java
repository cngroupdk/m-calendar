package dk.cngroup.m_calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;


public class MyDialog extends Dialog {

    public MyDialog(Context context) {
        super(context);
        final Dialog dialog = this;
        final int LIMIT = 30000;
        final int SECOND = 1000;

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
    }
}
