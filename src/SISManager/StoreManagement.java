package SISManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

import enums.ProductType;
import enums.ShippingType;
import exceptions.StockExceptions;
import facade.OrderDetailsFacade;
import memento.StoreManagementCaretaker;
import memento.StoreManagementMemento;
import models.Customer;
import models.Order;
import models.Product;
import models.ShippingCompany;
import shipping.Shipping;
import shipping.ShippingInfo;
import shipping.ShippingObserverImpl;
import utilities.UndoOrderCommand;

public class StoreManagement {

	private static volatile StoreManagement instance;
	private Set<ShippingCompany> companies;
	private Map<String, Product> products;
	private Stack<Order> ordersUndoStack;
	private StoreManagementCaretaker caretaker;

	private StoreManagement() throws IllegalArgumentException{
		this.products = new TreeMap<>();
		this.companies = new LinkedHashSet<>();
		this.ordersUndoStack = new Stack<>();
		this.caretaker = new StoreManagementCaretaker();
	}

	public static StoreManagement getInstance() {
		if (instance == null) {
			synchronized (StoreManagement.class) {
				if (instance == null) {
					instance = new StoreManagement();
				}
			}
		}
		return instance;
	}


	
	public void addCompany(ShippingCompany company) {
		companies.add(company);
	}

	public void removeCompany(ShippingCompany company) {
		if (this.companies.contains(company)) {
			this.companies.remove(company);
		}
	}

	public Set<ShippingCompany> getCompanies() {
		return companies;
	}

	public void undoOrder() {
		if (!ordersUndoStack.isEmpty()) {
			System.out.println("OrderList before:");
			printAllOrders();
			Order order = ordersUndoStack.pop();
			Product product = order.getProductAdapter().getProduct();
			if (product != null) {
				UndoOrderCommand command = new UndoOrderCommand(order);
				command.execute(product);
				System.out.println("OrderList After:");
				printAllOrders();
			} else {
				System.out.println("Product not found for undo operation.");
			}
		}
	}

	public Order getOrder(String productId, String orderId) {
		Iterator<OrderDetailsFacade> iterator = products.get(productId).getAllOrders().iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next().getOrder();
			if (order.getOrderId().equals(orderId)) {
				return order;
			}
		}
		return null;
	}

	public void addProduct(String productId, Product product) {

		if (products.containsKey(productId)) {
			throw new IllegalArgumentException("Product with ID " + productId + " already exists.");
		}

		products.put(productId, product);
	}

	public void removeProduct(String productId) {
		if (!products.containsKey(productId)) {
			throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");

		}
		Product product = products.get(productId);
		if (product != null) {
			product.removeAllOrders();
			products.remove(productId);
			System.out.println("Product removed successfully.");
		}
	}

	public Product getProductById(String productId) {
		Product product = products.get(productId);
		if (product != null) {
			return product;

		} else {
			System.out.println("Product with ID " + productId + " not found.");
		}
		return product;
	}

	public Map<String, Product> getAllProducts() {
		return this.products;
	}

	public boolean updateStock(String productId, int quantity) throws IllegalArgumentException{
		Product product = products.get(productId);
		if (product == null) {
			throw new IllegalArgumentException("Product with ID " + productId + " not found.");
		}
		product.setStock(quantity);
		System.out.println("Inventory updated successfully for product with ID " + productId);
		return true;
	}

	public boolean addOrderToProduct(String orderId, Product product, Shipping shipping, Customer customer, int quantity) {
		if (product != null) {
			Order order = null;
			try {
				order = new Order(orderId, product, customer, quantity);
			
			
			if (order.addOrderDetils(shipping)) {
				ordersUndoStack.push(order);
			}
			return true;
			} catch (StockExceptions e) {
				order = null;
				System.out.println(e);
			}
		} else {
			return false;
		}
		return false;
		
	}

	public Shipping checkShippable(ShippingInfo info, ShippingType type) {
		@SuppressWarnings("unused")
		ShippingType cheapestShippingType;
		ShippingObserverImpl observer;
		int cost, cheapestCost;
		Shipping shipping = null;
		observer = new ShippingObserverImpl();

		for (ShippingCompany company : getCompanies()) {
			cost = observer.calculateShippingCost(company, info, type);
			observer.update(company, cost, type);
		}

		ShippingCompany cheapestCompany = observer.getCheapestCompany();
		cheapestCost = observer.getCheapestCost();
		cheapestShippingType = observer.getShippingType();
		shipping = new Shipping(cheapestCompany, type, cheapestCost);
		System.out.println(shipping);

		return shipping;
	}

	public void removeOrderFromProduct(String productId, Order order) {
		Product product = products.get(productId);
		String id = order.getOrderId();
		int quantity = order.getQuantity();
		product.removeOrder(id, quantity);
	}

	public void printAllProductsByType(ProductType type) {

		for (Map.Entry<String, Product> entry : products.entrySet()) {
			Product product = entry.getValue();
			if (product.getProductType() == type) {
				System.out.println(product);
			}
		}
	}

	public void printAllOrders() {
		Iterator<Order> iterator = ordersUndoStack.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			System.out.println(order);
		}
	}
	public boolean checkProductStockByType(Product product) {
		if(products.containsValue(product)) {
			return true;
		}
		return false;
	}

	public void saveState() {
	    StoreManagementMemento memento = new StoreManagementMemento(products, companies, ordersUndoStack);
	    caretaker.addMemento(memento);
	    
	    LocalDateTime currentTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDateTime = currentTime.format(formatter);
	    
	    System.out.println("System backup created at: " + formattedDateTime);
	}
	
	public void restoreState() {
		StoreManagementMemento memento = caretaker.getMemento();
		if(memento != null) {
			this.products = memento.getProducts();
			this.companies = memento.getCompanies();
			this.ordersUndoStack = memento.getOrdersStack();
			System.out.println("System restored state to the last backup.");
		}
	}
	
}