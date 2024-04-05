package products;

import java.util.Objects;

import enums.ProductType;
import models.Product;
import utilities.Values;

public class SoldToWholesalers extends Product {
	private ProductType type;
	private final String currencySymbol = Values.SHEKEL;

	public SoldToWholesalers(String productId, ProductType type, String product_name, int cost_price, int selling_price,
			int stock) {
		super(productId, product_name, cost_price, selling_price, stock, type);
	}

	public ProductType getType() {
		return type;
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
		return currencySymbol;
	}

	@Override
	public boolean supportsAccountantInvoiceGeneration() {
		return true;
	}

	@Override
	public boolean supportsCustomerInvoiceGeneration() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(currencySymbol, type);
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
		if (!(obj instanceof SoldToWholesalers)) {
			return false;
		}
		SoldToWholesalers other = (SoldToWholesalers) obj;
		return Objects.equals(currencySymbol, other.currencySymbol) && type == other.type;
	}
}
