package com.aldiramdan.library.controller;

import com.aldiramdan.library.model.dto.request.CategoryRequest;
import com.aldiramdan.library.model.dto.response.ResponseData;
import com.aldiramdan.library.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(categoryService.getAll()).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(categoryService, times(1)).getAll();
    }

    @Test
    public void testGetById() throws Exception {
        long categoryId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(categoryService.getById(categoryId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.getById(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(categoryService, times(1)).getById(categoryId);
    }

    @Test
    public void testCreate() throws Exception {
        CategoryRequest request = new CategoryRequest();
        ResponseData responseData = new ResponseData(HttpStatus.CREATED.value(), "Success", null);
        when(categoryService.add(request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(categoryService, times(1)).add(request);
    }

    @Test
    public void testUpdate() throws Exception {
        long categoryId = 1L;
        CategoryRequest request = new CategoryRequest();
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(categoryService.update(categoryId, request)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.update(categoryId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(categoryService, times(1)).update(categoryId, request);
    }

    @Test
    public void testDelete() throws Exception {
        long categoryId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(categoryService.delete(categoryId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.delete(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(categoryService, times(1)).delete(categoryId);
    }

    @Test
    public void testRecovery() throws Exception {
        long categoryId = 1L;
        ResponseData responseData = new ResponseData(HttpStatus.OK.value(), "Success", null);
        when(categoryService.recovery(categoryId)).thenReturn(responseData);

        ResponseEntity<ResponseData> response = categoryController.recovery(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseData, response.getBody());

        verify(categoryService, times(1)).recovery(categoryId);
    }
}