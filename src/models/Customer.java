package models;

import java.util.Objects;

public class Customer {
	private String customer_name;
	private String mobile;

	public Customer(String customer_name, String mobile) {
		setCustomer_name(customer_name);
		setMobile(mobile);
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		if (customer_name == null) {
			throw new NullPointerException("Customer name cannot be null.");
		}
		this.customer_name = customer_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		if (mobile == null) {
			throw new NullPointerException("Mobile cannot be null.");
		}
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "[customer_name= " + customer_name + ", mobile=" + mobile + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer_name, mobile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
		return Objects.equals(customer_name, other.customer_name) && Objects.equals(mobile, other.mobile);
	}

}
