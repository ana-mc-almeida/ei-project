package org.acme;

public class SelledProduct {

	public enum TypeOfAnalysis {
		LOYALTY_CARD, CUSTOMER, DISCOUNT_COUPON, SHOP, PRODUCT, POSTAL_CODE
	}

	public TypeOfAnalysis typeOfAnalysis;
	public String typeValue;
	public java.time.LocalDateTime timestamp;
	public double result;

	public SelledProduct() {
	}

	public SelledProduct(TypeOfAnalysis typeOfAnalysis, String typeValue, java.time.LocalDateTime timestamp,
			double result) {
		this.typeOfAnalysis = typeOfAnalysis;
		this.typeValue = typeValue;
		this.timestamp = timestamp;
		this.result = result;
	}

	@Override
	public String toString() {
		return "{typeOfAnalysis=" + typeOfAnalysis + ", typeValue=" + typeValue
				+ ", timestamp=" + timestamp.toString() + ", result=" + result + "}";
	}

}
