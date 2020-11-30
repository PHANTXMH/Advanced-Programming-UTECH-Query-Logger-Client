package covid.client.models;

import java.sql.Time;
import java.util.List;

import covid.client.enumeration.Day;

public class LiveChatAvailability {

    private long id;

    private User user;

    private Time startTime;

    private Time endTime;

    private List<Day> liveChatAvailableDays;

    public LiveChatAvailability(long id, User user, Time startTime, Time endTime, List<Day> liveChatAvailableDays) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.liveChatAvailableDays = liveChatAvailableDays;
    }
    
    public LiveChatAvailability()
    {
    	
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Day> getLiveChatAvailableDays() {
        return liveChatAvailableDays;
    }

    public void setLiveChatAvailableDays(List<Day> liveChatAvailableDays) {
        this.liveChatAvailableDays = liveChatAvailableDays;
    }

    @Override
    public String toString() {
        return "LiveChatAvailability{" +
                "id=" + id +
                ", user=" + user +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", liveChatAvailableDays=" + liveChatAvailableDays +
                '}';
    }
}
