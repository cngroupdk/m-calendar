package dk.cngroup.m_calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;

public class MyDialog extends Dialog {


    private final int LIMIT = 30000;
    private final int SECOND =  1000;


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


}
