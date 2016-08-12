package complex_types;

import bg.ansr.magicmapper.core.ANSRMagicMapper;
import bg.ansr.magicmapper.core.MagicMapper;
import bg.ansr.magicmapper.core.field.ComplexField;
import bg.ansr.magicmapper.core.field.SimpleField;
import complex_types.models.AuthorDto;
import complex_types.models.AuthorEntity;
import complex_types.models.BookDto;
import complex_types.models.BookEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ComplexTypesMapUsingDelegate {

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

        Assert.assertEquals(12, dto.getBookDto().getId());
    }

    @Test
    public void testComplexFieldIdentity_Book_Name() throws ParseException {
        AuthorDto dto = this.getAuthorDto();

        Assert.assertEquals("Firestarter", dto.getBookDto().getName());
    }

    private AuthorDto getAuthorDto() throws ParseException {
        this.mapper
                .from(BookEntity.class)
                .to(BookDto.class)
                .forRule((entity, dto, magicMapper) -> {
                    dto.setId(entity.getId());
                    dto.setName(entity.getBookName());
                });

        this.mapper
                .from(AuthorEntity.class)
                .to(AuthorDto.class)
                .forRule((entity, dto, magicMapper) -> {
                    dto.setId(entity.getId());
                    dto.setName(entity.getAuthorName());
                    dto.setBornOn(entity.getDate());
                    dto.setBookDto(
                            magicMapper.map(entity.getBookEntity(), BookDto.class)
                    );
                });

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date bornOn = sdf.parse("06.06.2006 03:00:00");

        BookEntity bookEntity = new BookEntity(12, "Firestarter");
        AuthorEntity entity = new AuthorEntity(6, "John", bornOn, bookEntity);

        AuthorDto dto = mapper.map(entity, AuthorDto.class);

        return dto;
    }
}
