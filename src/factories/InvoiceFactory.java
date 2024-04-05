package factories;

import enums.InvoiceType;
import facade.OrderDetailsFacade;
import invoiceTemplates.InvoiceFormatForAccountant;
import invoiceTemplates.InvoiceFormatForCustomer;
import models.InvoiceTemplate;
import models.Product;
import utilities.Profit;

public class InvoiceFactory {

	public static InvoiceTemplate invoiceFactory(OrderDetailsFacade order, InvoiceType invoiceType) {
		if (order == null || invoiceType == null) {
			throw new IllegalArgumentException("Order and invoice type must not be null");
		}

		Product product = order.getProduct();
		Profit profit = product.getTheProfitCalculator();
		InvoiceTemplate invoice = null;

		switch (invoiceType) {
		case ACCOUNTANT:
			if (product.supportsAccountantInvoiceGeneration()) {
				invoice = new InvoiceFormatForAccountant(product, order, profit);
			}
			break;
		case CUSTOMER:
			if (product.supportsCustomerInvoiceGeneration()) {
				invoice = new InvoiceFormatForCustomer(product, order, profit);
			}
			break;
		default:
			System.out.println("Product not support inovice");
		}

		return invoice;
	}
}
