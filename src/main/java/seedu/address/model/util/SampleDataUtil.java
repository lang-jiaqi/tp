package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.cat.Cat;
import seedu.address.model.cat.Health;
import seedu.address.model.cat.Location;
import seedu.address.model.cat.Name;
import seedu.address.model.cat.Trait;

/**
 * Contains utility methods for populating {@code AddressBook} with sample cat data.
 */
public class SampleDataUtil {

    /**
     * Returns an array of sample {@code Cat} objects.
     */
    public static Cat[] getSampleCats() {
        return new Cat[] {
            new Cat(new Name("Bowie"), getTraitList("Orange"), new Location("Utown"),
                new Health("Vaccinated")),
            new Cat(new Name("Mochi"), getTraitList("White", "Fluffy"), new Location("Science"),
                new Health("Unknown")),
            new Cat(new Name("Luna"), getTraitList("Black"), new Location("PGP"),
                new Health("Vaccinated")),
            new Cat(new Name("Kiki"), getTraitList("Tabby", "short tail"), new Location("SDE"),
                new Health("Healthy")),
            new Cat(new Name("Pipi"), getTraitList("Calico"), new Location("Biz"),
                new Health("Unknown")),
            new Cat(new Name("Simba"), getTraitList("Brown"), new Location("FASS"),
                new Health("Unknown"))
        };
    }

    /**
     * Returns a sample {@code ReadOnlyAddressBook} pre-populated with sample cat data.
     */
    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Cat sampleCat : getSampleCats()) {
            sampleAb.addCat(sampleCat);
        }
        return sampleAb;
    }

    /**
     * Returns a trait list containing the list of strings given.
     */
    public static List<Trait> getTraitList(String... strings) {
        return Arrays.stream(strings)
                .map(Trait::new)
                .collect(Collectors.toList());
    }

}
