package memento;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import models.Order;
import models.Product;
import models.ShippingCompany;

public class StoreManagementMemento {
	private Map<String, Product> products;
	private Set<ShippingCompany> companies;
	private Stack<Order> order;

	
	public StoreManagementMemento(Map<String, Product> products, Set<ShippingCompany> companies,
			Stack<Order> orderStack) {
		this.products = new TreeMap<>(products);
		this.companies = new LinkedHashSet<>(companies);
		this.order = new Stack<>();
		this.order.addAll(orderStack);
	}

	public Map<String, Product> getProducts() {
		return products;
	}

	public Set<ShippingCompany> getCompanies() {
		return companies;
	}

	public Stack<Order> getOrdersStack() {
		return order;
	}
}
