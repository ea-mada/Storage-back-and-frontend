package com.eamada.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eamada.storage.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>  {

}
