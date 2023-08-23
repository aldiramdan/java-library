package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.AuthorRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.Author;
import com.aldiramdan.library.repository.AuthorRepository;
import com.aldiramdan.library.service.AuthorService;
import com.aldiramdan.library.validator.AuthorValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorValidator authorValidator;

    private ResponseData responseData;

    @Override
    public ResponseData getAll() {
        List<Author> listAuthor = authorRepository.findAll();

        return responseData = new ResponseData(200, "Success", listAuthor);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);

        return responseData = new ResponseData(200, "Success", findAuthor);
    }

    @Override
    public ResponseData add(AuthorRequest request) throws Exception {
        Optional<Author> findAuthorByName = authorRepository.findByName(request.getName());
        authorValidator.validateAuthorIsExists(findAuthorByName);

        Author author = new Author();
        author.setName(request.getName());

        authorRepository.save(author);

        return responseData = new ResponseData(201, "Success", author);
    }

    @Override
    public ResponseData update(Long id, AuthorRequest request) throws Exception {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);

        Author author = findAuthor.get();
        if (author.getName() != request.getName()) {
            Optional<Author> findAuthorByName = authorRepository.findByName(request.getName());
            authorValidator.validateAuthorIsExists(findAuthorByName);

            author.setName(request.getName());
        }
        author.setName(author.getName());

        authorRepository.save(author);

        return responseData = new ResponseData(200, "Success", author);
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);

        Author author = findAuthor.get();
        authorValidator.validateAuthorIsAlreadyDeleted(author);

        author.setIsDeleted(true);

        authorRepository.save(author);

        return responseData = new ResponseData(200, "Successfully deleted author", null);
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);

        Author author = findAuthor.get();
        authorValidator.validateAuthorIsAlreadyDeleted(author);

        author.setIsDeleted(false);

        authorRepository.save(author);

        return responseData = new ResponseData(200, "Successfully recovery author", null);
    }
}
