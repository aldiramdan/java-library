package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.GenreRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.GenreService;
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

public class GenreControllerTest {
    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenreController categoryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(genreService.getAll()).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(genreService, times(1)).getAll();
    }

    @Test
    public void testGetById() throws Exception {
        long genreId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(genreService.getById(genreId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.getById(genreId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(genreService, times(1)).getById(genreId);
    }

    @Test
    public void testCreate() throws Exception {
        GenreRequest request = new GenreRequest();
        ResponseData responseData = new ResponseData(HttpStatus.CREATED.value(), "Success", null);
        when(genreService.add(request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(genreService, times(1)).add(request);
    }

    @Test
    public void testUpdate() throws Exception {
        long genreId = 1L;
        GenreRequest request = new GenreRequest();
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(genreService.update(genreId, request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.update(genreId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(genreService, times(1)).update(genreId, request);
    }

    @Test
    public void testDelete() throws Exception {
        long genreId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(genreService.delete(genreId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.delete(genreId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(genreService, times(1)).delete(genreId);
    }

    @Test
    public void testRecovery() throws Exception {
        long genreId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(genreService.recovery(genreId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.recovery(genreId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(genreService, times(1)).recovery(genreId);
    }
}
