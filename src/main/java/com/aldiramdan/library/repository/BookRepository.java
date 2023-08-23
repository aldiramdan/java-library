package com.aldiramdan.library.repository;

import com.aldiramdan.library.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsBorrowed(Boolean isBorrowed);

    List<Book> findByTitleContaining(String title);

    @Query("""
            select b from Book b inner join Author a\s
            on b.author.id = a.id\s
            where a.name like %:name%\s
            """)
    List<Book> findByAuthorName(String name);

    @Query("""
            select b from Book b inner join Genre g\s
            on b.genre.id = g.id\s
            where g.name like %:name%\s
            """)
    List<Book> findByGenreName(String name);

    @Query("""
            select b from Book b inner join Category c\s
            on b.category.id = c.id\s
            where c.name like %:name%\s
            """)
    List<Book> findByCategoryName(String name);

    @Query("""
            select b from Book b inner join Publisher p\s
            on b.publisher.id = p.id\s
            where p.name like %:name%\s
            """)
    List<Book> findByPublisherName(String name);
}
