package covid.client.models;

import java.sql.Time;

public class LiveChatAvailableTime {

    private Long id;

    private Time startTime;

    private Time endTime;

    public LiveChatAvailableTime(Long id, Time startTime, Time endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LiveChatAvailableTime() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "LiveChatAvailableTime{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
