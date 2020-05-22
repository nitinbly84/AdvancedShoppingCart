package com.shoppingcart.executors;

import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.shoppingcart.billing.Billing;
import com.shoppingcart.entities.Bill;
import com.shoppingcart.utilities.CheckFile;
import com.shoppingcart.utilities.MessageWriter;
import com.shoppingcart.utilities.ReadConfigurations;
import com.shoppingcart.utilities.StackCommands;

/**
 * @author Nitin Agrawal
 * @Date 25-Apr-2020
 * Generates the list of bills after processing the input file for the given
 * counter & person name at that billing counter
 */
public class BillProcessor {

	private String currency = null;

	private boolean isNew = true;
	private BillRetriever billRetriever;
	private StackCommands commands;
	private static String logFileName = "BillProcessor.txt";
	private static MessageWriter messageWriter = new MessageWriter(logFileName);

	public BillProcessor() {
		billRetriever = new BillRetriever();
		commands = new StackCommands();
	}
	
	public BillProcessor processBills(String folderPath, String extension, String counter, String name) {
		try {
			Stream<Path> browse = Files.walk(Paths.get(folderPath));
			browse.filter(Files::isRegularFile)
				  .map(inputFile -> inputFile.toString())
				  .filter(fileName -> CheckFile.checkFileExtension(fileName, extension))
				  .forEach(inputFile -> processBills(this, inputFile, counter, name));
			browse.close();
		} catch (Exception e) {
			messageWriter.writeToFile(e.getMessage());
			messageWriter.writeToFile(e.getStackTrace());
		}
		return this;
	}
	
	private void processBills(BillProcessor billProcessor, String inputFile, String counter, String name) {
		billProcessor = processBills(inputFile, counter, name);		
	}
	
	public BillProcessor processBills(String inputFile, String counter, String name) {
		if(counter == null || counter.isEmpty() || name == null || name.isEmpty()) {
			System.out.println("Provide valid values for counter & name...");
		}
		if(isNew) {
			commands.stackCommands(inputFile, counter, name);
			return this;
		}

		Billing billing = new Billing(counter);
		List<Billing> billings = billRetriever.getAllCounterBilling().get(counter);
		if(billings == null) {
			billings = new ArrayList<>();
			billRetriever.getAllCounterBilling().put(counter, billings);
		}
		billings.add(billing);
		billing.setIncharge(name);

		List<String> data = new ReadConfigurations().readInputTestData(inputFile);
		DecimalFormat df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.UP);
		for(String line : data) {
			String[] vals = line.split(":");
			char[] chars = vals[1].toCharArray();
			if(currency == null) {
				int i = 0;
				for(char ch : chars) {
					if(ch >=48 && ch < 57)
						break;
					i++;
				}
				currency = vals[1].substring(0, i);
			}
			try {
				vals[1] = vals[1].replace(",", "");
				String val = vals[1].replace(currency, "");
				vals[1] = currency+df.format(Double.valueOf(val));
				Bill bill = new Bill(vals[0], vals[1], counter);
				billRetriever.getBills().add(bill);
				billing.getBills().add(bill);
			} catch (Exception e) {
				messageWriter.writeToFile(e.getMessage());
				messageWriter.writeToFile(e.getStackTrace());
			}
		};

		for(Bill bill : billRetriever.getBills()) {
			double org = Double.parseDouble(bill.getOrg().replace(currency, ""));
			String finalAmount = currency+billing.doBilling(bill.getType(), org);
			bill.setFinalAmount(finalAmount);
		}
		return this;
	}

	public BillRetriever process() {
		isNew = false;
		isNew = commands.executeCommands(this);
		return billRetriever;
	}
}
