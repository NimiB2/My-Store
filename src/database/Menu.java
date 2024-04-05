package database;

public class Menu {

	public static String theMenu() {
		String menu;
		StringBuilder sb = new StringBuilder();
		System.out.println("------ MAIN MENU ------");
		sb.append("[1]. ").append("Automatic system").append("\n");
		sb.append("[2]. ").append("Adding a product").append("\n");
		sb.append("[3]. ").append("Removing a product").append("\n");
		sb.append("[4]. ").append("Product stock update").append("\n");
		sb.append("[5]. ").append("Adding an order to a product").append("\n");
		sb.append("[6]. ").append("Undo - removing orders").append("\n");
		sb.append("[7]. ").append("Displaying the details of a product").append("\n");
		sb.append("[8]. ").append("Showing all the products in the store").append("\n");
		sb.append("[9]. ").append("Printing orders for a specific product").append("\n");
		sb.append("[10]. ").append("System backup").append("\n");
		sb.append("[11]. ").append("System recovery").append("\n");
		sb.append("Exit- press on E/e").append("\n");
		sb.append("\n Select the action you want to perform: ").append("\n");

		menu = sb.toString();
		return menu;
	}
}
