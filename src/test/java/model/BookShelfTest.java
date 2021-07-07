package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookShelfTest {

    BookShelf bookShelf;

    @BeforeEach
    void setUp() {
        bookShelf = new BookShelf();
    }

    @AfterEach
    void tearDown() {
        bookShelf = null;
    }

    @Test
    void emptyBookShelfWhenBookNoBookIsAdded(){
        List<String> books = bookShelf.books();
        assertTrue(books.isEmpty(), "BookShelf should be empty");
    }

    @Test
    void bookShelfContainsTwoBooksWhenTwoBooksIsAdded(){
        bookShelf.add("Effective Java");
        bookShelf.add("Code Complete");

        List<String> books = bookShelf.books();
        assertEquals(2, books.size(), "BookShelf should have two books.");
    }
}