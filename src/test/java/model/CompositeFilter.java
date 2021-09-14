package model;

import java.util.ArrayList;
import java.util.List;

public class CompositeFilter implements BookFilter {
    private final List<BookFilter> filters;

    public CompositeFilter() {
        this.filters = new ArrayList<>();
    }

    public void addFilter(BookFilter filter) {
        filters.add(filter);
    }

    @Override
    public boolean apply(Book b) {
        return filters.stream().map(bookFilter -> bookFilter.apply(b))
                .reduce(true, (b1, b2) -> b1 && b2);
    }

}
