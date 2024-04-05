package models;

import java.util.Objects;

import facade.OrderDetailsFacade;
import utilities.Profit;

public abstract class InvoiceTemplate {

	protected final OrderDetailsFacade orderDetails;
	protected Profit profit;
	protected final Product product;
	protected final String symbol;

	public InvoiceTemplate(Product product, OrderDetailsFacade order, Profit profit) {
		if (order == null) {
			throw new NullPointerException("Order details cannot be null.");
		}
		this.product = product;
		this.orderDetails = order;
		this.profit = profit;
		this.symbol = orderDetails.getProduct().getCurrencySymbol();

	}

	protected abstract String getHeader();

	protected abstract String getBody();

	protected abstract String getFooter();

	public final String generate() {
		StringBuilder invoice = new StringBuilder();
		invoice.append("\n+++++++++++++++++++++++++++++++++\n");
		invoice.append(getHeader());
		invoice.append("\n------------------------------\n");
		invoice.append(getBody());
		invoice.append("------------------------------\n");
		invoice.append(getFooter());
		invoice.append("+++++++++++++++++++++++++++++++++\n");
		return invoice.toString();
	}
	@Override
	public String toString() {
		return generate();
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderDetails, profit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InvoiceTemplate)) {
			return false;
		}
		InvoiceTemplate other = (InvoiceTemplate) obj;
		return Objects.equals(orderDetails, other.orderDetails) && Objects.equals(profit, other.profit);
	}

}
