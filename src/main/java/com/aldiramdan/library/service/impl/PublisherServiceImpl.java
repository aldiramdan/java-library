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

    private ResponseData responseData;

    @Override
    public ResponseData getAll() {
        List<Publisher> listPublisher = publisherRepository.findAll();

        return responseData = new ResponseData(200, "Success", listPublisher);
    }

    @Override
    public ResponseData getById(Long id) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);

        return responseData = new ResponseData(200, "Success", findPublisher);
    }

    @Override
    public ResponseData add(PublisherRequest request) throws Exception {
        Optional<Publisher> findPublisherByName = publisherRepository.findByName(request.getName());
        publisherValidator.validatePublisherIsExists(findPublisherByName);

        Publisher publisher = new Publisher();
        publisher.setName(request.getName());

        publisherRepository.save(publisher);

        return responseData = new ResponseData(201, "Success", publisher);
    }

    @Override
    public ResponseData update(Long id, PublisherRequest request) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);

        Publisher publisher = findPublisher.get();
        if (publisher.getName() != request.getName()) {
            Optional<Publisher> findPublisherByName = publisherRepository.findByName(request.getName());
            publisherValidator.validatePublisherIsExists(findPublisherByName);

            publisher.setName(request.getName());
        }

        publisher.setName(publisher.getName());

        publisherRepository.save(publisher);

        return responseData = new ResponseData(200, "Success", publisher);
    }

    @Override
    public ResponseData delete(Long id) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);

        Publisher publisher = findPublisher.get();
        publisherValidator.validatePublisherIsAlreadyDeleted(publisher);

        publisher.setIsDeleted(true);

        publisherRepository.save(publisher);

        return responseData = new ResponseData(200, "Success", null);
    }

    @Override
    public ResponseData recovery(Long id) throws Exception {
        Optional<Publisher> findPublisher = publisherRepository.findById(id);
        publisherValidator.validatePublisherNotFound(findPublisher);

        Publisher publisher = findPublisher.get();
        publisherValidator.validatePublisherIsAlreadyDeleted(publisher);

        publisher.setIsDeleted(false);

        publisherRepository.save(publisher);

        return responseData = new ResponseData(200, "Successfully recovery publisher", null);
    }
}
