package model;

import lombok.Data;

import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Data
public class BookShelf {
    private final List<Book> books;
    private final Progress progress;

    public BookShelf() {
        this.progress = new Progress(0, 100, 0);
        this.books = new ArrayList<>();
    }

    public Progress progress(){
        int booksRead = Long.valueOf(getBooks().stream().filter(Book::isRead).count()).intValue();
        int booksToRead = getBooks().size() - booksRead;

        int percentageCompleted = booksRead * 100 / books.size();
        int percentageToRead = booksToRead * 100 / books.size();

        return new Progress(percentageCompleted, percentageToRead, 0);
    }

    public List<Book> books() {
        return Collections.unmodifiableList(books);
    }

    public void add(Book... books) {
        this.books.addAll(Arrays.asList(books));
    }

    public List<Book> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    public List<Book> arrange(Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
        return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
    }

    public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
        return books.stream().collect(groupingBy(fx));
    }

 public List<Book> findBooksByTitle(String title){
        return findBooksByTitle(title, b -> true);
 }

    List<Book> findBooksByTitle(String title, BookFilter filter) {
        return  getBooks().stream()
                .filter(book -> book.getTitle().contains(title.toLowerCase()))
                .filter(filter::apply).collect(Collectors.toList());
    }
}
