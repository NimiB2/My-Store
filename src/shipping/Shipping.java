package shipping;

import enums.ShippingType;
import models.ShippingCompany;

public class Shipping {
	private final ShippingCompany company;
	private final ShippingType shippingType;
	private final int shippingFees;

	public Shipping(ShippingCompany company, ShippingType shippingType, int shippingFees) {
		this.company = company;
		this.shippingType = shippingType;
		this.shippingFees = shippingFees;
	}

	public ShippingCompany getCompany() {
		return company;
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public int getShippingFees() {
		return shippingFees;
	}

	@Override
	public String toString() {
		return " [company=" + company + ", shippingType=" + shippingType + ", shippingFees=" + shippingFees + "]";
	}

}
