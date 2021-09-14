package config;

import model.Book;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BooksParameterResolver implements ParameterResolver {

    private final Map<String, Book> books;

    public BooksParameterResolver() {
        Map<String, Book> books = new HashMap<>();
        books.put("Effective Java", new Book("Effective Java", "Joshua Bloch", LocalDate.of(2008, Month.MAY, 8)));
        books.put("Code Complete",  new Book("Code Complete", "Steve McConnel", LocalDate.of(2004, Month.JUNE, 9)));
        books.put("The Mythical Man-Month", new Book("The Mythical Man-Month", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1)));
        books.put("The Clean Code", new Book("The clean Code", "Frederick Phillips Brooks", LocalDate.of(1975, Month.JANUARY, 1)));
        books.put("Refactoring", new Book("Refactoring", "Martin Fowler", LocalDate.of(2001, Month.NOVEMBER, 8)));
        this.books = books;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        return Objects.equals(parameter.getParameterizedType().getTypeName(),
                "java.util.Map<java.lang.String, model.Book>");
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return books;
    }
}
