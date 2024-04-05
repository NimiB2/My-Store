package shipping;

import enums.ShippingCompanyType;
import models.ShippingCompany;
import utilities.Values;

public class DHL extends ShippingCompany {

	public DHL(ShippingCompanyType companyType, String contact, String whatsApp) {
		super(companyType, contact, whatsApp);
	}

	@Override
	public int calculateExpressCost(ShippingInfo info) {
		return Values.DHL_SHIPPING_EXPRESS_TAX * Values.USD_RATE + info.getDestImportTax();
	}

	@Override
	public int calculateStandardCost(ShippingInfo info) {
int temp = (int) (info.getSellingPrice() * Values.USD_RATE * Values.DHL_SHIPPING_STANDARD_TAX_PRECENT);
		if (temp > Values.DHL_SHIPPING_MAX) {
			temp = Values.DHL_SHIPPING_MAX;
		}
		return temp;

	}
}
