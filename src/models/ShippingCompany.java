package models;

import java.util.Objects;

import enums.ShippingCompanyType;
import interfaces.CalculateShippingInterface;

public abstract class ShippingCompany implements CalculateShippingInterface {
	protected ShippingCompanyType companyType;
	protected String contact;
	protected String WhatsApp;

	public ShippingCompany(ShippingCompanyType companyType, String contact, String whatsApp) {
		setShippingCompanyType(companyType);
		setContact(contact);
		setWhatsApp(whatsApp);
	}

	public ShippingCompanyType getShippingCompanyType() {
		return companyType;
	}

	public void setShippingCompanyType(ShippingCompanyType companyType) {
		if (companyType == null) {
			throw new IllegalArgumentException("Shipping company type cannot be null.");
		}
		this.companyType = companyType;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		if (contact == null || contact.isEmpty()) {
			throw new IllegalArgumentException("Contact information cannot be null or empty.");
		}
		this.contact = contact;
	}

	public String getWhatsApp() {
		return WhatsApp;
	}

	public void setWhatsApp(String whatsApp) {
		if (whatsApp == null || whatsApp.isEmpty()) {
			throw new IllegalArgumentException("WhatsApp information cannot be null or empty.");
		}
		WhatsApp = whatsApp;
	}

	@Override
	public String toString() {
		return " [The company =" + companyType + ", contact=" + contact + ", WhatsApp=" + WhatsApp + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(WhatsApp, companyType, contact);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ShippingCompany)) {
			return false;
		}
		ShippingCompany other = (ShippingCompany) obj;
		return Objects.equals(WhatsApp, other.WhatsApp) && companyType == other.companyType
				&& Objects.equals(contact, other.contact);
	}

}
