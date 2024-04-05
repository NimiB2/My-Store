package mainProgram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import SISManager.StoreManagement;
import database.HardCodeDatabase;
import database.Menu;
import enums.ProductType;
import enums.ShippingType;
import exceptions.MainException;
import facade.OrderDetailsFacade;
import factories.ProductFactory;
import interfaces.Shippable;
import memento.StoreManagementCaretaker;
import models.Customer;
import models.Product;
import shipping.Shipping;
import shipping.ShippingInfo;

public class Program {
	static Scanner scanner = new Scanner(System.in);
	static final int TAX = 20;
	static StoreManagementCaretaker caretacker = new StoreManagementCaretaker();
	static MainException me = new MainException();

	public static void main(String[] args) {
		StoreManagement storeManagement = StoreManagement.getInstance();
		String option;
		boolean exit = false;
				do {
				    try {
				        System.out.println(Menu.theMenu());
				        option = scanner.nextLine();

				        switch (option.toLowerCase()) {
				            case "1":
				                automaticSystem(storeManagement);
				                break;
				            case "2":
				                creationProduct(storeManagement);
				                break;
				            case "10":
				                storeManagement.saveState();
				                break;
				            case "11":
				                storeManagement.restoreState();
				                break;
				            case "3":
				                if (!checkIfStoreHaveProducts(storeManagement)) {
				                    break;
				                }
				                removingProduct(storeManagement);
				                break;
				            case "4":
				                if (!checkIfStoreHaveProducts(storeManagement)) {
				                    break;
				                }
				                updateStock(storeManagement);
				                break;
				            case "5":
				                if (!checkIfStoreHaveProducts(storeManagement)) {
				                    break;
				                }
				                createNewOrder(storeManagement);
				                break;
				            case "6":
				                if (!checkIfStoreHaveProducts(storeManagement)) {
				                    break;
				                }
				                undo(storeManagement);
				                break;
				            case "7":
				                if (!checkIfStoreHaveProducts(storeManagement)) {
				                    break;
				                }
				                printOneProduct(productSelection(storeManagement));
				                break;
				            case "8":
				            	  if (!checkIfStoreHaveProducts(storeManagement)) {
					                    break;
					                }
				                showingAllProducts(storeManagement);
				                break;
				            case "9":
				                if (!checkIfStoreHaveProducts(storeManagement)) {
				                    break;
				                }
				                showProductOrders(productSelection(storeManagement));
				                break;
				            case "e":
				                exit = true;
				                break;
				            default:
				                System.out.println("Invalid option! Please select a valid option.");
				                break;
				        }
				    } catch (NullPointerException | IllegalArgumentException | IllegalStateException e) {
				        System.out.println("Error: " + e.getMessage());
				    } catch (Exception e) {
				        System.out.println("General Exception");
				    } 
				} while (!exit);

	}

	// option 1
	public static void automaticSystem(StoreManagement storeManagement) {
		HardCodeDatabase hardCodeDatabase = new HardCodeDatabase(storeManagement);
		hardCodeDatabase.execute();
		printStoreProducts(storeManagement);
	}

	// option 2
	public static void creationProduct(StoreManagement storeManagement) {
		ShippingType selectedShippingType;
		ProductType userType;
		Product newProduct;
		String productId;
		String product_name;
		int cost_price;
		int selling_price;
		int stock;
		int shippingTypeCounter = ShippingType.values().length;
		boolean answer = true;

		userType = choosingProductType();
		System.out.println("Great, now we'll take some product details from you");
		System.out.println("Enter the product name:");
		System.out.println("User input: <String> for product name.");
		product_name = scanner.nextLine();
		System.out.println("Enter the product ID:");
		System.out.println("User input: <String> for product ID.");
		productId = scanner.nextLine();
		System.out.println("Enter the product cost price:");
		System.out.println("User input: <int> for product cost price.");
		cost_price = me.intException(scanner);
		System.out.println("Enter the product selling price:");
		System.out.println("User input: <int> for product selling price.");
		selling_price = me.intException(scanner);
		System.out.println("Enter the product stock:");
		System.out.println("User input: <int> for product stock amount.");
		stock = me.intException(scanner);

		// add product
		newProduct = ProductFactory.createProduct(productId, userType, product_name, cost_price, selling_price, stock);
		if (newProduct instanceof Shippable) {

			String destCountry;
			int destImportTax;
			int weight;
			List<ShippingType> typesList = new ArrayList<>();

			System.out.println("Enter the destination country of the product:");
			System.out.println("User input: <String> for destination country.");
			destCountry = scanner.nextLine();
			System.out.println("Enter the product weight:");
			System.out.println("User input: <int> for product weight.");
			weight = me.intException(scanner);
			// scanner.nextLine();
			destImportTax = TAX;

			selectedShippingType = chooseShippingType(typesList, false);
			typesList.add(selectedShippingType);
			shippingTypeCounter--;
			ShippingInfo info = new ShippingInfo(selectedShippingType, destCountry, destImportTax, weight,
					selling_price);

			while (shippingTypeCounter > 0 && answer) {
				System.out.println("Would you like to add another shipping type? answer by y/n");
				System.out.println("User input: <(char) y OR (char) n> based on user needs.");
				answer = checkAnswer();
				if (answer) {
					ShippingType selectedShippingType2 = chooseShippingType(typesList, true);
					typesList.add(selectedShippingType);
					info.addShippingType(selectedShippingType2);
					shippingTypeCounter--;
				}

			}
			Shippable newProduct1 = (Shippable) newProduct;
			newProduct1.setShippingInfo(info);
			// set info to product
		} else {
			newProduct = ProductFactory.createProduct(productId, userType, product_name, cost_price, selling_price,
					stock);
		}
		storeManagement.addProduct(productId, newProduct);
		printStoreProducts(storeManagement);

	}

