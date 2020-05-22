package com.shoppingcart.executors;

import com.shoppingcart.utilities.DisplayFormat;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 * This class is working as Test class to test the formatted results.
 */
public class ShoppingCartExecutor {

	public static void main(String[] args) {
		String[] inputFile = {
							  "/resources/InputTestData",
							  "/resources/InputTestData2",
							  "/resources/InputTestData3"
							  };
		String[] counterIncharge = {"Nitin", "Sam", "Ana"};
		String[] counters = {"1", "2", "3"};
		String newLinesDisplay = "\n\n";
		
		new ShoppingCartExecutor().processor(inputFile, counterIncharge, counters, newLinesDisplay);
	}
	
	public void processor(String[] inputFile, String[] counterIncharge, String[] counters, String newLinesDisplay) {
		BillProcessor billProcessor = new BillProcessor();
		// If want to read all the files in a directory or folder then give absolute path
		// like shown below.
		//.processBills("Absolute Path\\resources", null, "1", );
		BillProcessor pipeline = billProcessor.processBills(inputFile[0], counters[0], counterIncharge[0]);
		//									  .processBills("", "5", "Samel");
		//									  .processBills(inputFile[2], "2", "Ana");

		DisplayFormat.displayOnConsole(processBills(pipeline).getBills());
		
		// Use if just need to process the bills to use data later, this will not be re-evaluated if
		// the same pipeline is already processed, like it is already processed on line# 34
		processBills(pipeline);
		
		
		// Creating a new pipeline using same instance of BillProcessor like
		// above, processed commands will not be part of this pipeline
		// but as instance is same so data after processing of above command
		// will still be available. So if you don't need that previous processed
		// data & wants to have that memory then clear that data explicitly.
		pipeline = billProcessor.processBills(inputFile[1], counters[1], counterIncharge[1])
								.processBills(inputFile[2], counters[1], counterIncharge[2]);
		BillRetriever billRetriever = processBills(pipeline);

		display(billRetriever, counterIncharge[1], counters[1], newLinesDisplay);
		display(billRetriever, counterIncharge[2], counters[1], newLinesDisplay);
		display(billRetriever, counterIncharge[0], counters[0], newLinesDisplay);
		
		System.out.println(newLinesDisplay + "All Provided Bills Processed.....");
	}
	
	private BillRetriever processBills(BillProcessor pipeline) {
		if(pipeline == null) {
			System.out.println("Nothing to process....");
			return new BillRetriever();
		}
		return pipeline.process();
	}

	private void display(BillRetriever billRetriever, String counterIncharge, String counter, String newLines) {
		System.out.printf("%sBills processed by %s on counter %s :-\n", newLines, counterIncharge, counter);
		DisplayFormat.displayHeader();
		billRetriever.getCounterBilling(counter)
					 .stream()
					 .filter(billing -> (billing.getIncharge().equals(counterIncharge) && billing.getBillingCounter().equals(counter)))
					 .forEach(DisplayFormat::displayBills);
	}
}
