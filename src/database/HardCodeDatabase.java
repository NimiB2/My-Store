package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import SISManager.StoreManagement;
import enums.ProductType;
import enums.ShippingCompanyType;
import enums.ShippingType;
import factories.ProductFactory;
import interfaces.AutomaticSystemCommand;
import interfaces.Shippable;
import models.Customer;
import models.Product;
import models.ShippingCompany;
import shipping.DHL;
import shipping.FedEx;
import shipping.Shipping;
import shipping.ShippingInfo;

public class HardCodeDatabase implements AutomaticSystemCommand {
	private StoreManagement storeManagement;

	public HardCodeDatabase(StoreManagement storeManagement) {
		this.storeManagement = storeManagement;
	}

	@Override
	public void execute() {
		List<Product> products = createProducts();

		List<ShippingCompany> shippingCompanies = createShippingCompanies();

		addProductsToStoreManagement(storeManagement, products);
		addShippingCompanies(shippingCompanies, storeManagement);
		createOrders(storeManagement);
	}

	private List<Product> createProducts() {
		final int destImportTax = 20;
		List<Product> products = new ArrayList<>();

		ShippingInfo shippingInfo1 = new ShippingInfo(ShippingType.STANDARD, "USA", destImportTax, 100, 80);
		ShippingInfo shippingInfo2 = new ShippingInfo(ShippingType.EXPRESS, "UK", destImportTax, 150, 90);
		ShippingInfo shippingInfo3 = new ShippingInfo(ShippingType.STANDARD, "Canada", destImportTax, 120, 70);

		Product web1 = ProductFactory.createProduct("webA", ProductType.WEBSITE, "Web Product 1", 50, 80, 100);
		Product web2 = ProductFactory.createProduct("webD", ProductType.WEBSITE, "Web Product 2", 70, 100, 150);
		Product web3 = ProductFactory.createProduct("webF", ProductType.WEBSITE, "Web Product 3", 60, 90, 120);

		products.add(web1);
		products.add(web2);
		products.add(web3);

		Product store1 = ProductFactory.createProduct("storeA", ProductType.STORE, "Store Product 1", 40, 60, 80);
		Product store2 = ProductFactory.createProduct("storeD", ProductType.STORE, "Store Product 2", 45, 70, 90);
		Product store3 = ProductFactory.createProduct("storeF", ProductType.STORE, "Store Product 3", 55, 80, 100);

		products.add(store1);
		products.add(store2);
		products.add(store3);

		Product wholesale1 = ProductFactory.createProduct("wholesaleA", ProductType.WHOLESALERS, "Wholesale Product 1",
				30, 50, 200);
		Product wholesale2 = ProductFactory.createProduct("wholesaleD", ProductType.WHOLESALERS, "Wholesale Product 2",
				25, 40, 180);
		Product wholesale3 = ProductFactory.createProduct("wholesaleF", ProductType.WHOLESALERS, "Wholesale Product 3",
				35, 60, 220);

		products.add(wholesale1);
		products.add(wholesale2);
		products.add(wholesale3);

		List<ShippingInfo> websiteShippingInfoList = Arrays.asList(shippingInfo1, shippingInfo2, shippingInfo3);

		int websiteShippingInfoIndex = 0;
		for (Product product : products) {
			if (websiteShippingInfoIndex < websiteShippingInfoList.size()) {
				ShippingInfo websiteShippingInfo = websiteShippingInfoList.get(websiteShippingInfoIndex);
				if (product instanceof Shippable) {
					Shippable temp = (Shippable) product;
					temp.setShippingInfo(websiteShippingInfo);
				}
				websiteShippingInfoIndex++;
			} else {
				websiteShippingInfoIndex = 0;
			}
		}
		return products;
	}

	private List<Customer> createCustomers() {
		List<Customer> customers = new ArrayList<>();

		Customer customer1 = new Customer("Customer 1", "050-005500");
		Customer customer2 = new Customer("Customer 2", "050-005501");
		Customer customer3 = new Customer("Customer 3", "050-005502");
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);

		return customers;
	}

	private List<ShippingCompany> createShippingCompanies() {
		List<ShippingCompany> shippingCompanies = new ArrayList<>();
		ShippingCompany dhl = new DHL(ShippingCompanyType.DHL, "DHL Contact", "DHL WhatsApp");
		ShippingCompany fedex = new FedEx(ShippingCompanyType.FED_EX, "FedEx Contact", "FedEx WhatsApp");
		shippingCompanies.add(fedex);
		shippingCompanies.add(dhl);
		return shippingCompanies;
	}

	private void createOrders(StoreManagement storeManagement) {
		Map<String, Product> products = storeManagement.getAllProducts();
		List<Customer> customers = createCustomers();

		for (Product product : products.values()) {

			for (Customer customer : customers) {
				String orderId = generateOrderId(product, customer);
				int quantity = getRandomQuantity();
				Product product1 = storeManagement.getProductById(product.getProductId());
				Shipping shipping = null;
				if (product1 instanceof Shippable) {
					Shippable shippableProduct = (Shippable) product1;
					ShippingInfo shippingInfo = shippableProduct.getShipmentInfo();
					ShippingType shippingType = getRandomShippingType(shippingInfo);
					shipping = storeManagement.checkShippable(shippingInfo, shippingType);

				}
				if(!(storeManagement.addOrderToProduct(orderId, product1, shipping, customer, quantity))) {
					customer = customers.get(customers.size() - 1);
				}
			}
		}
	}

	private ShippingType getRandomShippingType(ShippingInfo shippingInfo) {
		ShippingType randomShippingType = null;
		Set<ShippingType> shipments = shippingInfo.getshipments();

		if (!shipments.isEmpty()) {
			List<ShippingType> shippingTypes = new ArrayList<>(shipments);
			Random random = new Random();
			randomShippingType = shippingTypes.get(random.nextInt(shippingTypes.size()));
		} else {
			System.out.println("No available shipping types for the product.");
		}

		return randomShippingType;
	}

	private void addProductsToStoreManagement(StoreManagement storeManagement, List<Product> products) {
		for (Product product : products) {
			storeManagement.addProduct(product.getProductId(), product);
		}
	}

	private void addShippingCompanies(List<ShippingCompany> shippingCompanies, StoreManagement storeManagement) {
		for (ShippingCompany company : shippingCompanies) {
			storeManagement.addCompany(company);
		}
	}

	private String generateOrderId(Product product, Customer customer) {
		int i = 1;
		return product.getProductId() + "_" + customer.getCustomer_name() + "_order" + (i++);
	}

	private int getRandomQuantity() {
		return (int) (Math.random() * 10) + 1;
	}

}
