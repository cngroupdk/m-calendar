package dk.cngroup.m_calendar;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import dk.cngroup.m_calendar.entity.Person;

public class HttpClient extends AsyncHttpClient {
    private Person organizer;
    private String url = "http://172.28.70.254/timurservices/api/contact?key=";

    public HttpClient(Person person ) {
        if (person != null) {

            this.organizer = person;
            url = url + organizer.getName();


            this.get(url, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {


                        String telNum = response.getString("Phone");
                        String abbr = response.getString("Abbreviation");
                        String skype = response.getString("Skype");
                        if (!skype.equals("") && !skype.equals("UNDEFINED")) {
                            organizer.setSkype(skype);
                        }
                        organizer.setAbbreviation(abbr);
                        organizer.setTel_number(telNum);


                        Log.e("JSON", "------setting organizer---------");
                        Log.e("JSON", "org name " + organizer.getName());
                        Log.e("JSON", "org skype " + organizer.getSkype());
                        Log.e("JSON", "-------------------");


                        SessionAllOrganizersInfo sessionAllOrganizersInfo = SessionAllOrganizersInfo.getInstance();
                        sessionAllOrganizersInfo.setOrganizer(organizer);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                }
            });

        }
    }


}
