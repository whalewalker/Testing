package model;

import java.time.LocalDate;

public class BookPublishedYearFilter implements BookFilter{
    private LocalDate startDate;
    private LocalDate preStartDate;

    public static BookFilter After(int year) {
        BookPublishedYearFilter filter = new BookPublishedYearFilter();
        filter.startDate = LocalDate.of(year, 12, 12);
        return filter;
    }

//    public static BookFilter Before(int year) {
//        BookPublishedYearFilter filter = new BookPublishedYearFilter();
//        filter.preStartDate = LocalDate.of(year, 12, 31);
//        return filter;
//    }

    @Override
    public boolean apply(final Book b) {
        return b.getPublishedOn().isAfter(startDate);
    }



}
