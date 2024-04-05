package factories;

import enums.ProductType;
import models.Product;
import products.SoldInStore;
import products.SoldThroughWebsite;
import products.SoldToWholesalers;


public class ProductFactory {
	public static Product createProduct(String productID, ProductType type, String product_name, int cost_price, int selling_price, int stock) {
		if(type == ProductType.STORE) {
			return new SoldInStore(productID, type, product_name, cost_price, selling_price, stock);
		} else if(type == ProductType.WEBSITE) {
			return new SoldThroughWebsite(productID, type, product_name, cost_price, selling_price, stock);
		} else if(type == ProductType.WHOLESALERS) {
			return new SoldToWholesalers(productID, type, product_name, cost_price, selling_price, stock);
		} else throw new IllegalArgumentException(); 
	}
}

