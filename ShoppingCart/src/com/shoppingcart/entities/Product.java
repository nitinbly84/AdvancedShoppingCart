package com.shoppingcart.entities;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 * This class represents the unique Product items.
 */
public class Product {

	private String prodID;
	private String name;
	private double price;
	
	public String getProdID() {
		return prodID;
	}
	public void setProdID(String prodID) {
		this.prodID = prodID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public int hashCode() {
		return prodID.hashCode();
	}
	
	@Override
	public boolean equals(Object prod) {
		return ((Product)prod).prodID.equalsIgnoreCase(this.prodID);
	}
}
