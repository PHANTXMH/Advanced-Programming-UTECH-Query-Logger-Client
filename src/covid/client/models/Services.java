package covid.client.models;

import java.io.Serializable;

public class Services extends TimeStamp implements Serializable {

    private Long id;

    private String name;

    private String icon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Services(Long id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public Services(Long id) {
        this.id = id;
    }

    public Services() {
    }
}
