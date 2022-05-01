package com.dbc.vemserback.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.FileService;

@RunWith(MockitoJUnitRunner.class)
public class FileTest {

	@InjectMocks
    private FileService fileService;
	
	@Mock
	public ExpectedException expectedException;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testTrhowsTheRigthException() throws Exception {
    	MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
    		      "text/plain", "Spring Framework".getBytes());
    	BusinessRuleException assertThrows2 = assertThrows(BusinessRuleException.class, () -> {
        	fileService.convertToByte(multipartFile);
        });
    	assertEquals("Esse tipo de arquivo não é suportado: .txt", assertThrows2.getMessage());
    }
    
    @Test
    public void testingTheTypesImageConversionAccepts() throws Exception {
    	File file = new File("src/main/resources/test_image/test_before.png");
    	FileInputStream input = new FileInputStream(file);
    	
    	MultipartFile multipartFile = new MockMultipartFile("file.png",
    	            file.getName(), "text/plain", input.readAllBytes());
    	byte[] convertToByte = fileService.convertToByte(multipartFile);
    	input.close();
    	
    	assertNotNull(convertToByte);
    }
	
    
}
