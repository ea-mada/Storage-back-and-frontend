package com.eamada.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eamada.storage.CreateItemCommand;
import com.eamada.storage.model.Item;
import com.eamada.storage.repository.ItemRepository;

@Service
public class ItemService {
	private ItemRepository itemRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public Item addItem(CreateItemCommand createItemCommand) {
		Item newItem = new Item(createItemCommand.getName(), createItemCommand.getUnitOfMeasurement(),
				createItemCommand.getCategory(), createItemCommand.getPrice());
		this.itemRepository.save(newItem);
		return newItem;
	}
}
