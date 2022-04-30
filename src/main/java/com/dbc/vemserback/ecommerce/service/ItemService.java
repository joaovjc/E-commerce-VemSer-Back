package com.dbc.vemserback.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dbc.vemserback.ecommerce.dto.Item.ItemCreateDTO;
import com.dbc.vemserback.ecommerce.dto.Item.ItemDTO;
import com.dbc.vemserback.ecommerce.dto.Item.ItemFullDTO;
import com.dbc.vemserback.ecommerce.entity.ItemEntity;
import com.dbc.vemserback.ecommerce.entity.TopicEntity;
import com.dbc.vemserback.ecommerce.enums.StatusEnum;
import com.dbc.vemserback.ecommerce.exception.BusinessRuleException;
import com.dbc.vemserback.ecommerce.repository.post.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
	private final ItemRepository itemRepository;
	private final FileService fileService;
	private final TopicService topicService;
	
	
	public ItemFullDTO createItem(ItemCreateDTO itemDTO, MultipartFile file, int idUser, Integer idTopic) throws BusinessRuleException {
		log.info("criando item");
		//checa para ver se a imagem está nula ou não
		if(file==null)throw new BusinessRuleException("a imagem do item não pode ser nula");
		String originalFilename = file.getOriginalFilename();
		
		//checa para ver o status do topico
		TopicEntity topicEntity = topicService.topicById(idTopic);
		if(topicEntity.getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("o topico não esta mais aberto a mudanças");
		
		ItemEntity build = ItemEntity.builder().itemName(itemDTO.getName()).description(itemDTO.getDescription())
				.value(itemDTO.getPrice()).fileName(originalFilename).file(fileService.convertToByte(file)).topicId(idTopic).topicEntity(topicEntity).build();
		//persiste um item
		itemRepository.save(build);
		
		//converte um item para dto
		return ItemFullDTO.builder()
				.description(build.getDescription())
				.file(new String(build.getFile()))
				.itemName(build.getItemName())
				.itemId(build.getItemId())
				.value(build.getValue())
				.build();
	}

	public List<ItemDTO> listItemsByTopicId(Integer topicId) throws BusinessRuleException {
		log.info("listando itens");
		//checa para ver se o topico existe e traz todos os item convertido para item dto
		TopicEntity topicById = topicService.topicById(topicId);
		return topicById.getItems().stream().map(ent->{
            return ItemDTO.builder()
                    .description(ent.getDescription())
                    .itemName(ent.getItemName())
                    .file(ent.getFile()!=null?new String(ent.getFile()):null)
                    .value(ent.getValue()).build();
        }).collect(Collectors.toList());
	}

	public void deleteById(int idItem, int userId) throws BusinessRuleException {
		log.info("deletando item");
		//checa se o item existe
		ItemEntity item = this.getById(idItem);
		//checa se o item é do usuario
		if(item.getTopicEntity().getUserId()!=userId)throw new BusinessRuleException("esse item não pertence a seu usuário");
		//checa se o status do topico permite alterações
		if(item.getTopicEntity().getStatus()!=StatusEnum.CREATING)throw new BusinessRuleException("o topico já não pode ser mais alterado");
		//delata o item
		this.itemRepository.delete(item);
	}
	
	private ItemEntity getById(int idItem) throws BusinessRuleException {
		log.info("buscando item");
		return this.itemRepository.findById(idItem).orElseThrow(()-> new BusinessRuleException("item não encontrado, por favor atualize a pagina"));
	}

}
