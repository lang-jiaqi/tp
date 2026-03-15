package seedu.address.model.cat;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Cat}'s {@code Tags} matches any of the keywords given.
 * This is used to filter the cat list based on specific traits (e.g., "friendly", "tabby").
 */
public class TraitContainsKeywordsPredicate implements Predicate<Cat> {
    private final List<String> keywords;

    public TraitContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Cat cat) {
        return keywords.stream()
                .anyMatch(keyword -> cat.getTraits().stream()
                        .anyMatch(trait -> trait.traitName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TraitContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TraitContainsKeywordsPredicate) other).keywords)); // state check
    }
}
