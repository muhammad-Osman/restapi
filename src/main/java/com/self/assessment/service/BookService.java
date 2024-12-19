package com.self.assessment.service;

import com.self.assessment.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    public Book saveBook(Book book);

    public Page<Book> getAllBooks(Pageable pageable);
}