	// option 3
	public static void removingProduct(StoreManagement storeManagement) {
		String id;
		printStoreProducts(storeManagement);

		System.out.println("Enter the product ID:");
		System.out.println("User input: <String> for product ID.");
		id = scanner.nextLine();
		if (storeManagement.getProductById(id) != null) {
			storeManagement.removeProduct(id);
			printStoreProducts(storeManagement);
		}
	}

	// option 4
	public static void updateStock(StoreManagement storeManagement) {
		Product product;
		int newStock;

		product = productSelection(storeManagement);
		if (product != null) {
			System.out.println("Insert the new stock:");
			System.out.println("User input: <int> for new stock amount.");
			newStock = me.intException(scanner); // Added intException.
			if (storeManagement.updateStock(product.getProductId(), newStock)) {
				System.out.println("Updated stock");
				System.out.println(product);
			}
		}
	}

	// option 5
	public static void createNewOrder(StoreManagement storeManagement) {
		Product product;
		Shipping shipping = null;
		Customer customer;
		String productId;
		String orderId;
		ProductType userType;
		int userQuantity;
		boolean isNewOrder = false;
		boolean hasStock = false;

		customer = createNewCustomer();

		userType = choosingProductType();
		System.out.println("These are the products that exist of this type:");
		printAllProductsByType(storeManagement, userType);

		System.out.println("Enter the ID of the product you want:");
		System.out.println("User input: <String> for product ID.");
		productId = scanner.nextLine();
		product = storeManagement.getProductById(productId);

		if (product != null && product.getProductType() == userType) {
			System.out.println("Enter the quantity you would like to purchase:");
			System.out.println("User input: <int> for quantity.");
			userQuantity = me.intException(scanner);

			hasStock = product.checkStock(userQuantity);

			if (hasStock) {
				System.out.println("Insert ID for the new order:");
				System.out.println("User input: <String> for new order ID.");
				orderId = scanner.nextLine();

				shipping = checkShippable(storeManagement, product, shipping);
				isNewOrder = storeManagement.addOrderToProduct(orderId, product, shipping, customer, userQuantity);

				if (isNewOrder) {
					System.out.println(product);
				}
			}
		} else {
			System.out.println(
					"No product with ID '" + productId + "' found or it does not match the selected product type.");
		}
	}

	private static Customer createNewCustomer() {
		String customer_name;
		String mobile;

		System.out.println("Enter your name:");
		System.out.println("User input: <String> for new customer name.");
		customer_name = scanner.nextLine();
		System.out.println("Insert your phone:");
		System.out.println("User input: <String> for new customer phone number.");
		mobile = scanner.nextLine();

		Customer customer = new Customer(customer_name, mobile);

		return customer;
	}

	public static Shipping checkShippable(StoreManagement storeManagement, Product product, Shipping shipping) {
		@SuppressWarnings("unused")
		ShippingType cheapestShippingType;
		ShippingType selectedShippingType;
		Set<ShippingType> shipments;
		ShippingInfo info;

		if (product instanceof Shippable) {
			Shippable productShippable = (Shippable) product;
			info = productShippable.getShipmentInfo();
			shipments = info.getshipments();

			if (shipments.size() > 1) {
				selectedShippingType = chooseShippingType(shipments, false);
			} else {
				System.out.println("This product has one shipping option:");
				System.out.println(shipments);

				selectedShippingType = shipments.iterator().next();
				shipping = storeManagement.checkShippable(info, selectedShippingType);

				System.out.println("Order shipping placed successfully.");

			}
		}

		return shipping;
	}

