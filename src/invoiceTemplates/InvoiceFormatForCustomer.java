package invoiceTemplates;

import facade.OrderDetailsFacade;
import models.InvoiceTemplate;
import models.Product;
import utilities.Profit;

public class InvoiceFormatForCustomer extends InvoiceTemplate {

	public InvoiceFormatForCustomer(Product product, OrderDetailsFacade order, Profit profit) {
		super(product, order, profit);

	}

	@Override
	protected String getHeader() {
		StringBuilder heder = new StringBuilder();
		heder.append("**Customer Receipt**\n");
		heder.append(orderDetails.getCustomer());
		return heder.toString();
	}

	@Override
	protected String getBody() {
		StringBuilder body = new StringBuilder();
		body.append("Product Name: ").append(product.getProduct_name()).append("\n");
		body.append("Quantity: ").append(orderDetails.getQuantity()).append("\n");
		int price = orderDetails.getProduct().getSellingPrice();
		body.append("Unit Price(including VAT 17%): ").append(price).append(symbol).append("\n");

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
		footer.append("The total vat: ").append(String.format("%.2f", profit.calculateTax(orderDetails))).append(symbol)
				.append("\n");
		;
		footer.append("\tThank You\n");
		return footer.toString();
	}

}
