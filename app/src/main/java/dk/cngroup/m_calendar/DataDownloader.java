package dk.cngroup.m_calendar;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DataDownloader {
    private URL rssUrl ;
    private String rssResult = "";
    DataParser dataParser = new DataParser();
    private Context context;

    public DataDownloader(Context context){
        this.context = context;
    }

    public String getCalendarFromUrl(){
        Log.e("SERVICE", "init");

        AsyncTask<Object, Void, String> t = new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {
                    Log.e("SERVICE", "do in Background");
                    String defaultStr = "UrlWasNotSet";
                   // defaultStr = "http://davinci.fmph.uniba.sk/~bachronikov2/testing/calendar1.ics";
                    String url = PreferenceManager.getDefaultSharedPreferences(context).getString("URL",defaultStr );
                    rssUrl = new URL(url);
                    //rssUrl = new URL("http://davinci.fmph.uniba.sk/~bachronikov2/testing/calendar1.ics");
                    StringBuilder b = new StringBuilder();
                    BufferedReader r = new BufferedReader(new InputStreamReader(rssUrl.openStream()));

                    try {
                        for (String line; (line = r.readLine()) != null; ) {
                            b.append(line).append("\n");
                        }
                    } finally {
                        r.close();
                    }
                    rssResult = b.toString();
                    return rssResult;

                } catch (MalformedURLException e) {
                    //throw new RuntimeException(e);
                    return e.getMessage();
                } catch (IOException e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String mess) {
                Log.e("SERVICE", "post execute ");
                Session session = Session.getInstance();
                ArrayList<Meeting> meetings = dataParser.getMeetingsFromString(mess);
                session.setOutlookCalendar(meetings);
            }
        };

        t.execute();
        return rssResult;

    }

}
