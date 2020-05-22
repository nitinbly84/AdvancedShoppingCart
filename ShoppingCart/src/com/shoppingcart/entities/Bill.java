package com.shoppingcart.entities;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 */
public class Bill {
	private static int count = 0;
	private String billId;
	private String type;
	private String org;
	private String finalAmount;
	
	public Bill(String type, String org, String counter) {
		count++;
		this.billId = "Bill#"+count+"_"+counter;
		this.type = type;
		this.org = org;
	}
	
	public String getBillId() {
		return billId;
	}
	
	public String getType() {
		return type;
	}
	
	public String getOrg() {
		return org;
	}
	
	public void setFinalAmount(String finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getFinalAmount() {
		return finalAmount;
	}	
}
