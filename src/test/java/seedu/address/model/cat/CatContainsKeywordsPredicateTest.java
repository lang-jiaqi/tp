package seedu.address.model.cat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CatBuilder;

public class CatContainsKeywordsPredicateTest {

    @Test
    public void test_catMatchesKeywords_returnsTrue() {
        // 1. Match Name only
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("Mochi"), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        assertTrue(predicate.test(new CatBuilder().withName("Mochi").build()));

        // 2. Match multiple fields (Name AND Location AND Trait)
        predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("Mochi"),
                Collections.singletonList("UTown"),
                Collections.singletonList("Calico"),
                Collections.emptyList());
        assertTrue(predicate.test(new CatBuilder()
                .withName("Mochi")
                .withLocation("UTown")
                .withTraits("Calico")
                .build()));

        // 3. Match using keywords with different casing (Case-insensitivity)
        predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("mOcHi"), Collections.emptyList(),
                Collections.singletonList("cAlIcO"), Collections.emptyList());
        assertTrue(predicate.test(new CatBuilder().withName("Mochi").withTraits("Calico").build()));
    }

    @Test
    public void test_catDoesNotMatchKeywords_returnsFalse() {
        // 1. Keywords match some fields but not all (Since we use AND logic)
        // Predicate requires Name "Mochi" AND Location "FOS"
        CatContainsKeywordsPredicate predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("Mochi"),
                Collections.singletonList("FOS"),
                Collections.emptyList(),
                Collections.emptyList());

        // Cat only has the correct name, but wrong location
        assertFalse(predicate.test(new CatBuilder().withName("Mochi").withLocation("UTown").build()));

        // 2. Non-matching keywords
        predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("Luna"), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new CatBuilder().withName("Mochi").build()));

        // 3. Keywords match a trait value, but searching for Name
        predicate = new CatContainsKeywordsPredicate(
                Collections.singletonList("Striped"), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
        assertFalse(predicate.test(new CatBuilder().withName("Mochi").withTraits("Striped").build()));
    }
}
