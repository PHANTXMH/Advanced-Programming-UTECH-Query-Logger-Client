package covid.client.models;


import java.util.Date;

public class ComplaintResponses{

    private Long id;

    private String response;

    private Boolean read;

    private Date readDate;

    private Date createdAt;

    private Complaints complaints;

    private User createdUser;

    private User modifyUser;

    public ComplaintResponses() {
    }

    public ComplaintResponses(Long id, String response, Boolean read, Date readDate, Date createdAt, Complaints complaints, User createdUser, User modifyUser) {
        this.id = id;
        this.response = response;
        this.read = read;
        this.readDate = readDate;
        this.createdAt = createdAt;
        this.complaints = complaints;
        this.createdUser = createdUser;
        this.modifyUser = modifyUser;
    }
    
    public ComplaintResponses(String response, Complaints complaints, User createdUser)
    {
    	this.response = response;
    	this.complaints = complaints;
    	this.createdUser = createdUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Complaints getComplaints() {
        return complaints;
    }

    public void setComplaints(Complaints complaints) {
        this.complaints = complaints;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }

    public User getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(User modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    public String toString() {
        return "ComplaintResponses{" +
                "id=" + id +
                ", response='" + response + '\'' +
                ", read=" + read +
                ", readDate=" + readDate +
                ", createdAt=" + createdAt +
                ", complaints=" + complaints +
                ", createdUser=" + createdUser +
                ", modifyUser=" + modifyUser +
                '}';
    }
}
