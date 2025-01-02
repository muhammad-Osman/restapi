package com.self.assessment.service;

import com.self.assessment.dao.BookDao;
import com.self.assessment.dto.BookPagination;
import com.self.assessment.model.Book;
import com.self.assessment.repository.BookRepository;
import com.self.assessment.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test saveBook
    @Test
    void testSaveBook() {
        // Arrange
        BookDao bookDao = new BookDao();
        bookDao.setTitle("Test Book");
        bookDao.setAuthor("Test Author");
        bookDao.setAvailable(true);

        doNothing().when(bookRepository).insertBook(anyString(), anyString(), anyString(), anyBoolean());

        // Act
        String result = bookService.saveBook(bookDao);

        // Assert
        assertEquals("Book created!!", result);
        verify(bookRepository, times(1))
                .insertBook(anyString(), eq("Test Book"), eq("Test Author"), eq(true));
    }

    // ✅ Test bookList
    @Test
    void testBookList() {
        // Arrange
        Book book1 = new Book();
        book1.setId(UUID.randomUUID().toString());
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setId(UUID.randomUUID().toString());
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setAvailable(false);

        List<Book> bookList = List.of(book1, book2);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> page = new PageImpl<>(bookList, pageable, bookList.size());

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(page);

        // Act
        BookPagination result = bookService.bookList(0, 10);

        // Assert
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());
        assertEquals(10, result.getSize());
        assertEquals(2, result.getContent().size());
        assertEquals("Book 1", result.getContent().get(0).getTitle());

        verify(bookRepository, times(1)).findAll(any(Pageable.class));
    }
}
