package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.BookRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.*;
import com.aldiramdan.library.repository.*;
import com.aldiramdan.library.service.BookService;
import com.aldiramdan.library.validator.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorValidator authorValidator;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreValidator genreValidator;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherValidator publisherValidator;

    private ResponseData responseData;

    @Override
    public ResponseData getAll(Boolean isBorrowed) {
        List<Book> listBook;
        if (Objects.isNull(isBorrowed)) {
            listBook = bookRepository.findAll();
        } else {
            listBook = bookRepository.findByIsBorrowed(isBorrowed);
        }

        responseData = new ResponseData(200, "Success", listBook);
        return responseData;
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        Book book = findBook.get();

        responseData = new ResponseData(200, "Success", book);
        return responseData;
    }

    @Override
    public ResponseData getByTitle(String title) {
        List<Book> listBook = new ArrayList<>();
        if (title.isEmpty()) {
            responseData = new ResponseData(200, "Success", listBook);
            return responseData;
        }

        listBook = bookRepository.findByTitleContaining(title);

        responseData = new ResponseData(200, "Success", listBook);
        return responseData;
    }

    @Override
    public ResponseData add(BookRequest request) throws Exception {
        Optional<Author> findAuthor = authorRepository.findById(request.getAuthor());
        authorValidator.validateAuthorNotFound(findAuthor);
        authorValidator.validateAuthorIsAlreadyDeleted(findAuthor.get());

        Optional<Category> findCategory = categoryRepository.findById(request.getCategory());
        categoryValidator.validateCategoryNotFound(findCategory);
        categoryValidator.validateCategoryIsAlreadyDeleted(findCategory.get());

        Optional<Genre> findGenre = genreRepository.findById(request.getGenre());
        genreValidator.validateGenreNotFound(findGenre);
        genreValidator.validateCategoryIsAlreadyDeleted(findGenre.get());

        Optional<Publisher> findPublisher = publisherRepository.findById(request.getPublisher());
        publisherValidator.validatePublisherNotFound(findPublisher);
        publisherValidator.validatePublisherIsAlreadyDeleted(findPublisher.get());

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(findAuthor.get());
        book.setCategory(findCategory.get());
        book.setGenre(findGenre.get());
        book.setPublisher(findPublisher.get());
        book.setSynopsis(request.getSynopsis());

        bookRepository.save(book);

        responseData = new ResponseData(201, "Success", book);
        return responseData;
    }

    @Override
    public ResponseData update(Long id, BookRequest request) throws Exception {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        Optional<Author> findAuthor = authorRepository.findById(request.getAuthor());
        authorValidator.validateAuthorNotFound(findAuthor);

        Optional<Category> findCategory = categoryRepository.findById(request.getCategory());
        categoryValidator.validateCategoryNotFound(findCategory);
        categoryValidator.validateCategoryIsAlreadyDeleted(findCategory.get());

        Optional<Genre> findGenre = genreRepository.findById(request.getGenre());
        genreValidator.validateGenreNotFound(findGenre);
        genreValidator.validateCategoryIsAlreadyDeleted(findGenre.get());

        Optional<Publisher> findPublisher = publisherRepository.findById(request.getPublisher());
        publisherValidator.validatePublisherNotFound(findPublisher);

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(findAuthor.get());
        book.setCategory(findCategory.get());
        book.setGenre(findGenre.get());
        book.setPublisher(findPublisher.get());
        book.setSynopsis(request.getSynopsis());

        bookRepository.save(book);

        responseData = new ResponseData(200, "Success", book);
        return responseData;
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        Book book = findBook.get();
        bookValidator.validateBookIsAlreadyDeleted(book);

        book.setIsDeleted(true);

        bookRepository.save(book);

        responseData = new ResponseData(200, "Success", null);
        return responseData;
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        Book book = findBook.get();

        book.setIsDeleted(false);

        bookRepository.save(book);

        responseData = new ResponseData(200, "Success", null);
        return responseData;
    }
}
