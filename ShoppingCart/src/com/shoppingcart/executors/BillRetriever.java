package com.shoppingcart.executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shoppingcart.billing.Billing;
import com.shoppingcart.entities.Bill;

/**
 * @author Nitin Agrawal
 * @Date 02-May-2020
 * This class will fetch the details of processed bills
 */
public class BillRetriever {
	
	private List<Bill> bills = new ArrayList<>();
	// Below map works as in-memory DB for the bills information & counter information
	private static Map<String, List<Billing>> counterBills = new HashMap<>();
	
	public List<Bill> getBills() {
		return bills;
	}

	public List<Billing> getCounterBilling(String counter) {
		List<Billing> list = null;
		if((list = counterBills.get(counter)) == null)
			list = new ArrayList<>();
		return list;
	}

	public Map<String,List<Billing>> getAllCounterBilling() {
		return counterBills;
	}
}
