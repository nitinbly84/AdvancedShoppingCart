package com.shoppingcart.billing;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.shoppingcart.entities.Bill;
import com.shoppingcart.entities.Product;
import com.shoppingcart.entities.ShoppingCart;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 * This class will store the Billing details like
 * Which billing counter is used by which person &
 * what items were billed & what amount was billed.
 * Once Billing counter get its id or name then it
 * will not be changed later. Only the incharge will
 * change during the time.
 * Every counter will have its own instance of
 * CalculateDiscount to calculate the discount, to
 * apply different discounts to different counters,
 * if required.
 * Note : double has been used to represent money here,
 * but it is not the correct way & BigDecimal or Money
 * classes need to be used here.
 */
public class Billing {
	
	private String billingCounter;
	private double totalPrice = 0d;
	private Map<ShoppingCart, String> carts;
	private String incharge;
	private CalculateDiscount cd;
	private List<Bill> bills = new ArrayList<>();
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	public Billing(String counterName) {
		this.billingCounter = counterName;
		cd = new CalculateDiscount();
		df.setRoundingMode(RoundingMode.UP);
	}

	public String getBillingCounter() {
		return billingCounter;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Map<ShoppingCart, String> getCarts() {
		return carts;
	}

	public void setCarts(Map<ShoppingCart, String> carts) {
		this.carts = carts;
	}

	public String getIncharge() {
		return incharge;
	}

	public void setIncharge(String incharge) {
		this.incharge = incharge;
	}
	
	public List<Bill> getBills() {
		return bills;
	}
	
	/**
	 * This method will read the items from the cart & will
	 * calculate the bill after applying the required discounts.
	 * Once billing is done the cart is emptied.
	 * @param cart
	 * @return
	 */
	public String doBilling(ShoppingCart cart) {
		Set<Product> products = cart.getAllProducts();
		products.forEach(a -> totalPrice+=a.getPrice());
		cart.deleteCart();
		return df.format(totalPrice - cd.calculateDiscount(cart.getCustomerType(), totalPrice));
	}
	
	/**
	 * This method will calculate the bill amount for the customer type
	 * after applying applicable discounts. 
	 * @param type
	 * @param amount
	 * @return
	 */
	public String doBilling(String type, double amount) {
		return df.format(amount - cd.calculateDiscount(type, amount));
	}
}

