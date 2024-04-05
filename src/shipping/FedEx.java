package shipping;

import enums.ShippingCompanyType;
import models.ShippingCompany;
import utilities.Values;

public class FedEx extends ShippingCompany {

	public FedEx(ShippingCompanyType companyType, String contact, String whatsApp) {
		super(companyType, contact, whatsApp);
	}

	@Override
	public int calculateExpressCost(ShippingInfo info) {
		int tax = info.getDestImportTax();
		int totalWeight = info.getWeight();
		totalWeight /= Values.FED_EX_SHIPPING_WEIGHT_CHECK;
		return (Values.FED_EX_SHIPPING_EXPRESS_TAX * Values.USD_RATE) * totalWeight + tax;
	}

	@Override
	public int calculateStandardCost(ShippingInfo info) {
		int totalWeight = info.getWeight();
		totalWeight /= (Values.FED_EX_SHIPPING_STANDARD_TAX);

		return (Values.FED_EX_SHIPPING_EXPRESS_TAX * Values.USD_RATE) * totalWeight;
	}

}
