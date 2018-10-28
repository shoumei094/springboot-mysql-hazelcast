package lol.shomei.service;

import lol.shomei.entity.Book;
import lol.shomei.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unused")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public long addBook(String title) {
        if (StringUtils.isEmpty(title)) {
            throw new RuntimeException("title cannot be null");
        }

        if (title.length() > 200) {
            throw new RuntimeException("title is too long");
        }

        return bookRepository.addBook(title);
    }

    @CachePut(value = "book", key = "#id")
    public Book updateBook(long id, String title) {
        if (StringUtils.isEmpty(title)) {
            throw new RuntimeException("title cannot be null");
        }

        if (title.length() > 200) {
            throw new RuntimeException("title is too long");
        }

        bookRepository.updateBook(id, title);

        Book book = bookRepository.getBookById(id);

        if (book == null) {
            throw new RuntimeException("update failed");
        }

        return book;
    }

    @Cacheable("book")
    public Book getBookById(long id) {
        return bookRepository.getBookById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }
}
