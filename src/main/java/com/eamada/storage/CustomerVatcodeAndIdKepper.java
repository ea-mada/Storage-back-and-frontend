package com.eamada.storage;

public class CustomerVatcodeAndIdKepper {
	private String vatCode;
	private Long id;
	
	public CustomerVatcodeAndIdKepper(String vatCode, Long id) {
		this.vatCode = vatCode;
		this.id = id;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
