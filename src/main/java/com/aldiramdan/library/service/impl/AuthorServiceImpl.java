package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.AuthorRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.Author;
import com.aldiramdan.library.repository.AuthorRepository;
import com.aldiramdan.library.service.AuthorService;
import com.aldiramdan.library.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;

    @Override
    public ResponseData getAll() {
        List<Author> listAuthor = authorRepository.findAll();

        return new ResponseData(200, "Success", listAuthor);
    }

    @Override
    public ResponseData getById(Long id) {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);

        return new ResponseData(200, "Success", findAuthor);
    }

    @Override
    public ResponseData add(AuthorRequest request) {
        Optional<Author> findAuthorByName = authorRepository.findByName(request.getName());
        authorValidator.validateAuthorIsExists(findAuthorByName);

        Author author = new Author();
        author.setName(request.getName());
        authorRepository.save(author);

        return new ResponseData(201, "Success", author);
    }

    @Override
    public ResponseData update(Long id, AuthorRequest request) {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);

        if (!findAuthor.get().getName().equals(request.getName())) {
            Optional<Author> findAuthorByName = authorRepository.findByName(request.getName());
            authorValidator.validateAuthorIsExists(findAuthorByName);
            findAuthor.get().setName(request.getName());
        }
        authorRepository.save(findAuthor.get());

        return new ResponseData(200, "Success", findAuthor.get());
    }

    @Override
    public ResponseData delete(Long id) {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);
        authorValidator.validateAuthorIsAlreadyDeleted(findAuthor);

        findAuthor.get().setIsDeleted(true);
        authorRepository.save(findAuthor.get());

        return new ResponseData(200, "Successfully deleted author", null);
    }

    @Override
    public ResponseData recovery(Long id) {
        Optional<Author> findAuthor = authorRepository.findById(id);
        authorValidator.validateAuthorNotFound(findAuthor);
        authorValidator.validateAuthorIsAlreadyRecovery(findAuthor);

        findAuthor.get().setIsDeleted(false);
        authorRepository.save(findAuthor.get());

        return new ResponseData(200, "Successfully recovery author", null);
    }
}
