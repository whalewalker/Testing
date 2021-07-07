package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookShelf {
    private final List<String> books;

    public BookShelf() {
        this.books = new ArrayList<>();
    }

    public List<String> books() {
        return books;
    }

    public void add(String... books) {
//        Arrays.stream(books).forEach(book -> this.books.add(book));
//        Arrays.stream(books).forEach(this.books::add);

        this.books.addAll(Arrays.asList(books));
    }
}
