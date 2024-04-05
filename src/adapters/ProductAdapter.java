package adapters;

import java.util.Objects;

import enums.ProductType;
import models.Product;
import utilities.Profit;

public class ProductAdapter {
	private Product product;

	public ProductAdapter(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return this.product;
	}

	public String getProductId() {
		return this.product.getProductId();
	}

	public ProductType getType() {
		return this.product.getProductType();
	}

	public String getName() {
		return this.product.getProduct_name();
	}

	public int getCostPrice() {
		return this.product.getCostPrice();
	}

	public int getSellingPrice() {
		return this.product.getSellingPrice();
	}

	public String getCurrencySymbol() {
		return this.product.getCurrencySymbol();
	}

	public Profit getTheProfitCalculator() {
		return this.product.getTheProfitCalculator();
	}

	public int getProductProfit() {
		return this.product.getProductProfit();
	}

	@Override
	public String toString() {
		return "PRODUCT:\n" + "Type: " + getType() + "\nProduct name: " + getName() + "\nProduct cost: "
				+ getCostPrice() + getCurrencySymbol() + "\nProduct selling price: " + getSellingPrice()
				+ getCurrencySymbol() + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Product) {
			Product otherProduct = (Product) obj;
			return product.equals(otherProduct);
		}

		ProductAdapter other = (ProductAdapter) obj;
		return Objects.equals(product, other.product);
	}

}
