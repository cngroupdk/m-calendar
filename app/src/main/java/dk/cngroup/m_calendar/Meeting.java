package dk.cngroup.m_calendar;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Meeting implements Comparable<Meeting>{
    private GregorianCalendar beginTime;
    private GregorianCalendar endTime;
    private String name = "";
    private String organizator = "";
    private ArrayList<String> participants = new ArrayList<String>();

    public Meeting(GregorianCalendar beginTime, GregorianCalendar endTime, String name, String organizator, ArrayList<String> participants) {
        this.beginTime = beginTime;
        this.endTime = endTime;
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

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public GregorianCalendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(GregorianCalendar beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

    public String getMeetingTime(){
        return formatTime(beginTime) + " - " + formatTime(endTime);
    }

    public String formatTime(GregorianCalendar cg){
        int minute =  cg.get(GregorianCalendar.MINUTE);
        String minutes = (minute < 9 ) ?  "0" + minute+ "" : minute + "";
        return cg.get(GregorianCalendar.HOUR_OF_DAY)+ "" + ":" + minutes ;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Meeting another) {
        return getBeginTime().compareTo(another.getBeginTime());
    }

    public boolean isSame(Meeting another){
        return this.getName().equals(another.getName()) &&
                this.getOrganizator().equals(another.getOrganizator()) &&
                (this.getBeginTime().compareTo(another.getBeginTime()) == 0) &&
                (this.getEndTime().compareTo(another.getEndTime()) == 0);
    }
}


