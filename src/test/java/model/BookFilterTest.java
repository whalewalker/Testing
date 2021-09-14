package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BookFilterTest {
    private Book cleanCode;
    private Book codeComplete;

    @BeforeEach
    void init() {
        cleanCode = new Book("Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.AUGUST, 1));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
    }

    @Nested
    @DisplayName("book published date")
    class BookPublishedFilter {
        @Test
        @DisplayName("is after specified year")
        void validateBookPublishedDatePostAskedYear() {
            BookFilter filter = BookPublishedYearFilter.After(2007);
            assertThat(filter).isNotNull();
            assertTrue(filter.apply(cleanCode));
            assertFalse(filter.apply(codeComplete));
        }

        @Test
        @DisplayName("is before specified year")
        void validateBookPublishedDateBeforePostAskedYear() {
            BookFilter filter = BookPublishedYearFilter.Before(2007);
            assertThat(filter).isNotNull();
            assertFalse(filter.apply(cleanCode));
            assertTrue(filter.apply(codeComplete));
        }

        @Test
        @DisplayName("composite criteria is based on multiple filters")
        void shouldFilterOnMultiplesCriteria() {
            CompositeFilter compositeFilter = new CompositeFilter();
            final Map<Integer, Boolean> invocationMap = new HashMap<>();
            compositeFilter.addFilter(b -> {
                invocationMap.put(1, true);
                return true;
            });
            assertTrue(compositeFilter.apply(cleanCode));
        }

        @DisplayName("composite criteria does not invoke after first failure")
        @Test
        void shouldNotInvokeAfterFirstFailure(){
            CompositeFilter compositeFilter = new CompositeFilter();
            compositeFilter.addFilter( b -> false);
            compositeFilter.addFilter( b -> true);
            assertFalse(compositeFilter.apply(cleanCode));
        }
    }
}
