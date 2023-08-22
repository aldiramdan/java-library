package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.AuthorRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorControllerTest {
    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(authorService.getAll()).thenReturn(responseData);

        ResponseEntity<ResponseData> response = authorController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(authorService, times(1)).getAll();
    }

    @Test
    public void testGetById() throws Exception {
        long authorId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(authorService.getById(authorId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = authorController.getById(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(authorService, times(1)).getById(authorId);
    }

    @Test
    public void testCreate() throws Exception {
        AuthorRequest request = new AuthorRequest();
        ResponseData responseData = new ResponseData(HttpStatus.CREATED.value(), "Success", null);
        when(authorService.add(request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = authorController.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(authorService, times(1)).add(request);
    }

    @Test
    public void testUpdate() throws Exception {
        long authorId = 1L;
        AuthorRequest request = new AuthorRequest();
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(authorService.update(authorId, request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = authorController.update(authorId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(authorService, times(1)).update(authorId, request);
    }

    @Test
    public void testDelete() throws Exception {
        long authorId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(authorService.delete(authorId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = authorController.delete(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(authorService, times(1)).delete(authorId);
    }

    @Test
    public void testRecovery() throws Exception {
        long authorId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(authorService.recovery(authorId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = authorController.recovery(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(authorService, times(1)).recovery(authorId);
    }
}