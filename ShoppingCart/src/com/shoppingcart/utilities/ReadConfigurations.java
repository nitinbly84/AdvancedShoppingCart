package com.shoppingcart.utilities;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.shoppingcart.entities.Discounts;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 * It reads the discount configuration file to get the discount rates to be applied
 * to calculate the discounts later. It also reads the input data file to read the
 * input data for the bills to process.
 */
public class ReadConfigurations {

	private static Map<String, List<Discounts>> discountTypes = new HashMap<>();
	private static Map<String, Discounts> uniqueDiscounts = new HashMap<>();
	private static String logFileName = "ReadConfigurations.txt";
	private static MessageWriter messageWriter = new MessageWriter(logFileName);

	static {
		try {
			readConfigurations();
		} catch (Exception e) {
			MessageWriter.writeToFile(logFileName, e.getStackTrace());
		}
	}

	/**
	 * It will read the discount configurations when this class is loaded for the first time.
	 * Currently it expects this configuration file in the project path.
	 * @throws URISyntaxException
	 */
	private static void readConfigurations() {
		String configFile = "/resources/configuration.config";

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(ReadConfigurations.class.getResource(configFile).toURI()))) {
			stream.forEach(ReadConfigurations::parse);
		} catch (Exception e) {
			MessageWriter.writeToFile(logFileName, e.getStackTrace());
		}
	}

	/**
	 * It populates discountTypes with the discount information
	 * for the given customer type.
	 * @param line
	 */
	private static void parse(String line) {
		if(!line.startsWith("#")) {
			String[] strs = line.split("-");
			if(strs.length < 4)
				return;
			String type = strs[0].toUpperCase();
			List<Discounts> discounts = discountTypes.get(type);
			if(discounts == null) {
				discounts = new ArrayList<>();
			}
			Discounts dis = null;
			String key = ""+strs[1]+strs[2]+strs[3];
			if((dis= uniqueDiscounts.get(key)) == null) {
				dis = new Discounts(Double.parseDouble(strs[1]), Double.parseDouble(strs[2]), Double.parseDouble(strs[3]));
				uniqueDiscounts.put(key, dis);
			}
			discounts.add(dis);
			discountTypes.put(type, discounts);
		}
	}

	/** 
	 * Returns the list of discounts applicable to particular CustomerType.
	 * If no list exists for the given customer type then returns emptyList.
	 * @param type
	 * @return
	 */
	public static List<Discounts> getDiscountsFor(String type) {
		List<Discounts> result = discountTypes.get(type);
		if(result == null)
			result = new ArrayList<>();
		return result;
	}

	/**
	 * Reads the data provided in the input data file.
	 * Note : If the file name contains letter ':' then
	 * the file name is considered to have the complete path.
	 * @param fileName
	 * @return
	 * @throws URISyntaxException
	 */
	public List<String> readInputTestData(String fileName) {
		List<String> data = new ArrayList<>();
		Path path = null;
		if(fileName.contains(":"))
			path = Paths.get(fileName);
		else
			try {
				path = Paths.get(ReadConfigurations.class.getResource(fileName).toURI());
			} catch (URISyntaxException uriEx) {
				writeErrorMessage(uriEx);
			}
		try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(line -> {
				if(!line.startsWith("#")) {
					try {
						String[] strs = line.split(" ");
						data.add(strs[0]+":"+strs[1]);
					} catch(Exception e) {
						writeErrorMessage(e);
					}
				}	
			});

		} catch (Exception e) {
			messageWriter.writeToFile("Error while reading file with name : " + fileName);
			writeErrorMessage(e);
		}
		return data;
	}
	
	/**
	 * Just thought to have these 2 common lines in separate method.
	 */
	private void writeErrorMessage(Exception e) {
		messageWriter.writeToFile(e.getMessage())
					 .writeToFile(e.getStackTrace());
	}
}
