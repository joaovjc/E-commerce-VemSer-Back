package com.dbc.vemserback.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.dbc.vemserback.ecommerce.service.FileService;

@RunWith(MockitoJUnitRunner.class)
public class FileTest {

	@InjectMocks
    private FileService fileService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    
    
	
}
