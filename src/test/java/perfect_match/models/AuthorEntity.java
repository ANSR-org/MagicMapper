package perfect_match.models;

import java.util.Date;

public class AuthorEntity {

    private int id;

    private String name;

    private Date bornOn;

    public AuthorEntity() {

    }

    public AuthorEntity(int id, String name, Date bornOn) {
        this.setId(id);
        this.setName(name);
        this.setBornOn(bornOn);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBornOn() {
        return bornOn;
    }

    public void setBornOn(Date bornOn) {
        this.bornOn = bornOn;
    }
}
