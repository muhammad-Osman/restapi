package com.self.assessment.service;

import com.self.assessment.dao.BookDao;
import com.self.assessment.dto.BookPagination;
import com.self.assessment.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    public String saveBook(BookDao book);
    public BookPagination bookList(int pageNo, int pageSize);
}
