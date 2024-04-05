package interfaces;

import models.Product;

public interface OrderCommand {
	void execute(Product product);
}
