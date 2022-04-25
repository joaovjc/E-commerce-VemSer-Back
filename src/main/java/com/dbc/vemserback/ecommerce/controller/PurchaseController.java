package com.dbc.vemserback.ecommerce.controller;


import com.dbc.vemserback.ecommerce.dto.PurchaseList.PurchaseDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;


    @PostMapping(path = "/create-item/{topic-id}", consumes = {MULTIPART_FORM_DATA_VALUE})
	public String createItem(@PathVariable(name = "topic-id") Integer idTopic, @Valid @ModelAttribute(name = "data") PurchaseDTO CreateDTO,
                              @RequestPart MultipartFile file, BindingResult bindingResult) throws BusinessRuleException, InterruptedException {
		System.out.println("chegou no controller: "+ CreateDTO.getName());
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return purchaseService.createPurchase(CreateDTO, file, Integer.parseInt((String) userb), idTopic);
	}

//	@GetMapping("/topics/itens/{id-topic}")
//	public Object allItens(@PathVariable(name = "id-topic") Integer idTopic) {
//		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return this.purchaseService.listAllTopics(idTopic,Integer.parseInt((String) userb));
//	}
}
