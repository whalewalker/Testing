package model;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class BookShelfTest {

    BookShelf bookShelf;
    Book effectiveJava;
    Book codeComplete;
    Book mythicalManMonth;
    Book cleanCode;


    @BeforeEach
    void setUp() {
        bookShelf = new BookShelf();
        effectiveJava = new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8));
        codeComplete = new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9));
        mythicalManMonth = new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1));
        cleanCode = new Book("The Clean Code", "Robert C. Martin", LocalDate.of(2008, Month.APRIL, 6));
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

        try {
            books.add(mythicalManMonth);
            fail("Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, "Should\n" +
                    "throw UnsupportedOperationException.");
        }
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
        bookShelf.add(effectiveJava, codeComplete, mythicalManMonth);
        bookShelf.arrange();
        List<Book> books = bookShelf.books();
        assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth), books, "Books in bookshelf are in insertion order");
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
        assertThat(booksByPublishedYear).containsKey(Year.of(2008)).containsValues(Arrays.asList(effectiveJava, cleanCode));

        assertThat(booksByPublishedYear).containsKey(Year.of(2004)).containsValues(Collections.singletonList(codeComplete));

        assertThat(booksByPublishedYear).containsKey(Year.of(1975)).containsValues(Collections.singletonList(mythicalManMonth));
    }

    @Test
    @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
    void groupBooksByUserProvidedCriteria(){
        bookShelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
        Map<String, List<Book>> booksByAuthor = bookShelf.groupBy(Book::getAuthor);

        assertThat(booksByAuthor).containsKey("Joshua Bloch").containsValues(Collections.singletonList(effectiveJava));

        assertThat(booksByAuthor).containsKey("Robert C. Martin").containsValues(Collections.singletonList(effectiveJava));

        assertThat(booksByAuthor).containsKey("Steve McConnel").containsValues(Collections.singletonList(codeComplete));

        assertThat(booksByAuthor).containsKey("Frederick Phillips Brooks").containsValues(Collections.singletonList(mythicalManMonth));

    }

}

