package com.shoppingcart.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * @author Nitin Agrawal
 * @Date 07-May-2020
 * This class is used to log the messages in the provided
 * file in the logs directory of the project.
 */
public class MessageWriter {
	private File file = null;
	private static String logDirectory = "D:/ShoppingCartLogs/";
	private static Calendar calendar = Calendar.getInstance();

	static {
		File file = new File(logDirectory);
		if(!file.exists())
			file.mkdir();
	}

	public MessageWriter(String fileName) {
		fileName = logDirectory + fileName;
		file = new File(fileName);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public MessageWriter writeToFile(String message) {
		try(FileWriter fileWriter = new FileWriter(file, true)) {
			fileWriter.append(calendar.getTime() + " : " + message);
			fileWriter.flush();
			fileWriter.append("\n");
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void writeToFile(StackTraceElement[] stack) {
		for(StackTraceElement message : stack)
			writeToFile(message.toString());
	}

	public static void writeToFile(String fileName, String message) {
		fileName = logDirectory + fileName;
		File file = new File(fileName);
		try {
			if(!file.exists())
				file.createNewFile();
			try(FileWriter fileWriter = new FileWriter(file, true)) {
				fileWriter.append(calendar.getTime() + " : " + message);
				fileWriter.append("\n");
				fileWriter.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param fileName
	 * @param stack
	 * This method needs to be refactored as it is not efficient because
	 * it causes to create new File & FileWriter objects on every message.
	 */
	public static void writeToFile(String fileName, StackTraceElement[] stack) {
		for(StackTraceElement message : stack)
			writeToFile(fileName, message.toString());
	}
}
