package factories;

import enums.ShippingCompanyType;
import models.ShippingCompany;
import shipping.DHL;
import shipping.FedEx;

public class ShippingCompanyFactory {

	public static ShippingCompany createShippingCompany(ShippingCompanyType companyType, String name, String contact,
			String whatsApp) {
		switch (companyType) {
		case DHL:
			return new DHL(companyType, contact, whatsApp);
		case FED_EX:
			return new FedEx(companyType, contact, whatsApp);

		default:
			throw new IllegalArgumentException("Unsupported shipping company type: " + companyType);
		}
	}
}
