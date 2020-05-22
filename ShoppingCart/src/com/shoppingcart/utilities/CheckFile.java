package com.shoppingcart.utilities;

/**
 * @author Nitin Agrawal
 * @Date 08-May-2020
 * This file is a utility file which currently checking if the file name has the
 * required extension. But in future this file can be used to provide other files
 * related utility features.
 */
public class CheckFile {	
	/**
	 * If no extension is provided i.e. it is given as null or empty then file name
	 * shouldn't have any extension at all. If the extension is provided then file name
	 * should end with that extension.
	 * @param fileName
	 * @param extension
	 * @return
	 */
	public static boolean checkFileExtension(String fileName, String extension) {
		String[] words = fileName.split("\\.");
		int len = words.length;
		if(words[len-1].equalsIgnoreCase(extension) 
				|| (len == 1 && (extension == null || extension == "")))
			return true;
		return false;
	}
}