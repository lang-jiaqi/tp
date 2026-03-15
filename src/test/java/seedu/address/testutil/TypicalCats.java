package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTH_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_FLUFFY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRAIT_ORANGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.cat.Cat;

/**
 * A utility class containing a list of {@code Cat} objects to be used in tests.
 */
public class TypicalCats {

    public static final Cat BOWIE = new CatBuilder().withName("Bowie")
            .withTraits("Orange").withLocation("Utown").withHealth("Vaccinated").build();
    public static final Cat MOCHI = new CatBuilder().withName("Mochi")
            .withTraits("White", "Fluffy").withLocation("Science").withHealth("Unknown").build();
    public static final Cat LUNA = new CatBuilder().withName("Luna")
            .withTraits("Black").withLocation("PGP").withHealth("Vaccinated").build();
    public static final Cat KIKI = new CatBuilder().withName("Kiki")
            .withTraits("Tabby", "short tail").withLocation("SDE").withHealth("Healthy").build();
    public static final Cat PIPI = new CatBuilder().withName("Pipi")
            .withTraits("Calico").withLocation("Biz").withHealth("Unknown").build();
    public static final Cat NEMO = new CatBuilder().withName("Nemo")
            .withTraits("Orange", "striped").withLocation("CLB").withHealth("Vaccinated").build();
    public static final Cat SIMBA = new CatBuilder().withName("Simba")
            .withTraits("Brown").withLocation("FASS").withHealth("Unknown").build();

    // Manually added
    public static final Cat FELIX = new CatBuilder().withName("Felix")
            .withTraits("Grey").withLocation("UTown").withHealth("Unknown").build();
    public static final Cat OSCAR = new CatBuilder().withName("Oscar")
            .withTraits("Black", "White").withLocation("Medicine").withHealth("Unknown").build();

    // Manually added - Cat's details found in {@code CommandTestUtil}
    public static final Cat AMY = new CatBuilder().withName(VALID_NAME_AMY)
            .withTraits(VALID_TRAIT_ORANGE).withLocation(VALID_LOCATION_AMY).withHealth(VALID_HEALTH_AMY).build();
    public static final Cat BOB = new CatBuilder().withName(VALID_NAME_BOB)
            .withTraits(VALID_TRAIT_FLUFFY, VALID_TRAIT_ORANGE).withLocation(VALID_LOCATION_BOB)
            .withHealth(VALID_HEALTH_BOB).build();

    public static final String KEYWORD_MATCHING_MOCHI = "Mochi"; // A keyword that matches MOCHI

    private TypicalCats() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical cats.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Cat cat : getTypicalCats()) {
            ab.addCat(cat);
        }
        return ab;
    }

    public static List<Cat> getTypicalCats() {
        return new ArrayList<>(Arrays.asList(BOWIE, MOCHI, LUNA, KIKI, PIPI, NEMO, SIMBA));
    }
}
