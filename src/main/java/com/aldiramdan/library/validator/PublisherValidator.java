package com.aldiramdan.library.validator;

import com.aldiramdan.library.exception.custom.FoundException;
import com.aldiramdan.library.exception.custom.NotFoundException;
import com.aldiramdan.library.exception.custom.NotProcessException;
import com.aldiramdan.library.model.entity.Publisher;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PublisherValidator {
    public void validatePublisherNotFound(Optional<Publisher> findPublisher) throws Exception {
        if (findPublisher.isEmpty()) {
            throw new NotFoundException("Publisher is not found!");
        }
    }

    public void validatePublisherIsExists(Optional<Publisher> findPublisher) throws Exception {
        if (findPublisher.isPresent()) {
            throw new FoundException("Publisher is found!");
        }
    }

    public void validatePublisherIsAlreadyDeleted(Publisher publisher) throws Exception {
        if (Objects.nonNull(publisher.getIsDeleted()) && publisher.getIsDeleted()) {
            throw new NotProcessException("Publisher is already deleted!");
        }
    }
}
