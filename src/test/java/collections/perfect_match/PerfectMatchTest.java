package collections.perfect_match;

import bg.ansr.magicmapper.core.ANSRMagicMapper;
import bg.ansr.magicmapper.core.MagicMapper;
import collections.perfect_match.models.AuthorDto;
import collections.perfect_match.models.AuthorEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class PerfectMatchTest {

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
    public void testFieldIdentity_BookRates() throws ParseException {
        AuthorDto dto = this.getAuthorDto();

        Assert.assertEquals(new Integer(4), dto.getBookRates().get(0));
    }

    private AuthorDto getAuthorDto() throws ParseException {
        this.mapper
                .from(AuthorEntity.class)
                .toFinal(AuthorDto.class);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date bornOn = sdf.parse("06.06.2006 03:00:00");
        AuthorEntity entity = new AuthorEntity(6, "John", bornOn);
        entity.getBookRates().add(4);
        entity.getBookRates().add(6);

        AuthorDto dto = mapper.map(entity, AuthorDto.class);

        return dto;
    }
}
