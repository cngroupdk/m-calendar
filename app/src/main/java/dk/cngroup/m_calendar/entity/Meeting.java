package dk.cngroup.m_calendar.entity;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Meeting implements Comparable<Meeting> {
    private GregorianCalendar beginTime;
    private GregorianCalendar endTime;
    private String name = "";
    private Person organizator;
    private ArrayList<Person> attendees = new ArrayList<Person>();

    public Meeting(GregorianCalendar beginTime, GregorianCalendar endTime, String name, Person organizator, ArrayList<Person> attendees) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.name = name;
        this.organizator = organizator;
        this.attendees = attendees;
    }

    public Meeting() {
    }

    public int getNumberOfParticipants() {
        return attendees.size();
    }

    public String getName() {
        return name;
    }

    public Person getOrganizator() {
        return organizator;
    }

    public ArrayList<Person> getAttendees() {
        return attendees;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setOrganizator(Person organizator) {
        this.organizator = organizator;
    }

    public void addParticipant(Person name) {
        this.attendees.add(name);
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

    public String getMeetingTime() {
        return formatTime(beginTime) + " - " + formatTime(endTime);
    }

    public String formatTime(GregorianCalendar cg) {
        int minute = cg.get(GregorianCalendar.MINUTE);
        String minutes = (minute < 9) ? "0" + minute + "" : minute + "";
        return cg.get(GregorianCalendar.HOUR_OF_DAY) + "" + ":" + minutes;
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

    private void ignoreMilliseconds(Meeting m) {
        m.getBeginTime().set(GregorianCalendar.MILLISECOND, 0);
        m.getEndTime().set(GregorianCalendar.MILLISECOND, 0);
    }

    @Override
    public boolean equals(Object o) {
        Meeting another = (Meeting) o;
        ignoreMilliseconds(this);
        ignoreMilliseconds(another);
        return this.getName().equals(another.getName()) &&
                this.getBeginTime().equals(another.getBeginTime()) &&
                this.getEndTime().equals(another.getEndTime());
    }
}


