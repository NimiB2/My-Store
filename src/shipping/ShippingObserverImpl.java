package shipping;

import enums.ShippingType;
import interfaces.ShippingObserver;
import models.ShippingCompany;

public class ShippingObserverImpl implements ShippingObserver {
	private int cheapestCost;
	private ShippingCompany cheapestCompany;
	private ShippingType shippingType;
	private boolean firstUpdate = true;

	public ShippingObserverImpl() {
	}

	@Override
	public void update(ShippingCompany company, int cost, ShippingType shippingType) {
		if (firstUpdate || cost < cheapestCost) {
			cheapestCost = cost;
			cheapestCompany = company;
			this.shippingType = shippingType;
			firstUpdate = false;
		}
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public ShippingCompany getCheapestCompany() {
		return cheapestCompany;
	}

	public int getCheapestCost() {
		return cheapestCost;
	}

	public int calculateShippingCost(ShippingCompany company, ShippingInfo info, ShippingType shippingType) {

		switch (shippingType) {
		case EXPRESS:
			return company.calculateExpressCost(info);
		case STANDARD:
			System.out.println(
					"You have to take care of the import taxes, because the store does not take care of it at this stage");
			return company.calculateStandardCost(info);
		default:
			throw new IllegalArgumentException("Invalid shipping type");
		}
	}
}
