package com.dbc.vemserback.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import com.dbc.vemserback.ecommerce.service.FileService;

@RunWith(MockitoJUnitRunner.class)
public class FileTest {

	@InjectMocks
    private FileService fileService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    
    public void testTrhowsTheRigthException() throws Exception {
    	
    	
    }
    
    @Test
    public void testingTheTypesImageConversionAccepts() throws Exception {
    	Resource fileResource = new ClassPathResource(
                "src/main/resources/test_image/");
    	Resource filematch = new ClassPathResource(
                "screen-shots/HomePage-attachment.png");
    	
    	MockMultipartFile file = new MockMultipartFile(fileResource.getFilename(), fileResource.getInputStream());
    	
    	byte[] convertToByte = fileService.convertToByte(file);
    	
    	assertEquals(filematch.getInputStream().readAllBytes(), Base64.getDecoder().decode(convertToByte));
    }
	
    
}
