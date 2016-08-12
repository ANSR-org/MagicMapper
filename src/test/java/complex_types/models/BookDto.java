package complex_types.models;

/**
 * Created by RoYaL on 8/12/2016.
 */
public class BookDto {
    private long id;

    private String name;

    /**
     * This constructor is needed by the framework.
     * If you don't have any constructors, a parameterless
     * one will be created by default. Once you define a constructor
     * you need to define a parameterless one as well
     */
    public BookDto() {

    }

    public BookDto(long id, String bookName) {
        this.setId(id);
        this.setName(bookName);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
