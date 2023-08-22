package com.aldiramdan.library.repository;

import com.aldiramdan.library.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsBorrowed(Boolean isBorrowed);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
