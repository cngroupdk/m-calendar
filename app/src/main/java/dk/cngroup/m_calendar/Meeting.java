package dk.cngroup.m_calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Meeting {
    private GregorianCalendar fromTime;
    private GregorianCalendar toTime;
    private String name;
    private String organizator;
    private ArrayList<String> participants = new ArrayList<String>();

    public Meeting(GregorianCalendar fromTime, GregorianCalendar toTime, String name, String organizator, ArrayList<String> participants) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.name = name;
        this.organizator = organizator;
        this.participants = participants;
    }

    public Meeting(){}

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

    public void addParticipant(String name){
        this.participants.add(name);
    }

    public GregorianCalendar getToTime() {
        return toTime;
    }

    public GregorianCalendar getFromTime() {
        return fromTime;
    }

    public void setFromTime(GregorianCalendar fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(GregorianCalendar toTime) {
        this.toTime = toTime;
    }

    public String getMeetingTime(){
        return formatTime(fromTime) + " - " + formatTime(toTime);
    }

    public String formatTime(GregorianCalendar cg){
        int minute =  cg.get(GregorianCalendar.MINUTE);
        String minutes = (minute < 9 ) ?  "0" + minute+ "" : minute + "";
        return cg.get(GregorianCalendar.HOUR_OF_DAY)+ "" + ":" + minutes ;
    }

}
