package model;

import config.BooksParameterResolver;
import org.assertj.core.api.Java6Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A book shelf")
@ExtendWith(BooksParameterResolver.class)
class BookShelfTest {
    BookShelf bookShelf;
    Book effectiveJava;
    Book codeComplete;
    Book mythicalManMonth;
    Book cleanCode;
    Book refactoring;


    @BeforeEach
    void setUp(Map<String, Book> books) {
        bookShelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("The Clean Code");
        this.refactoring = books.get("Refactoring");
        bookShelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode, refactoring);
    }

    @AfterEach
    void tearDown() {
        bookShelf = null;
        effectiveJava = null;
        codeComplete = null;
        mythicalManMonth = null;
    }

    /*FIRST FEATURE
    *
    * As a user, I want to add multiple books to my bookshelf so that I can read
       them later.
    * */

    @Test
    @DisplayName("bookShelf is empty when no book is added to it")
    void emptyBookShelfWhenNoBookIsAdded() {
        List<Book> books = bookShelf.books();
        assertTrue(books.isEmpty(), "BookShelf should be empty");
    }

    @Test
    @DisplayName("bookShelf contain two books when two books is added to it")
    void bookShelfContainsTwoBooksWhenTwoBooksIsAdded() {
        bookShelf.add(effectiveJava, codeComplete);
        List<Book> books = bookShelf.books();
        assertEquals(2, books.size(), "BookShelf should have two books.");
    }

    @Test
    @DisplayName("empty bookShelf remains empty when add is called without any books")
    void emptyBookShelfWhenAddMethodIsCalledWithoutBooks() {
        bookShelf.add();
        List<Book> books = bookShelf.books();
        assertTrue(books.isEmpty(), "BookShelf should be empty");
    }

    @Test
    @DisplayName("bookShelf returns an immutable books collection to client")
    void booksReturnedFromBookShelfIsImmutableForClient() {
        bookShelf.add(effectiveJava, codeComplete);
        List<Book> books = bookShelf.books();
        assertThrows(UnsupportedOperationException.class, () -> books.add(mythicalManMonth));
    }

    /*SECOND FEATURE
    *
    * As a user, I should be able to arrange my bookshelf based on certain
        criteria
    * */

    @Test
    @DisplayName("bookshelf is arranged by user provided criteria (by book title lexicographically by default")
    void bookShelfArrangeBookByTitle() {
        bookShelf.add(effectiveJava, codeComplete, mythicalManMonth);
        List<Book> books = bookShelf.arrange();
        assertEquals(Arrays.asList(codeComplete, effectiveJava, mythicalManMonth), books);
    }

    @Test
    @DisplayName("books in bookshelf are in order of insertion after arrange is called")
    void booksInBookShelfAreInsertionOrderAfterCallingArrangeMethod() {
        bookShelf.add(codeComplete, mythicalManMonth, effectiveJava);
        bookShelf.arrange();
        List<Book> books = bookShelf.books();
        assertEquals(Arrays.asList(codeComplete, mythicalManMonth, effectiveJava), books, "Books in bookshelf are in insertion order");
    }

    @Test
    @DisplayName("books inside bookShelf are arranged according to user provider criteria (in a reversed order)")
    void bookShelfArrangedByUserProvidedCriteria() {
        bookShelf.add(effectiveJava, codeComplete, mythicalManMonth);
        Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
        List<Book> books = bookShelf.arrange(reversed);
        assertThat(books).isSortedAccordingTo(reversed);
    }

    /* Third Feature
    As a user, I should be able to group books in my bookshelf based on
        certain criteria
 */
    @Test
    @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by year published)")
    void groupBooksInsideBookShelfByPublicationYear(){
        bookShelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);

        Map<Year, List<Book>> booksByPublishedYear = bookShelf.groupByPublicationYear();
        assertThat(booksByPublishedYear).containsKey(Year.of(2008)).containsValues(List.of(effectiveJava));

        assertThat(booksByPublishedYear).containsKey(Year.of(2004)).containsValues(Collections.singletonList(codeComplete));

        assertThat(booksByPublishedYear).containsKey(Year.of(1975)).containsValues(Arrays.asList(mythicalManMonth, cleanCode));
    }

    @Test
    @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
    void groupBooksByUserProvidedCriteria(){
        bookShelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
        Map<String, List<Book>> booksByAuthor = bookShelf.groupBy(Book::getAuthor);

        assertThat(booksByAuthor).containsKey("Joshua Bloch").containsValues(Collections.singletonList(effectiveJava));

        assertThat(booksByAuthor).containsKey("Steve McConnel").containsValues(Collections.singletonList(codeComplete));

        assertThat(booksByAuthor).containsKey("Frederick Phillips Brooks").containsValues(Arrays.asList(mythicalManMonth, cleanCode));

    }

    @Nested
    @DisplayName("shelf book progress")
    class BookShelfProgress {

        @Test
        @DisplayName("is 0% completed and 100% to-read when no book is read yet")
        void progress100PercentageUnread() {
            Progress progress = bookShelf.progress();
            Java6Assertions.assertThat(progress.getCompleted()).isEqualTo(0);
            Java6Assertions.assertThat(progress.getToRead()).isEqualTo(100);
        }

        @Test
        @DisplayName("is 40% completed and 60% to-read when 2 books are finish and 3 books are yet to be read")
        void progressWithCompletedAndToReadPercentage() {
            effectiveJava.startedReadingOn(LocalDate.of(2016, Month.JULY, 1));
            effectiveJava.finishedReadingOn(LocalDate.of(2016, Month.JULY, 31));
            cleanCode.startedReadingOn(LocalDate.of(2016, Month.AUGUST, 1));
            cleanCode.finishedReadingOn(LocalDate.of(2016, Month.AUGUST, 31));
            Progress progress = bookShelf.progress();
            Java6Assertions.assertThat(progress.getCompleted()).isEqualTo(40);
            Java6Assertions.assertThat(progress.getToRead()).isEqualTo(60);
        }
    }

    @Nested
    @DisplayName("Search")
    class BookShelfSearch {

        @Test
        @DisplayName("should find books with title containing text and published after specified date")
        void shouldFindBooksWithTitleContainingText() {
            List<Book> books = bookShelf.findBooksByTitle("Code", b -> b.getPublishedOn().isBefore(LocalDate.of(2014, Month.DECEMBER, 31)));
            assertThat(books.size()).isEqualTo(2);
        }



    }
    
}

