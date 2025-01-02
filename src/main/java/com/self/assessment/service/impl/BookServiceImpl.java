package com.self.assessment.service.impl;

import com.self.assessment.dao.BookDao;
import com.self.assessment.dto.BookPagination;
import com.self.assessment.exception.ResourceNotFoundException;
import com.self.assessment.model.Book;
import com.self.assessment.repository.BookRepository;
import com.self.assessment.service.BookService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String saveBook(BookDao book) {
        String bookId = UUID.randomUUID().toString();
        bookRepository.insertBook(bookId, book.getTitle(), book.getAuthor(), book.isAvailable());
        return "Book created!!";
    }

    @Override
    public BookPagination bookList(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Book> dbResult = bookRepository.findAll(pageable);
        System.out.println("\n\n" + dbResult + "\n\n");
        return new BookPagination(dbResult.getTotalPages(),
                dbResult.getNumberOfElements(), dbResult.getSize(), dbResult.getContent());
    }

}
