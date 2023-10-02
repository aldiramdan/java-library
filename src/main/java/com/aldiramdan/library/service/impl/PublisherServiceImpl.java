package com.aldiramdan.library.service.impl;

import com.aldiramdan.library.model.dto.request.PublisherRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.model.entity.Publisher;
import com.aldiramdan.library.repository.PublisherRepository;
import com.aldiramdan.library.service.PublisherService;
import com.aldiramdan.library.validator.PublisherValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherValidator publisherValidator;

    @Override
    public ResponseData getAll() {
        List<Publisher> listPublisher = publisherRepository.findAll();

        return new ResponseData(200, "Success", listPublisher);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);

        return new ResponseData(200, "Success", findPublisher);
    }

    @Override
    public ResponseData add(PublisherRequest request) throws Exception {
        Optional<Publisher> findPublisherByName = publisherRepository.findByName(request.getName());
        publisherValidator.validatePublisherIsExists(findPublisherByName);

        Publisher publisher = new Publisher();
        publisher.setName(request.getName());
        publisherRepository.save(publisher);

        return new ResponseData(201, "Success", publisher);
    }

    @Override
    public ResponseData update(Long id, PublisherRequest request) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);

        if (!findPublisher.get().getName().equals(request.getName())) {
            Optional<Publisher> findPublisherByName = publisherRepository.findByName(request.getName());
            publisherValidator.validatePublisherIsExists(findPublisherByName);
            findPublisher.get().setName(request.getName());
        }
        publisherRepository.save(findPublisher.get());

        return new ResponseData(200, "Success", findPublisher.get());
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);
        publisherValidator.validatePublisherIsAlreadyDeleted(findPublisher);

        findPublisher.get().setIsDeleted(true);
        publisherRepository.save(findPublisher.get());

        return new ResponseData(200, "Successfully deleted publisher", null);
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);
        publisherValidator.validatePublisherIsAlreadyRecovery(findPublisher);

        findPublisher.get().setIsDeleted(false);
        publisherRepository.save(findPublisher.get());

        return new ResponseData(200, "Successfully recovery publisher", null);
    }
}
