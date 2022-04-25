package com.dbc.vemserback.ecommerce;

import com.dbc.vemserback.ecommerce.repository.post.QuotationRepository;
import com.dbc.vemserback.ecommerce.service.QuotationService;
import com.dbc.vemserback.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class QuotationTest {

    @Mock
    private QuotationRepository quotationRepository;
    @Mock
    private  ObjectMapper objectMapper;
    @Mock
    private  UserService userService;

    @InjectMocks
    private QuotationService quotationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testCreateQuotation() {
//        QuotationEntity quotationEntity = new QuotationEntity();
//        QuotationCreateDTO quotationCreateDTO = new QuotationCreateDTO();
//
//        when(objectMapper.convertValue(quotationCreateDTO, QuotationEntity.class)).thenReturn(quotationEntity);
//        when(quotationRepository.save(any(QuotationEntity.class))).thenReturn(quotationEntity);
//
//        quotationService.createQuotation(quotationCreateDTO);
//
//        verify(quotationRepository, times(1)).save(any(QuotationEntity.class));
//
//    }



}
