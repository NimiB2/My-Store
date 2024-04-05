package products;

import java.util.Objects;
import enums.ProductType;
import enums.ShippingType;
import interfaces.Shippable;
import models.Product;
import shipping.ShippingInfo;
import utilities.Values;

public class SoldThroughWebsite extends Product implements Shippable {

	private final String currencySymbol = Values.DOLLAR;
	private ShippingInfo info;

	public SoldThroughWebsite(String productId, ProductType type, String product_name, int cost_price,
			int selling_price, int stock) {
		super(productId, product_name, cost_price, selling_price, stock, type);

	}
	
	public void setShippingInfoToProduct(Product product, String productID, ShippingInfo shippingInfo) {
		if(product.getProductId() == productID)
			this.info = shippingInfo;
	}
	@Override
	public ShippingInfo getShipmentInfo() {
		return info;
	}
	
	public void addShippingType(ShippingType type) {
		this.info.addShippingType(type);
	}

	public void removeShippingType(ShippingType type) {
		this.info.removeShippingType(type);
	}

	@Override
	public int getSellingPrice() {
		return this.selling_price;
	}

	@Override
	public int getCostPrice() {
		return this.cost_price;
	}

	@Override
	public String getCurrencySymbol() {
		return this.currencySymbol;
	}

	@Override
	public boolean supportsAccountantInvoiceGeneration() {
		return false;
	}

	@Override
	public boolean supportsCustomerInvoiceGeneration() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(currencySymbol, info);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof SoldThroughWebsite)) {
			return false;
		}
		SoldThroughWebsite other = (SoldThroughWebsite) obj;
		return Objects.equals(currencySymbol, other.currencySymbol) && Objects.equals(info, other.info);
	}

	@Override
	public void setShippingInfo(ShippingInfo info) {
		this.info = info;
		
	}

}
