package collections.complex_types.models;

import java.util.Date;
import java.util.List;

public class AuthorEntity {

    private int id;

    private String authorName;

    private Date date;

    private List<BookEntity> bookEntity;

    public AuthorEntity() {

    }

    public AuthorEntity(int id, String name, Date bornOn, List<BookEntity> bookEntity) {
        this.setId(id);
        this.setAuthorName(name);
        this.setDate(bornOn);
        this.setBookEntity(bookEntity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<BookEntity> getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(List<BookEntity> bookEntity) {
        this.bookEntity = bookEntity;
    }
}
