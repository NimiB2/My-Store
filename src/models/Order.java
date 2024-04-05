package models;

import java.util.Objects;

import adapters.ProductAdapter;
import exceptions.StockExceptions;
import facade.OrderDetailsFacade;
import shipping.Shipping;

public class Order {

	private String orderId;
	private Customer customer;
	private ProductAdapter product;
	private int quantity;

	public Order(String orderId, Product product, Customer customer, int quantity) throws StockExceptions {
		setOrderId(orderId);
		setCustomer(customer);
		setQuantity(quantity);
		setProduct(product);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderNumber) {
		if (orderNumber == null || orderNumber.isEmpty()) {
			throw new IllegalArgumentException("Order ID cannot be null or empty.");
		}
		this.orderId = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		if (customer == null) {
			throw new NullPointerException("Customer cannot be null.");
		}
		this.customer = customer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than 0.");
		}
		this.quantity = quantity;
	}

	public ProductAdapter getProductAdapter() {
		return product;
	}

	public void setProduct(Product product) throws StockExceptions {
		if (product == null) {
			throw new NullPointerException("Product cannot be null.");
		}
		if (product.checkStock(this.quantity)) {
			this.product = product.getAdapter();
		} else {
			throw new StockExceptions();
		}

	}

	public boolean addOrderDetils(Shipping shipping) {
		OrderDetailsFacade detailsFacade = new OrderDetailsFacade(this, shipping);
		
		return this.product.getProduct().addOrderDetails(detailsFacade);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order Id = ").append(orderId).append("\n");
		sb.append(customer);
		sb.append("Quantity = ").append(quantity).append("\n");

		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, customer, product, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Order)) {
			return false;
		}
		Order other = (Order) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(customer, other.customer)
				&& Objects.equals(product.getProduct(), other.product.getProduct()) && quantity == other.quantity;
	}

}
