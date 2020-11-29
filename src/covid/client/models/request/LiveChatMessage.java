package covid.client.models.request;

public class LiveChatMessage {

    private String name;
    private String message;
    private Long from;
    private Long to;

    public LiveChatMessage(String name, String message, Long from, Long to) {
        this.name = name;
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public LiveChatMessage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String toJsonString(){
        return "{ \"name\": \"" +this.name+ "\", \"message\": \""+this.message+"\", \"to\": "+this.to+", \"from\": "+this.from+" }";
    }

}
