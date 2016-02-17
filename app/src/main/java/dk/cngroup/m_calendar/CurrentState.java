package dk.cngroup.m_calendar;


public class CurrentState {
    private boolean isStarted = false;
    private boolean isFinished = false;
    public Meeting currentMeeting = null;

    public boolean isFinished() {
        return isFinished;
    }


    public CurrentState(Meeting currentMeeting) {
        this.currentMeeting = currentMeeting;
    }

    public Meeting getCurrentMeeting() {
        return currentMeeting;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }
}
