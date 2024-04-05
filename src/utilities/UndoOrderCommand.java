package utilities;

import interfaces.OrderCommand;
import models.Order;
import models.Product;

public class UndoOrderCommand implements OrderCommand {

	private Order order;

	public UndoOrderCommand(Order order) {
		this.order = order;
	}

	@Override
	public void execute(Product product) {
		if (product != null) {
			product.removeOrder(this.order.getOrderId(), this.order.getQuantity());
			System.out.println("The order was successfully cancelled");
		} else {
			System.out.println("Product not found for undo operation.");
		}
	}

}
