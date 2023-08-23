package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.GenreRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.Genre;
import com.aldiramdan.library.repository.GenreRepository;
import com.aldiramdan.library.service.GenreService;
import com.aldiramdan.library.validator.GenreValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreValidator genreValidator;

    private ResponseData responseData;

    @Override
    public ResponseData getAll() {
        List<Genre> listGenre = genreRepository.findAll();

        responseData = new ResponseData(200, "Success", listGenre);
        return responseData;
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);

        responseData = new ResponseData(200, "Success", findGenre);
        return responseData;
    }

    @Override
    public ResponseData add(GenreRequest request) throws Exception {
        Optional<Genre> findGenreByName = genreRepository.findByName(request.getName());
        genreValidator.validateGenreIsExists(findGenreByName);

        Genre genre = new Genre();
        genre.setName(request.getName());

        genreRepository.save(genre);

        responseData = new ResponseData(201, "Success", genre);
        return responseData;
    }

    @Override
    public ResponseData update(Long id, GenreRequest request) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);

        Genre genre = findGenre.get();
        if (genre.getName() != request.getName()) {
            Optional<Genre> findGenreByName = genreRepository.findByName(request.getName());
            genreValidator.validateGenreIsExists(findGenreByName);

            genre.setName(request.getName());
        }
        genre.setName(genre.getName());

        genreRepository.save(genre);

        responseData = new ResponseData(200, "Success", genre);
        return responseData;
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);

        Genre genre = findGenre.get();
        genreValidator.validateCategoryIsAlreadyDeleted(genre);

        genre.setIsDeleted(true);

        genreRepository.save(genre);

        responseData = new ResponseData(200, "Success", null);
        return responseData;
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);

        Genre genre = findGenre.get();
        genreValidator.validateCategoryIsAlreadyDeleted(genre);

        genre.setIsDeleted(false);

        genreRepository.save(genre);

        responseData = new ResponseData(200, "Successfully recovery genre", null);
        return responseData;
    }
}
