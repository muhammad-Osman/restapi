package com.self.assessment.repository;

import com.self.assessment.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, String> {
    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO book (id, title, author, available) " +
                    "VALUES (:id, :title, :author, :available)",
            nativeQuery = true
    )
    void insertBook(String id, String title, String author, boolean available);


}
