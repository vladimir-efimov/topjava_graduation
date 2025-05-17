package ru.javawebinar.topjavagraduation.utils;

import ru.javawebinar.topjavagraduation.model.AbstractBaseEntity;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


public class Matcher <T> {

    private final String[] fieldsToIgnore;
    private final String[] fieldsToIgnoreForCollection;
    private final String[] fieldsToCompareWithEquals;

    public Matcher(List<String> fieldsToIgnore) {
        this(fieldsToIgnore, fieldsToIgnore, List.of());
    }

    public Matcher(List<String> fieldsToIgnore, List<String> fieldsToIgnoreForCollection,
                   List<String> fieldsToCompareWithEquals) {
        this.fieldsToIgnore = fieldsToIgnore.toArray(new String[0]);
        this.fieldsToIgnoreForCollection = fieldsToIgnoreForCollection.toArray(new String[0]);
        this.fieldsToCompareWithEquals = fieldsToCompareWithEquals.toArray(new String[0]);
    }

    public void assertMatch(T expected, T actual) {
        Matcher.assertMatch(expected, actual, fieldsToIgnore, fieldsToCompareWithEquals);
    }

    public void assertMatch(List<T> expected, List<T> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for(int i = 0; i < expected.size(); i++) {
            Matcher.assertMatch(expected.get(i), actual.get(i), fieldsToIgnoreForCollection,
                    fieldsToCompareWithEquals);
        }
    }

    private static <T> void assertMatch(T expected, T actual, String[] fieldsToIgnore,
                                        String[] fieldsToCompareWithEquals) {
        assertThat(actual).usingRecursiveComparison()
                .withComparatorForFields(
                        (a,e) -> e.equals(a) ? 0 : 1
                        ,fieldsToCompareWithEquals)
                .ignoringFields(fieldsToIgnore).isEqualTo(expected);
    }
}
