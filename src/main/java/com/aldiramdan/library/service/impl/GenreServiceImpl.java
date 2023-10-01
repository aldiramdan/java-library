package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.GenreRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.Genre;
import com.aldiramdan.library.repository.GenreRepository;
import com.aldiramdan.library.service.GenreService;
import com.aldiramdan.library.validator.GenreValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreValidator genreValidator;

    @Override
    public ResponseData getAll() {
        List<Genre> listGenre = genreRepository.findAll();

        return new ResponseData(200, "Success", listGenre);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);

        return new ResponseData(200, "Success", findGenre);
    }

    @Override
    public ResponseData add(GenreRequest request) throws Exception {
        Optional<Genre> findGenreByName = genreRepository.findByName(request.getName());
        genreValidator.validateGenreIsExists(findGenreByName);

        Genre genre = new Genre();
        genre.setName(request.getName());
        genreRepository.save(genre);

        return new ResponseData(201, "Success", genre);
    }

    @Override
    public ResponseData update(Long id, GenreRequest request) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);

        if (findGenre.get().getName() != request.getName()) {
            Optional<Genre> findGenreByName = genreRepository.findByName(request.getName());
            genreValidator.validateGenreIsExists(findGenreByName);
            findGenre.get().setName(request.getName());
        }
        genreRepository.save(findGenre.get());

        return new ResponseData(200, "Success", findGenre.get());
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);
        genreValidator.validateCategoryIsAlreadyDeleted(findGenre);

        findGenre.get().setIsDeleted(true);
        genreRepository.save(findGenre.get());

        return new ResponseData(200, "Successfully deleted genre", null);
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Genre> findGenre = genreRepository.findById(id);
        genreValidator.validateGenreNotFound(findGenre);
        genreValidator.validateCategoryIsAlreadyRecovery(findGenre);

        findGenre.get().setIsDeleted(false);
        genreRepository.save(findGenre.get());

        return new ResponseData(200, "Successfully recovery genre", null);
    }
}
