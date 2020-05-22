package com.shoppingcart.billing;

import java.util.Comparator;

import com.shoppingcart.entities.Discounts;
import com.shoppingcart.utilities.ReadConfigurations;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 * This class calculates the applicable discounts for the particular
 * customer type & billing amount
 * Note : double has been used to represent money here,
 * but it is not the correct way & BigDecimal or Money
 * classes need to be used to represent Monetary figures.
 */
public class CalculateDiscount {

	private double discount;
	private double amount;
	
	public double calculateDiscount(String type, double amount) {
		discount = 0.0d;
		this.amount = amount;
		return getDiscount(type);
	}

	/**
	 * Calculates the discount for the given customer type.
	 * @param amount
	 * @return
	 */
	private double getDiscount(String customerType) {
		ReadConfigurations.getDiscountsFor(customerType.toUpperCase())
						  .stream()
						  .sorted(Comparator.reverseOrder())
						  .filter(dis -> amount >= dis.getLower())
						  .forEach(dis -> applyDiscount(dis));
		return discount;
	}
	
	private void applyDiscount(Discounts dis) {
		if(dis.getLower() < amount && (amount <= dis.getUpper() || dis.getUpper() < 1d)) {
			discount+=(amount-dis.getLower())*(dis.getDiscount()/100);
			amount = dis.getLower();
		} else if(dis.getLower() < amount && amount > dis.getUpper()) {
			discount+=(amount-dis.getUpper())*(dis.getDiscount()/100);
			amount = dis.getUpper();
		}
	}
}
