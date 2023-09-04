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

    private ResponseData responseData;

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

        return responseData = new ResponseData(200, "Success", listResult);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        ResponseBook result = new ResponseBook(findBook.get());
        return responseData = new ResponseData(200, "Success", result);
    }

    @Override
    public ResponseData searchByName(String column, String name) {
        List<Book> listBook = new ArrayList<>();
        if (name.isEmpty()) {
            return responseData = new ResponseData(200, "Success", listBook);
        }

        switch (column) {
            case "title":
                listBook = bookRepository.findByTitleContaining(name);
                break;
            case "author":
                listBook = bookRepository.findAuthorByName(name);
                break;
            case "category":
                listBook = bookRepository.findCategoryByName(name);
                break;
            case "genre":
                listBook = bookRepository.findGenreByName(name);
                break;
            case "publisher":
                listBook = bookRepository.findPublisherByName(name);
                break;
            default:
                listBook = Collections.emptyList();
        }

        List<ResponseBook> listResult = new ArrayList<>();
        for (Book b : listBook) {
            ResponseBook temp = new ResponseBook(b);
            listResult.add(temp);
        }

        return responseData = new ResponseData(200, "Success", listResult);
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

        ResponseBook result = new ResponseBook(book);
        return responseData = new ResponseData(201, "Success", result);
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

        Book book = findBook.get();
        book.setTitle(request.getTitle());
        book.setAuthor(findAuthor.get());
        book.setCategory(findCategory.get());
        book.setGenre(findGenre.get());
        book.setPublisher(findPublisher.get());
        book.setSynopsis(request.getSynopsis());

        bookRepository.save(book);

        ResponseBook result = new ResponseBook(book);
        return responseData = new ResponseData(200, "Success", result);
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        Book book = findBook.get();
        bookValidator.validateBookIsAlreadyDeleted(book);

        book.setIsDeleted(true);

        bookRepository.save(book);

        return responseData = new ResponseData(200, "Successfully deleted book", null);
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Book> findBook = bookRepository.findById(id);
        bookValidator.validateBookNotFound(findBook);

        Book book = findBook.get();

        book.setIsDeleted(false);

        bookRepository.save(book);

        return responseData = new ResponseData(200, "Successfully recovery book", null);
    }
}
