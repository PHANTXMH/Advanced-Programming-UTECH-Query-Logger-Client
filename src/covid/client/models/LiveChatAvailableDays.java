package covid.client.models;

import covid.client.enumeration.Day;

public class LiveChatAvailableDays {
    private Long id;
    private LiveChatAvailability liveChatAvailability;
    private Day day;

    public LiveChatAvailableDays() {
    }

    public LiveChatAvailableDays(Long id, LiveChatAvailability liveChatAvailability, Day day) {
        this.id = id;
        this.liveChatAvailability = liveChatAvailability;
        this.day = day;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LiveChatAvailability getLiveChatAvailability() {
        return liveChatAvailability;
    }

    public void setLiveChatAvailability(LiveChatAvailability liveChatAvailability) {
        this.liveChatAvailability = liveChatAvailability;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "LiveChatAvailableDays{" +
                "id=" + id +
                ", liveChatAvailability=" + liveChatAvailability +
                ", day=" + day +
                '}';
    }
}
