package invoiceTemplates;

import facade.OrderDetailsFacade;
import models.InvoiceTemplate;
import models.Product;
import utilities.Profit;

public class InvoiceFormatForAccountant extends InvoiceTemplate {

	public InvoiceFormatForAccountant(Product product, OrderDetailsFacade order, Profit profit) {
		super(product, order, profit);

	}

	@Override
	protected String getHeader() {
		StringBuilder heder = new StringBuilder();
		heder.append("**Accountant inovice**\n");
		heder.append("Customer:" + orderDetails.getCustomer().getCustomer_name());
		return heder.toString();
	}

	@Override
	protected String getBody() {
		StringBuilder body = new StringBuilder();
		body.append("**Order Details**\n");
		body.append("Product Name: ").append(product.getProduct_name()).append("\n");
		body.append("Unit selling price: ").append(product.getSellingPrice()).append(symbol).append("\n");
		body.append("Unit cost price: ").append(product.getCostPrice()).append(symbol).append("\n");

		body.append("Quantity: ").append(orderDetails.getQuantity()).append("\n");
		body.append("Unit profit: ").append(profit.calculateProfitPerOrder(orderDetails)).append(symbol).append("\n");

		if (orderDetails.getShipping() != null) {
			body.append("Shipping: ").append(orderDetails.getShipping()).append("\n");
		}
		return body.toString();
	}

	@Override
	protected String getFooter() {
		StringBuilder footer = new StringBuilder();
		footer.append("Final payment: ");
		footer.append(profit.calculatePayment(orderDetails)).append(symbol).append("\n");
		footer.append("Total profit: " + profit.calculateTotalProductProfit(product));
		footer.append(symbol).append("\n");
		return footer.toString();
	}

}
