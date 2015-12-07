package dk.cngroup.m_calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Meeting {
    private Date fromDate;
    private Date toDate;
    private String name;
    private String organizator;
    private ArrayList<String> participants = new ArrayList<String>();

    public Meeting(Date fromDate, Date toDate, String name, String organizator, ArrayList<String> participants) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.name = name;
        this.organizator = organizator;
        this.participants = participants;
    }

    public int getNumberOfParticipants(){
        return participants.size();
    }

    public String getName() {
        return name;
    }

    public String getOrganizator() {
        return organizator;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setOrganizator(String organizator) {
        this.organizator = organizator;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getMeetingTime(){
        return formatTime(fromDate) + " - " + formatTime(toDate);
    }

    public String formatTime(Date date){
        String result = "";
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(date);
    }

}
