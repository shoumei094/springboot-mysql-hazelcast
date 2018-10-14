package lol.shomei.repository;

import lol.shomei.entity.Book;

import java.util.List;

public interface BookRepository {

    long addBook(String title);

    long updateBook(long id, String title);

    Book getBookById(long id);

    List<Book> getAllBooks();
}
