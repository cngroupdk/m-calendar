package dk.cngroup.m_calendar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import dk.cngroup.m_calendar.entity.Meeting;
import dk.cngroup.m_calendar.entity.OutlookCalendar;
import dk.cngroup.m_calendar.entity.Person;

public class PersonInfoDownloader {
    public void getOrganizersInfo() {
        Session session = Session.getInstance();
        SessionAllOrganizersInfo sessionAllOrganizersInfo = SessionAllOrganizersInfo.getInstance();

        OutlookCalendar outlookCalendar = session.getOutlookCalendar();

        if (outlookCalendar != null) {
            ArrayList<Meeting> meetings = outlookCalendar.getAllMeetings();
            if (meetings != null) {
                PersonHttpClient personHttpClient = new PersonHttpClient();
                for (Meeting meeting : meetings) {
                    if (meeting.getOrganizator() != null) {
                        personHttpClient.startDownload(meeting);
                    } else {
                        sessionAllOrganizersInfo.addNewMeeting(meeting);
                    }
                }
            }
        }
    }


    private class PersonHttpClient extends AsyncHttpClient {
        private String url = "http://172.28.70.254/timurservices/api/contact?key=";

        public void startDownload(final Meeting meeting) {

            final SessionAllOrganizersInfo sessionAllOrganizersInfo = SessionAllOrganizersInfo.getInstance();
            if (meeting != null) {
                final Person organizer;
                organizer = meeting.getOrganizator();
                if (organizer != null) {
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
                                meeting.setOrganizator(organizer);
                                sessionAllOrganizersInfo.addNewMeeting(meeting);
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
    }
}
