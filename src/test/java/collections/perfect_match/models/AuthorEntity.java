package collections.perfect_match.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorEntity {

    private int id;

    private String name;

    private Date bornOn;

    private List<Integer> bookRates;

    public AuthorEntity() {

    }

    public AuthorEntity(int id, String name, Date bornOn) {
        this.setId(id);
        this.setName(name);
        this.setBornOn(bornOn);
        this.bookRates = new ArrayList<>();
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

    public List<Integer> getBookRates() {
        return bookRates;
    }

    public void setBookRates(List<Integer> bookRates) {
        this.bookRates = bookRates;
    }
}
