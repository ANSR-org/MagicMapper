package collections.complex_types;

import bg.ansr.magicmapper.core.ANSRMagicMapper;
import bg.ansr.magicmapper.core.MagicMapper;
import bg.ansr.magicmapper.core.field.CollectionField;
import bg.ansr.magicmapper.core.field.ComplexField;
import bg.ansr.magicmapper.core.field.SimpleField;
import collections.complex_types.models.AuthorDto;
import collections.complex_types.models.AuthorEntity;
import collections.complex_types.models.BookDto;
import collections.complex_types.models.BookEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComplexTypesFieldNameAsString {

    private MagicMapper mapper;

    @Before
    public void setUp() {
        this.mapper = new ANSRMagicMapper();
    }

    @Test
    public void testFieldIdentity_Id() throws ParseException {
        AuthorDto dto = this.getAuthorDto();

        Assert.assertEquals(6, dto.getId());
    }

    @Test
    public void testFieldIdentity_Name() throws ParseException {
        AuthorDto dto = this.getAuthorDto();

        Assert.assertEquals("John", dto.getName());
    }

    @Test
    public void testFieldIdentity_BornOn() throws ParseException {
        AuthorDto dto = this.getAuthorDto();

        Assert.assertEquals(Date.from(Instant.parse("2006-06-06T00:00:00.00Z")).getTime(), dto.getBornOn().getTime());
    }

    @Test
    public void testComplexFieldIdentity_Book_Id() throws ParseException {
        AuthorDto dto = this.getAuthorDto();

        Assert.assertEquals(42, dto.getBookDto().get(1).getId());
    }

    @Test
    public void testComplexFieldIdentity_Book_Name() throws ParseException {
        AuthorDto dto = this.getAuthorDto();

        Assert.assertEquals("Firestarter", dto.getBookDto().get(0).getName());
    }

    private AuthorDto getAuthorDto() throws ParseException {
        this.mapper
                .from(BookEntity.class)
                .to(BookDto.class)
                .forRule(
                        new SimpleField("id", "id"),
                        new SimpleField("bookName", "name")
                );

        this.mapper
                .from(AuthorEntity.class)
                .to(AuthorDto.class)
                .forRule(
                        new SimpleField("id", "id"),
                        new SimpleField("authorName", "name"),
                        new SimpleField("date", "bornOn"),
                        new CollectionField("bookEntity", "bookDto", this.mapper.getExistingBinding(BookEntity.class, BookDto.class))
                );

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date bornOn = sdf.parse("06.06.2006 03:00:00");

        List<BookEntity> books = new ArrayList<>();
        books.add(new BookEntity(12, "Firestarter"));
        books.add(new BookEntity(42, "War && Peace"));


        AuthorEntity entity = new AuthorEntity(6, "John", bornOn, books);

        AuthorDto dto = mapper.map(entity, AuthorDto.class);

        return dto;
    }
}
