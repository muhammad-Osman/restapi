package com.self.assessment.controller;

import com.self.assessment.dto.Post;
import com.self.assessment.model.Book;
import com.self.assessment.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        Book newBook = bookService.saveBook(book);
        System.out.println("\n\n " + newBook + "\n\n");
        return ResponseEntity.ok(newBook);
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getBooksWithPagination(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Page<Book> books = bookService.getAllBooks(PageRequest.of(page, size));
        return ResponseEntity.ok(books);
    }

    @GetMapping("/thirdparty")
    public ResponseEntity<List<Post>> callThirdPartyApi() {
        RestTemplate restTemplate = new RestTemplate();
        Post[] posts = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class);
        List<Post> postList = Arrays.asList(posts);
        return ResponseEntity.ok(postList);
    }
}
