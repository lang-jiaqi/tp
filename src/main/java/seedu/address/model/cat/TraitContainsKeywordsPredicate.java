package seedu.address.model.cat;

import java.util.List;
import java.util.function.Predicate;

/**
 *
 */
public class TraitContainsKeywordsPredicate implements Predicate<Cat> {
    private final List<String> keywords;

    public TraitContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Cat cat) {
        return keywords.stream()
                .anyMatch(keyword -> cat.getTags().stream()
                        .anyMatch(tag -> tag.tagName.equalsIgnoreCase(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TraitContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TraitContainsKeywordsPredicate) other).keywords)); // state check
    }
}
