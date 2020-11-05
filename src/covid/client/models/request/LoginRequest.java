package covid.client.models.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private int username;
    private String password;

    public LoginRequest(int username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
