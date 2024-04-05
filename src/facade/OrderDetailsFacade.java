package facade;

import java.util.Objects;

import enums.InvoiceType;
import factories.InvoiceFactory;
import models.Customer;
import models.InvoiceTemplate;
import models.Order;
import models.Product;
import shipping.Shipping;

public class OrderDetailsFacade {
	private Order order;
	private Shipping shipping;
	private InvoiceTemplate accountantInvoice;
	private InvoiceTemplate customerInvoice;

	public OrderDetailsFacade(Order order, Shipping shipping) {
		this.order = order;
		this.shipping = shipping;
		setInvoice();
	}

	public Product getProduct() {
		return order.getProductAdapter().getProduct();
	}

	public Order getOrder() {
		return order;
	}

	public String getOrderId() {
		return order.getOrderId();
	}

	public int getQuantity() {
		return order.getQuantity();
	}

	public Customer getCustomer() {
		return order.getCustomer();
	}

	public Shipping getShipping() {
		return this.shipping;
	}

	public InvoiceTemplate getAccountantInvoice() {
		return accountantInvoice;
	}

	public InvoiceTemplate getCustomerInvoice() {
		return customerInvoice;
	}

	public void setInvoice() {
		this.accountantInvoice = generateInvoice(InvoiceType.ACCOUNTANT);
		this.customerInvoice = generateInvoice(InvoiceType.CUSTOMER);
	}

	private InvoiceTemplate generateInvoice(InvoiceType invoiceType) {
		return InvoiceFactory.invoiceFactory(this, invoiceType);
	}

	public String printInvoices() {
		StringBuilder sb = new StringBuilder();

		if (accountantInvoice != null) {
			sb.append(accountantInvoice);
		}
		if (customerInvoice != null) {
			sb.append(customerInvoice);
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(order);

		if (getShipping() != null) {
			sb.append("Shipping: ").append(getShipping()).append("\n");
		}
		sb.append("\nOrder profit= ").append(getProduct().getTheProfitCalculator().calculateProfitPerOrder(this))
				.append("\n");

		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(order.getOrderId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj instanceof Order)) {
			Order other = (Order) obj;
			return order.equals(other);

		}
		OrderDetailsFacade other = (OrderDetailsFacade) obj;
		return Objects.equals(order, other.order) && Objects.equals(accountantInvoice, other.accountantInvoice)
				&& Objects.equals(customerInvoice, other.customerInvoice);
	}

}
