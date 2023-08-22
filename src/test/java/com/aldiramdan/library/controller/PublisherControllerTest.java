package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.PublisherRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PublisherControllerTest {
    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(publisherService.getAll()).thenReturn(responseData);

        ResponseEntity<ResponseData> response = publisherController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(publisherService, times(1)).getAll();
    }

    @Test
    public void testGetById() throws Exception {
        long publisherId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(publisherService.getById(publisherId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = publisherController.getById(publisherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(publisherService, times(1)).getById(publisherId);
    }

    @Test
    public void testCreate() throws Exception {
        PublisherRequest request = new PublisherRequest();
        ResponseData responseData = new ResponseData(HttpStatus.CREATED.value(), "Success", null);
        when(publisherService.add(request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = publisherController.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(publisherService, times(1)).add(request);
    }

    @Test
    public void testUpdate() throws Exception {
        long publisherId = 1L;
        PublisherRequest request = new PublisherRequest();
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(publisherService.update(publisherId, request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = publisherController.update(publisherId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(publisherService, times(1)).update(publisherId, request);
    }

    @Test
    public void testDelete() throws Exception {
        long publisherId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(publisherService.delete(publisherId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = publisherController.delete(publisherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(publisherService, times(1)).delete(publisherId);
    }

    @Test
    public void testRecovery() throws Exception {
        long publisherId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(publisherService.recovery(publisherId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = publisherController.recovery(publisherId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(publisherService, times(1)).recovery(publisherId);
    }
}
