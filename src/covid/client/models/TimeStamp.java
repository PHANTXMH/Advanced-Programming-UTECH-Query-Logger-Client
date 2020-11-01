package covid.client.models;

import java.io.Serializable;
import java.util.Date;

public class TimeStamp implements Serializable {

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private User createdUser;

    private User modifyUser;

    public TimeStamp() {
    }

    public TimeStamp(Date createdAt, Date updatedAt, Date deletedAt, User createdUser, User modifyUser) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.createdUser = createdUser;
        this.modifyUser = modifyUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
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
}
