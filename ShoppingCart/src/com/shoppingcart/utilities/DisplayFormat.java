package com.shoppingcart.utilities;

import java.util.List;

import com.shoppingcart.billing.Billing;
import com.shoppingcart.entities.Bill;

/**
 * @author Nitin Agrawal
 * @Date 25-Apr-2020
 * Prints the bills details on console in the given format
 */
public class DisplayFormat {
	
	public static void displayOnConsole(List<Bill> bills) {
		System.out.println(String.format("%-15s %-3s %-17s %-3s %-5s", "CustomerType", "|", "PurchaseAmount", "|", "BillAmount"));
		System.out.println("------------------------------------------------------");
		bills.forEach(bill -> {
			System.out.println(String.format("%-15s %-5s %-15s %-3s %-5s", bill.getType(), "|", bill.getOrg(), "|", bill.getFinalAmount()));
			System.out.println("------------------------------------------------------");
		});
	}
	
	public static void displayOne(Bill bill) {
		System.out.println(String.format("%-15s %-4s %-13s %-5s %-14s %-4s %-4s", bill.getType(), "|", bill.getBillId(), "|", bill.getOrg(), "|", bill.getFinalAmount()));
		System.out.println("----------------------------------------------------------------------");
	}
	
	public static void displayHeader() {
		System.out.println(String.format("%-15s %-5s %-12s %-3s %-16s %-3s %-2s", "CustomerType", "|", "Bill#", "|", "PurchaseAmount", "|", "BillAmount"));
		System.out.println("----------------------------------------------------------------------");
	}
	
	public static void displayBills(Billing billing) {
		billing.getBills()
			   .stream()
			   .forEach(bill -> DisplayFormat.displayOne(bill));
	}
}