	// option6
	public static void undo(StoreManagement storeManagement) {
		storeManagement.undoOrder();
	}

	// option7
	public static void printOneProduct(Product product) {

		if (product != null) {
			System.out.println("Product Details:");
			System.out.println(product);

			showProductOrders(product);
			presentationInvoices(product.getAllOrders());

		}

	}

	public static void presentationInvoices(LinkedHashSet<OrderDetailsFacade> allOrders) {
		Iterator<OrderDetailsFacade> iterator = allOrders.iterator();

		while (iterator.hasNext()) {
			OrderDetailsFacade orderDetails = iterator.next();
			System.out.println(orderDetails.printInvoices());
		}
	}

	// option 8
	public static void showingAllProducts(StoreManagement storeManagement) {
		int totalProfit = 0;
		Map<String, Product> allProducts = storeManagement.getAllProducts();
		if (allProducts != null) {
			System.out.println("All existing products:");

			for (Map.Entry<String, Product> entry : allProducts.entrySet()) {
				Product product = entry.getValue();
				printOneProduct(product);
				totalProfit += product.getProductProfit();
			}
			System.out.println("The total profit of the store= " + totalProfit);
		} else {
			System.out.println("No products exsist");
		}

	}

	// option 9
	public static void showProductOrders(Product product) {
		if (product != null) {
			System.out.println("Product orders:");
			product.printOrders();
			System.out.print("Total profit from orders: ");
			System.out.println(product.getTheProfitCalculator().calculateTotalProductProfit(product));
		}
	}

	// funcs

	public static Product productSelection(StoreManagement storeManagement) {
		String id;

		printStoreProducts(storeManagement);
		System.out.println("Enter the ID of the product you want:");
		System.out.println("User input: <String> for needed product ID.");
		id = scanner.nextLine();
		Product product = storeManagement.getProductById(id);
		if (product == null) {
			System.out.println("The product does not exist");
		}

		return product;
	}

	public static void printAllProductsByType(StoreManagement storeManagement, ProductType type) {
		storeManagement.printAllProductsByType(type);

	}

	public static void printStoreProducts(StoreManagement storeManagement) {
		for (ProductType type : ProductType.values()) {
			printAllProductsByType(storeManagement, type);
		}
	}

	public static ShippingType chooseShippingType(Collection<ShippingType> currentShippingTypes, boolean state) {
		String input;

		System.out.println("Select a shipping type:");
		for (ShippingType type : ShippingType.values()) {
			if (state) {
				if (!currentShippingTypes.contains(type)) {
					System.out.println("- " + type);
				}
			} else {
				System.out.println("- " + type);
			}

		}
		input = scanner.nextLine().toUpperCase();
		while (!isValidShippingType(input)) {
			System.out.println("Invalid shipping type! Please enter a valid product type.");
			input = scanner.nextLine().toUpperCase();
		}
		return ShippingType.valueOf(input);
	}

	public static ProductType choosingProductType() throws IllegalArgumentException {
		ProductType userType;
		String userChoice;

		System.out.println("These are the types in which the products are sold:");
		System.out.println("User input: <String> for product type");
		for (ProductType typeOptions : ProductType.values()) {
			System.out.println("- " + typeOptions);
		}
		System.out.println("Enter the type you want: ");
		System.out.println("User input: <String> for choosing product type.");
		userChoice = scanner.nextLine().toUpperCase();
		while (!isValidProductType(userChoice)) {
			System.out.println("Invalid product type! Please enter a valid product type.");
			userChoice = scanner.nextLine().toUpperCase();
		}
		userType = ProductType.valueOf(userChoice);
		return userType;
	}

	public static boolean checkAnswer() {
		String input = scanner.nextLine().toLowerCase();

		while (!input.equals("y") && !input.equals("n")) {
			System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no:");
			input = scanner.nextLine().toLowerCase();
		}
		return input.equals("y");
	}

	public static boolean checkIfStoreHaveProducts(StoreManagement storeManagement) {
		if (!storeManagement.getAllProducts().isEmpty()) {
			return true;
		} else {
			System.out.println("There are no products in the store");
			return false;
		}
	}

	public static boolean isValidProductType(String productType) {
		for (ProductType type : ProductType.values()) {
			if (type.name().equalsIgnoreCase(productType)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidShippingType(String shippingType) {
		for (ShippingType type : ShippingType.values()) {
			if (type.name().equalsIgnoreCase(shippingType)) {
				return true;
			}
		}
		return false;
	}
}
