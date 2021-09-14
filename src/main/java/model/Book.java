package model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Book implements Comparable<Book>{
    private final String title;
    private final String author;
    private final LocalDate publishedOn;
    private LocalDate startedReadingOn;
    private LocalDate finishedReadingOn;
    private String id;


    public Book(String title, String author, LocalDate publishedOn) {
        this.title = title.toLowerCase();
        this.author = author;
        this.publishedOn = publishedOn;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public int compareTo(Book book) {
        return title.compareTo(book.title);
    }

    public void startedReadingOn(LocalDate startedOn) {
        this.startedReadingOn = startedOn;
    }

    public void finishedReadingOn(LocalDate finishedOn) {
        this.finishedReadingOn = finishedOn;
    }

    public boolean isRead() {
        return startedReadingOn != null && finishedReadingOn != null;
    }
}
