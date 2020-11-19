package covid.client.models;


import covid.client.enumeration.ComplainStatus;

import java.util.Date;

public class Complaints{

    private Long id;

    private Services services;

    private ComplainStatus complainStatus;

    private String query;

    private Boolean read;

    private Date readDate;
    
    private Date createdAt;
    
   public Complaints() {
    }

    public Complaints(Long id, Services services, ComplainStatus complainStatus, String query, Boolean read, Date readDate) {
        this.id = id;
        this.services = services;
        this.complainStatus = complainStatus;
        this.query = query;
        this.read = read;
        this.readDate = readDate;
    }

    public Complaints(Services services, String query) {
        this.services = services;
        this.query = query;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public ComplainStatus getComplainStatus() {
        return complainStatus;
    }

    public void setComplainStatus(ComplainStatus complainStatus) {
        this.complainStatus = complainStatus;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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

    @Override
    public String toString() {
        return "Complaints{" +
                "id=" + id +
                ", services=" + services +
                ", complainStatus=" + complainStatus +
                ", query='" + query + '\'' +
                ", read=" + read +
                ", readDate=" + readDate +
                '}';
    }
}
