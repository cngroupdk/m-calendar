package dk.cngroup.m_calendar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

public class SkypeManager {
    private Context context;
    private String skypeName = "";

    public SkypeManager(Context context, String skypeName){
        this.context = context;
        this.skypeName = skypeName;
        Log.e("SKYPE", "INITIALIZE");
        if (!isSkypeClientInstalled()) {
            Log.e("SKYPE", "NOT INSTALED");
            goToMarket();
            return;
        }

        Uri skypeUri = Uri.parse(getUri());
        Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);

        myIntent.setComponent(new ComponentName("com.skype.raider", "com.skype.raider.Main"));
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(myIntent);
    }


    private boolean isSkypeClientInstalled() {
        PackageManager myPackageMgr = context.getPackageManager();
        try {
            myPackageMgr.getPackageInfo("com.skype.raider", PackageManager.GET_ACTIVITIES);
        }
        catch (PackageManager.NameNotFoundException e) {
            return (false);
        }
        return (true);
    }

    private  void goToMarket() {
        Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
        Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);

        return;
    }

    private String getUri(){
       return "skype:" + skypeName + "?call&video=true";
    }


}
