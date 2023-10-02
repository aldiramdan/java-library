package com.aldiramdan.library.model.dto.response;

import com.aldiramdan.library.model.entity.Book;
import lombok.Data;

@Data
public class ResponseBook {
    private Long id;
    private String title;
    private String authorName;
    private String categoryName;
    private String genreName;
    private String publisherName;
    private Boolean isBorrowed;

    public ResponseBook(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.authorName = book.getAuthor().getName();
        this.categoryName = book.getCategory().getName();
        this.genreName = book.getGenre().getName();
        this.publisherName = book.getPublisher().getName();
        this.isBorrowed = book.getIsBorrowed();
    }
}
