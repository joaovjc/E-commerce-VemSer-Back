package com.dbc.vemserback.ecommerce.controller.contributor;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.Item.ItemCreateDTO;
import com.dbc.vemserback.ecommerce.dto.Item.ItemFullDTO;
import com.dbc.vemserback.ecommerce.dto.topic.TopicCreateDTO;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.service.ItemService;
import com.dbc.vemserback.ecommerce.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contributor")
@RequiredArgsConstructor
@Api(value = "3 - Contributor API", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"3 - Contributor API"})
public class ContributorController {

	private final TopicService topicService;
	private final ItemService purchaseService;
	
	@PostMapping("/create-topic")
	public Integer createTopic(@RequestBody TopicCreateDTO dto) throws BusinessRuleException {
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return topicService.createTopic(dto,Integer.parseInt((String) userb));
	}

    @PostMapping(path = "/create-item/{topic-id}", consumes = {MULTIPART_FORM_DATA_VALUE})
	public ItemFullDTO createItem(@PathVariable(name = "topic-id") Integer idTopic, @Valid @ModelAttribute(name = "data") ItemCreateDTO CreateDTO,
                              @RequestPart MultipartFile file, BindingResult bindingResult) throws BusinessRuleException {
    	if(bindingResult.hasErrors()) {
    		StringBuilder builder = new StringBuilder();
        	bindingResult.getAllErrors().forEach(err -> builder.append(err.getDefaultMessage()));
    		throw new BusinessRuleException(builder.toString());
    	}
		Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return purchaseService.createItem(CreateDTO, file, Integer.parseInt((String) userb), idTopic);
	}
    
    @PutMapping("/update-status/{topic-id}")
    public void closeTopicById(@PathVariable(name = "topic-id") int idTopic) throws BusinessRuleException {
    	Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	this.topicService.openTopic(idTopic, Integer.parseInt((String) userb));
    }
    
    @DeleteMapping("/delete/{item-id}")
    public void deleteById(@PathVariable(name = "item-id") int itemId) throws BusinessRuleException {
    	Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	this.purchaseService.deleteById(itemId, Integer.parseInt((String) userb));
    }
    
    @DeleteMapping("/delete-topic/{topic-id}")
    public void deleteTopicById(@PathVariable(name = "topic-id") int topicId) throws BusinessRuleException {
    	Object userb = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	this.topicService.deleteById(topicId, Integer.parseInt((String) userb));
    }
}
