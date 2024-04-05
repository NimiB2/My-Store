package interfaces;

import shipping.ShippingInfo;

public interface Shippable {
	ShippingInfo getShipmentInfo();
	void setShippingInfo(ShippingInfo info);
}
