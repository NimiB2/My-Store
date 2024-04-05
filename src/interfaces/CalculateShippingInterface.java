package interfaces;

import shipping.ShippingInfo;

public interface CalculateShippingInterface {

	int calculateExpressCost(ShippingInfo info);

	int calculateStandardCost(ShippingInfo info);
}
