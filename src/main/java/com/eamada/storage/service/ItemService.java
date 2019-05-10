package com.eamada.storage.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	public ResponseEntity<Collection<Item>> getItems() {
		return new ResponseEntity<Collection<Item>>(this.itemRepository.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<Item> getItem(Long itemId) {
		return new ResponseEntity<Item>(this.itemRepository.findById(itemId).orElse(null), HttpStatus.OK);
	}
	
	public ResponseEntity<Item> addItem(CreateItemCommand createItemCommand) {
		Item newItem = new Item(createItemCommand.getName(), createItemCommand.getUnitOfMeasurement(),
				createItemCommand.getCategory(), createItemCommand.getPrice());
		this.itemRepository.save(newItem);
		return new ResponseEntity<>(newItem, HttpStatus.OK) ;
	}
	
}