package lol.shomei.controller;

import lol.shomei.entity.Book;
import lol.shomei.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/book")
@SuppressWarnings("unused")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(path = "/add")
    public void addBook(@RequestParam(name = "title") String title) {
        bookService.addBook(title);
    }

    @GetMapping(path = "/update/{id}")
    public void updateBook(@PathVariable long id, @RequestParam(name = "title") String title) {
        bookService.updateBook(id, title);
    }

    @GetMapping(path = "/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    @GetMapping(path = "/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
