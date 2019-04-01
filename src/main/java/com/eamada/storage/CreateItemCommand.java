package com.eamada.storage;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class CreateItemCommand {
	@NotNull
	@Length(min = 1, max = 20)
	private String name;
	
	@NotNull
	private int quantity;
	
	@NotNull
	private int heightCm;
	
	@NotNull
	private int widthCm;
	
	@NotNull
	private long priceEuroCents;
	
	public CreateItemCommand() { }

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getHeightCm() {
		return heightCm;
	}

	public int getWidthCm() {
		return widthCm;
	}

	public long getPriceEuroCents() {
		return priceEuroCents;
	}
	
}
