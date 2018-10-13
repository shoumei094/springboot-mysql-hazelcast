package lol.shomei.service;

import lol.shomei.entity.Book;

import java.util.List;

public interface BookService {

    long addBook(String title);

    Book getBookById(long id);

    List<Book> getAllBooks();
}
