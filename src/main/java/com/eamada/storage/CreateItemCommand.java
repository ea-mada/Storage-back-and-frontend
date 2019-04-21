package com.eamada.storage;

import javax.validation.constraints.NotNull;

import com.eamada.storage.model.Category;
import com.eamada.storage.model.UnitOfMeasurement;

public class CreateItemCommand {
	
	@NotNull
	private String name;
	@NotNull
    private double price;
	@NotNull
    private UnitOfMeasurement unitOfMeasurement;
	@NotNull
    private Category category;
	
	public CreateItemCommand() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
