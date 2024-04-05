package shipping;

import java.util.LinkedHashSet;
import java.util.Set;

import enums.ShippingType;

public class ShippingInfo {
	private Set<ShippingType> shipments;
	private String destCountry;
	private int destImportTax;
	private int weight;
	private int sellingPrice;

	public ShippingInfo(ShippingType shippingType, String destCountry, int destImportTax, int weight,
			int sellingPrice) {

		this.shipments = new LinkedHashSet<>();
		addShippingType(shippingType);
		setDestCountry(destCountry);
		setDestImportTax(destImportTax);
		setWeight(weight);
		this.sellingPrice = sellingPrice;
	}

	public void addShippingType(ShippingType shippingType) {
		this.shipments.add(shippingType);
	}

	public void removeShippingType(ShippingType shippingType) {
		if (this.shipments.size() > 1) {
			this.shipments.remove(shippingType);
		}
	}

	public Set<ShippingType> getshipments() {
		return shipments;
	}

	public int getDestImportTax() {
		return destImportTax;
	}

	public void setDestImportTax(int destImportTax) {
		if (destImportTax >= 0) {
			this.destImportTax = destImportTax;
		} else {
			throw new IllegalArgumentException("Destination import tax cannot be negative.");
		}
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		if (weight >= 0) {
			this.weight = weight;
		} else {
			throw new IllegalArgumentException("Weight cannot be negative.");
		}
	}

	public String getDestCountry() {
		return destCountry;
	}

	public void setDestCountry(String destCountry) {
		this.destCountry = destCountry;
	}

	public int getSellingPrice() {

		return this.sellingPrice;
	}

}
