package covid.client.models;

import covid.client.enumeration.Day;

import java.util.List;

public class LiveChatAvailability {

    private long id;

    private Day day;

    private User user;

    List<LiveChatAvailableTime> liveChatAvailableTimes;

    public LiveChatAvailability() {
    }

    public LiveChatAvailability(long id, Day day, User user) {
        this.id = id;
        this.day = day;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LiveChatAvailability(long id, Day day, User user, List<LiveChatAvailableTime> liveChatAvailableTimes) {
        this.id = id;
        this.day = day;
        this.user = user;
        this.liveChatAvailableTimes = liveChatAvailableTimes;
    }

    public List<LiveChatAvailableTime> getLiveChatAvailableTimes() {
        return liveChatAvailableTimes;
    }

    public void setLiveChatAvailableTimes(List<LiveChatAvailableTime> liveChatAvailableTimes) {
        this.liveChatAvailableTimes = liveChatAvailableTimes;
    }

    @Override
    public String toString() {
        return "LiveChatAvailability{" +
                "id=" + id +
                ", day=" + day +
                ", user=" + user +
                '}';
    }
}
