package name_differences;

import bg.ansr.magicmapper.core.ANSRMagicMapper;
import bg.ansr.magicmapper.core.MagicMapper;
import bg.ansr.magicmapper.core.field.SimpleField;
import name_differences.models.AuthorDto;
import name_differences.models.AuthorEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class NameDifferencesFieldNameAsStringTest {

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

    private AuthorDto getAuthorDto() throws ParseException {
        this.mapper
                .from(AuthorEntity.class)
                .to(AuthorDto.class)
                .forRule(
                        new SimpleField("id", "id"),
                        new SimpleField("authorName", "name"),
                        new SimpleField("date", "bornOn")
                );

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date bornOn = sdf.parse("06.06.2006 03:00:00");
        AuthorEntity entity = new AuthorEntity(6, "John", bornOn);
        AuthorDto dto = mapper.map(entity, AuthorDto.class);

        return dto;
    }
}
