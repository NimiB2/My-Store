package interfaces;

import enums.ShippingType;
import models.ShippingCompany;

public interface ShippingObserver {
	void update(ShippingCompany company, int cost, ShippingType shippingType);
}
