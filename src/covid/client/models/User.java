package covid.client.models;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class User implements Serializable {

    public User() {
    }

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Long contact;
    private String password;
    private String role;
    private Set<LiveChatAvailability> liveChatAvailabilities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<LiveChatAvailability> getLiveChatAvailabilities() {
        return liveChatAvailabilities;
    }

    public void setLiveChatAvailabilities(Set<LiveChatAvailability> liveChatAvailabilities) {
        this.liveChatAvailabilities = liveChatAvailabilities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contact=" + contact +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
