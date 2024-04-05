package utilities;

import SISManager.StoreManagement;
import facade.OrderDetailsFacade;
import models.Product;
import shipping.Shipping;

public class Profit {

	public int calculateProfitPerOrder(OrderDetailsFacade orderDetails) {
		Product product = orderDetails.getProduct();
		int quantity = orderDetails.getQuantity();
		int sellingPrice = product.getSellingPrice();
		int costPrice = product.getCostPrice();

		sellingPrice /= product.getRate();

		return ((sellingPrice - costPrice) * quantity);
	}

	public int calculateTotalProductProfit(Product product) {
		int totalProfit = 0;
		for (OrderDetailsFacade order : product.getAllOrders()) {
			totalProfit += calculateProfitPerOrder(order);
		}
		return totalProfit;
	}

	public int calculateTotalStoreProfit(StoreManagement storeManagement) {
		int totalProfit = 0;
		for (Product product : storeManagement.getAllProducts().values()) {
			totalProfit += calculateTotalProductProfit(product);
		}
		return totalProfit;
	}

	public int calculatePayment(OrderDetailsFacade orderDetails) {
		Shipping shipping = orderDetails.getShipping();
		int shippingFees;
		int payment = 0;

		if (shipping == null) {
			shippingFees = 0;
		} else {
			shippingFees = shipping.getShippingFees();
		}
		Product product = orderDetails.getProduct();
		int quantity = orderDetails.getQuantity();
		int sellingPrice = product.getSellingPrice();
		int rate = product.getRate();

		sellingPrice /= rate;
		shippingFees /= rate;

		payment = sellingPrice * quantity;
		payment += shippingFees;
		return payment;
	}

	public double calculateTax(OrderDetailsFacade orderDetails) {
		return calculatePayment(orderDetails) * Values.VAT;
	}

}