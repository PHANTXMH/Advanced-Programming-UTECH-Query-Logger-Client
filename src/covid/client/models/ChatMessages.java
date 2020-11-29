package covid.client.models;
import java.util.Date;


public class ChatMessages {

    private Long id;

    private String message;

    private Date createdAt;

    private Boolean read;

    private Date readDate;

    private Chat chat;

    private User sendBy;

    public ChatMessages() {
    }

    public ChatMessages(Long id, String message, Date createdAt, Boolean read, Date readDate, Chat chat, User sendBy) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.read = read;
        this.readDate = readDate;
        this.chat = chat;
        this.sendBy = sendBy;
    }

    public ChatMessages(String message, Boolean read) {
        this.message = message;
        this.read = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getSendBy() {
        return sendBy;
    }

    public void setSendBy(User sendBy) {
        this.sendBy = sendBy;
    }

    @Override
    public String toString() {
        return "ChatMessages{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                ", read=" + read +
                ", readDate=" + readDate +
                ", chat=" + chat +
                ", sendBy=" + sendBy +
                '}';
    }
}
