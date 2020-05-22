package com.shoppingcart.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 * It represents the cart to store the items selected by a customer.
 * Every cart is created once & then same cart will be used by different
 * customers & types for different items in the cart.
 */
public class ShoppingCart {
	
	private Set<Product> cart;
	private String cartID;
	private String customerType;
	
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public ShoppingCart(String cartID) {
		this.cartID = cartID;
	}
	
	public String getCartID() {
		return cartID;
	}

	public ShoppingCart() {
		cart = new HashSet<>();
	}
	
	public void addProduct(Product product) {
		cart.add(product);
	}
	
	public Set<Product> getAllProducts() {
		return cart;
	}
	
	public boolean deleteProductFromCart(Product product) {
		return cart.remove(product);
	}
	
	public void deleteCart() {
		cart = null;
	}
}
