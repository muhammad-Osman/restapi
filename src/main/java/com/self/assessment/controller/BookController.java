package com.self.assessment.controller;

import com.self.assessment.dao.BookDao;
import com.self.assessment.dto.BookPagination;
import com.self.assessment.dto.Post;
import com.self.assessment.model.Book;
import com.self.assessment.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createBook(@RequestBody BookDao book) {
        String newBook = bookService.saveBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BookPagination> getBooksWithPagination(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookService.bookList(page, size));
    }

    @GetMapping("/thirdparty")
    public ResponseEntity<List<Post>> callThirdPartyApi() {
        RestTemplate restTemplate = new RestTemplate();
        Post[] posts = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class);
        List<Post> postList = Arrays.asList(posts);
        return ResponseEntity.ok(postList);
    }
}
