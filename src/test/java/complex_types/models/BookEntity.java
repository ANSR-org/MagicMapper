package complex_types.models;

/**
 * Created by RoYaL on 8/12/2016.
 */
    public class BookEntity {

        private long id;

        private String bookName;

    public BookEntity() {

    }

    public BookEntity(long id, String bookName) {
        this.setId(id);
        this.setBookName(bookName);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
