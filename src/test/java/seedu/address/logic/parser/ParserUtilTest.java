package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_TRAIT = " ";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_LOCATION = "Utown";
    private static final String VALID_HEALTH = "Vaccinated";
    private static final String VALID_TRAIT_1 = "Orange";
    private static final String VALID_TRAIT_2 = "Fluffy";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((String) null));
    }

    @Test
    public void parseLocation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocation(INVALID_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithoutWhitespace_returnsLocation() throws Exception {
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(VALID_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithWhitespace_returnsTrimmedLocation() throws Exception {
        String locationWithWhitespace = WHITESPACE + VALID_LOCATION + WHITESPACE;
        Location expectedLocation = new Location(VALID_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(locationWithWhitespace));
    }

    @Test
    public void parseHealth_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHealth((String) null));
    }

    @Test
    public void parseHealth_validValueWithoutWhitespace_returnsHealth() throws Exception {
        Health expectedHealth = new Health(VALID_HEALTH);
        assertEquals(expectedHealth, ParserUtil.parseHealth(VALID_HEALTH));
    }

    @Test
    public void parseHealth_validValueWithWhitespace_returnsTrimmedHealth() throws Exception {
        String healthWithWhitespace = WHITESPACE + VALID_HEALTH + WHITESPACE;
        Health expectedHealth = new Health(VALID_HEALTH);
        assertEquals(expectedHealth, ParserUtil.parseHealth(healthWithWhitespace));
    }

    @Test
    public void parseTrait_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTrait(null));
    }

    @Test
    public void parseTrait_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTrait(INVALID_TRAIT));
    }

    @Test
    public void parseTrait_validValueWithoutWhitespace_returnsTrait() throws Exception {
        Trait expectedTrait = new Trait(VALID_TRAIT_1);
        assertEquals(expectedTrait, ParserUtil.parseTrait(VALID_TRAIT_1));
    }

    @Test
    public void parseTrait_validValueWithWhitespace_returnsTrimmedTrait() throws Exception {
        String traitWithWhitespace = WHITESPACE + VALID_TRAIT_1 + WHITESPACE;
        Trait expectedTrait = new Trait(VALID_TRAIT_1);
        assertEquals(expectedTrait, ParserUtil.parseTrait(traitWithWhitespace));
    }

    @Test
    public void parseTraits_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTraits(null));
    }

    @Test
    public void parseTraits_collectionWithInvalidTraits_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTraits(Arrays.asList(VALID_TRAIT_1, INVALID_TRAIT)));
    }

    @Test
    public void parseTraits_emptyCollection_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseTraits(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTraits_collectionWithValidTraits_returnsTraitList() throws Exception {
        List<Trait> actualTraitList = ParserUtil.parseTraits(Arrays.asList(VALID_TRAIT_1, VALID_TRAIT_2));
        List<Trait> expectedTraitList = Arrays.asList(new Trait(VALID_TRAIT_1), new Trait(VALID_TRAIT_2));
        assertEquals(expectedTraitList, actualTraitList);
    }
}
