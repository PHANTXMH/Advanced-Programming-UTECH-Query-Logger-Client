package covid.client.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Chat {

    private Long id;

    private Date dateCreated;

    private User to;

    private User from;

    private List<ChatMessages> chatMessagesList = new ArrayList<>();

    public Chat() {
    }

    public Chat(Long id, Date dateCreated, User to, User from, List<ChatMessages> chatMessagesList) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.to = to;
        this.from = from;
        this.chatMessagesList = chatMessagesList;
    }

    public Chat(User to, User from) {
        this.to = to;
        this.from = from;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public List<ChatMessages> getChatMessagesList() {
        return chatMessagesList;
    }

    public void setChatMessagesList(List<ChatMessages> chatMessagesList) {
        this.chatMessagesList = chatMessagesList;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", dateCreated=" + dateCreated +
                ", to=" + to +
                ", from=" + from +
                ", chatMessagesList=" + chatMessagesList +
                '}';
    }
}
