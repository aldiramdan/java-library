package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.BookRequest;
import com.aldiramdan.library.model.dto.response.ResponseBook;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.*;
import com.aldiramdan.library.repository.*;
import com.aldiramdan.library.service.BookService;
import com.aldiramdan.library.validator.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;
    private final GenreRepository genreRepository;
    private final GenreValidator genreValidator;
    private final PublisherRepository publisherRepository;
    private final PublisherValidator publisherValidator;

    @Override
    public ResponseData getAll(Boolean isBorrowed) {
        List<Book> listBook;
        if (Objects.isNull(isBorrowed)) {
            listBook = bookRepository.findAll();
        } else {
            listBook = bookRepository.findByIsBorrowed(isBorrowed);
        }

        List<ResponseBook> listResult = new ArrayList<>();
        for (Book b : listBook) {
            ResponseBook temp = new ResponseBook(b);
            listResult.add(temp);
        }

        return new ResponseData(200, "Success", listResult);
    }

    @Override
    public ResponseData getById(Long id) {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        return new ResponseData(200, "Success", new ResponseBook(findBook.get()));
    }

    @Override
    public ResponseData searchByName(String column, String name) {
        List<Book> listBook = new ArrayList<>();
        if (name.isEmpty()) {
            return new ResponseData(200, "Success", listBook);
        }

        listBook = switch (column) {
            case "title" -> bookRepository.findByTitleContaining(name);
            case "author" -> bookRepository.findAuthorByName(name);
            case "category" -> bookRepository.findCategoryByName(name);
            case "genre" -> bookRepository.findGenreByName(name);
            case "publisher" -> bookRepository.findPublisherByName(name);
            default -> Collections.emptyList();
        };

        List<ResponseBook> listResult = new ArrayList<>();
        for (Book b : listBook) {
            ResponseBook temp = new ResponseBook(b);
            listResult.add(temp);
        }

        return new ResponseData(200, "Success", listResult);
    }

    @Override
    public ResponseData add(BookRequest request) {
        Optional<Author> findAuthor = authorRepository.findById(request.getAuthor());
        authorValidator.validateAuthorNotFound(findAuthor);
        authorValidator.validateAuthorIsAlreadyDeleted(findAuthor);

        Optional<Category> findCategory = categoryRepository.findById(request.getCategory());
        categoryValidator.validateCategoryNotFound(findCategory);
        categoryValidator.validateCategoryIsAlreadyDeleted(findCategory);

        Optional<Genre> findGenre = genreRepository.findById(request.getGenre());
        genreValidator.validateGenreNotFound(findGenre);
        genreValidator.validateCategoryIsAlreadyDeleted(findGenre);

        Optional<Publisher> findPublisher = publisherRepository.findById(request.getPublisher());
        publisherValidator.validatePublisherNotFound(findPublisher);
        publisherValidator.validatePublisherIsAlreadyDeleted(findPublisher);

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(findAuthor.get());
        book.setCategory(findCategory.get());
        book.setGenre(findGenre.get());
        book.setPublisher(findPublisher.get());
        book.setSynopsis(request.getSynopsis());
        bookRepository.save(book);

        return new ResponseData(201, "Success", new ResponseBook(book));
    }

    @Override
    public ResponseData update(Long id, BookRequest request) {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        Optional<Author> findAuthor = authorRepository.findById(request.getAuthor());
        authorValidator.validateAuthorNotFound(findAuthor);

        Optional<Category> findCategory = categoryRepository.findById(request.getCategory());
        categoryValidator.validateCategoryNotFound(findCategory);
        categoryValidator.validateCategoryIsAlreadyDeleted(findCategory);

        Optional<Genre> findGenre = genreRepository.findById(request.getGenre());
        genreValidator.validateGenreNotFound(findGenre);
        genreValidator.validateCategoryIsAlreadyDeleted(findGenre);

        Optional<Publisher> findPublisher = publisherRepository.findById(request.getPublisher());
        publisherValidator.validatePublisherNotFound(findPublisher);

        findBook.get().setTitle(request.getTitle());
        findBook.get().setAuthor(findAuthor.get());
        findBook.get().setCategory(findCategory.get());
        findBook.get().setGenre(findGenre.get());
        findBook.get().setPublisher(findPublisher.get());
        findBook.get().setSynopsis(request.getSynopsis());
        bookRepository.save(findBook.get());

        return new ResponseData(200, "Success", new ResponseBook(findBook.get()));
    }

    @Override
    public ResponseData delete(Long id) {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsAlreadyDeleted(findBook);

        findBook.get().setIsDeleted(true);
        bookRepository.save(findBook.get());

        return new ResponseData(200, "Successfully deleted book", null);
    }

    @Override
    public ResponseData recovery(Long id) {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);
        bookValidator.validateBookIsAlreadyRecovery(findBook);

        findBook.get().setIsDeleted(false);
        bookRepository.save(findBook.get());

        return new ResponseData(200, "Successfully recovery book", null);
    }
}
