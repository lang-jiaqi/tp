package seedu.address.model.cat;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests if a cat matches search keywords by name, location, trait, and health.
 */
public class CatContainsKeywordsPredicate implements Predicate<Cat> {
    private final List<String> nameKeywords;
    private final List<String> locationKeywords;
    private final List<String> traitKeywords;
    private final List<String> healthKeywords;

    /**
     * Creates a predicate that matches cats against the given keyword groups.
     * Empty keyword lists are treated as "no filter" for that category.
     *
     * @param nameKeywords keywords to match against a cat's name
     * @param locationKeywords keywords to match against a cat's location
     * @param traitKeywords keywords to match against a cat's traits
     * @param healthKeywords keywords to match against a cat's health status
     */
    public CatContainsKeywordsPredicate(List<String> nameKeywords, List<String> locationKeywords,
                                        List<String> traitKeywords, List<String> healthKeywords) {
        this.nameKeywords = nameKeywords;
        this.locationKeywords = locationKeywords;
        this.traitKeywords = traitKeywords;
        this.healthKeywords = healthKeywords;
    }


    @Override
    public boolean test(Cat cat) {
        boolean matchesName = nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> cat.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));

        boolean matchesLocation = locationKeywords.isEmpty() || locationKeywords.stream()
                .anyMatch(keyword -> cat.getLocation().value.toLowerCase().contains(keyword.toLowerCase()));

        boolean matchesTraits = traitKeywords.isEmpty() || traitKeywords.stream()
                .anyMatch(keyword -> cat.getTraits().stream()
                        .anyMatch(trait -> trait.traitName.toLowerCase().contains(keyword.toLowerCase())));

        boolean matchesHealth = healthKeywords.isEmpty() || healthKeywords.stream()
                .anyMatch(keyword -> cat.getHealth().value.toLowerCase().contains(keyword.toLowerCase()));

        // Returns true only if the cat matches ALL specified criteria (AND logic)
        return matchesName && matchesLocation && matchesTraits && matchesHealth;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CatContainsKeywordsPredicate)) {
            return false;
        }

        CatContainsKeywordsPredicate otherPredicate = (CatContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && locationKeywords.equals(otherPredicate.locationKeywords)
                && traitKeywords.equals(otherPredicate.traitKeywords)
                && healthKeywords.equals(otherPredicate.healthKeywords);
    }

    @Override
    public int hashCode() {
        return nameKeywords.hashCode() + locationKeywords.hashCode() + traitKeywords.hashCode()
                + healthKeywords.hashCode();
    }
}
