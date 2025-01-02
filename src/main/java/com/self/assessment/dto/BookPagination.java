package com.self.assessment.dto;

import com.self.assessment.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookPagination{
    Integer totalPages;
    Integer totalElements;
    Integer size;
    List<Book> content;
}