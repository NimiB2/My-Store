package interfaces;

import java.util.LinkedHashSet;

import enums.ProductType;
import facade.OrderDetailsFacade;

public interface ProductInterface {

	String getProductId();

	String getProduct_name();

	int getSellingPrice();

	int getCostPrice();

	int getStock();

	ProductType getProductType();

	LinkedHashSet<OrderDetailsFacade> getAllOrders();

	String getCurrencySymbol();
}
