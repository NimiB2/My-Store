package models;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;

import adapters.ProductAdapter;
import enums.ProductType;
import facade.OrderDetailsFacade;
import interfaces.InvoiceSupportable;
import interfaces.ProductInterface;
import utilities.CurrencyRateManager;
import utilities.Profit;

public abstract class Product implements ProductInterface, InvoiceSupportable {
	private String productId;
	private String product_name;
	protected int cost_price;
	protected int selling_price;
	protected int stock;
	private ProductType productType;
	private Profit profitCalculator;
	private LinkedHashSet<OrderDetailsFacade> orders;

	public Product(String productId, String product_name, int cost_price, int selling_price, int stock,
			ProductType productType) {

		this.orders = new LinkedHashSet<>();
		this.profitCalculator = new Profit();
		setProductId(productId);
		setProductType(productType);
		setProduct_name(product_name);
		setCostPrice(cost_price);
		setSellingPrice(selling_price);
		setStock(stock);
	}

	public void setProductType(ProductType productType) {
		if (this.productType == null) {
			this.productType = productType;
		}
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		if (productId == null || productId.isEmpty()) {
			throw new IllegalArgumentException("Product ID cannot be null or empty.");
		}
		this.productId = productId;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getCostPrice() {
		return cost_price;
	}

	public void setCostPrice(int cost_price) {
		if (cost_price <= 0) {
			throw new IllegalArgumentException("Cost price must be greater than 0.");
		}
		this.cost_price = cost_price;
	}

	public int getSellingPrice() {
		return selling_price;
	}

	public void setSellingPrice(int selling_price) {
		if (selling_price <= 0) {
			throw new IllegalArgumentException("Selling price must be greater than 0.");
		}
		this.selling_price = selling_price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		if (stock <= 0) {
			throw new IllegalArgumentException("Stock must be greater than 0.");
		}
		this.stock = stock;
	}

	public Profit getTheProfitCalculator() {
		return this.profitCalculator;
	}

	public int getProductProfit() {
		return profitCalculator.calculateTotalProductProfit(this);
	}

	public ProductType getProductType() {
		return productType;
	}

	public boolean isOrderIdExists(String orderId) {
		for (OrderDetailsFacade orderDetilsId : orders) {
			if (orderDetilsId.getOrderId().equals(orderId)) {
				return true;
			}
		}
		return false;
	}

	public void removeAllOrders() {
		orders.clear();
	}

	public boolean addOrderDetails(OrderDetailsFacade detailsFacade) {

		if (detailsFacade == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}
		if (isOrderIdExists(detailsFacade.getOrderId())) {
			throw new IllegalArgumentException("Order with ID " + detailsFacade.getOrderId() + " already exists");
		}
		int quantity = detailsFacade.getQuantity();

		if (detailsFacade.getOrder().getProductAdapter().getProductId().equals(this.productId)) {
			OrderDetailsFacade details = detailsFacade;
			orders.add(details);
			this.stock -= quantity;
			return true;
		} else
			throw new IllegalArgumentException("Mismatch product");
	}



	public void removeOrder(String orderId, int quantity) {
		int newStock;
		if (isOrderIdExists(orderId)) {
			OrderDetailsFacade order = getOrder(orderId);
			newStock = this.stock + quantity;
			setStock(newStock);
			orders.remove(order);
		}
	}

	public OrderDetailsFacade getOrder(String orderId) {
		Iterator<OrderDetailsFacade> iterator = orders.iterator();
		while (iterator.hasNext()) {
			OrderDetailsFacade orderDetail = iterator.next();
			if (orderDetail.getOrder().getOrderId().equals(orderId)) {
				return orderDetail;
			}
		}
		return null;
	}

	public LinkedHashSet<OrderDetailsFacade> getAllOrders() {
		return orders;
	}

	public boolean checkStock(int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity cannot be negative.");
		}
		if (stock < quantity) {
			throw new IllegalStateException("Not enough in stock for product: " + product_name);
		}
		return true;
	}
	


	public ProductAdapter getAdapter() {
		return new ProductAdapter(this);
	}

	public int getRate() {
		String currencySymbol = getCurrencySymbol();
		return CurrencyRateManager.getRate(currencySymbol);
	}

	public abstract String getCurrencySymbol();

	public void printOrders() {
		if (orders.isEmpty()) {
			System.out.println("No orders for this product.");
			return;
		}

		System.out.println("orderList:");
		int orderNumber = 1;
		for (OrderDetailsFacade orderDetails : orders) {
			System.out.println("Order num: " + orderNumber++);
			System.out.println(orderDetails);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("Type: ").append(getProductType()).append("\n");
		sb.append("Product ID: ").append(getProductId()).append("\n");
		sb.append("Product name: ").append(getProduct_name()).append("\n");
		sb.append("Product selling price: ").append(getSellingPrice()).append(getCurrencySymbol()).append("\n");
		sb.append("Product cost: ").append(getCostPrice()).append(getCurrencySymbol()).append("\n");
		sb.append("Product stock: ").append(getStock()).append("\n");
		sb.append("The number of existing orders: ").append(orders.size()).append("\n");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, productType, product_name, cost_price, selling_price, stock);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return Objects.equals(productId, other.productId) && productType == other.productType
				&& Objects.equals(product_name, other.product_name) && cost_price == other.cost_price
				&& selling_price == other.selling_price && stock == other.stock && Objects.equals(orders, other.orders);
	}

}
