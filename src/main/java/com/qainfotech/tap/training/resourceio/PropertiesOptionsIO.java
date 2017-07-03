package com.qainfotech.tap.training.resourceio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 *
 * @author Ramandeep RamandeepSingh AT QAInfoTech.com
 */
public class PropertiesOptionsIO {

	public Object getOptionValue(String optionKey) throws IOException {
		// throw new UnsupportedOperationException("Not implemented.");
		try {

			Properties prop = new Properties();
			InputStream input = new FileInputStream("src/main/resources/options.properties");
			prop.load(input);
			return ((Object) prop.getProperty(optionKey));
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public void addOption(String optionKey, Object optionValue) throws IOException {
		// throw new UnsupportedOperationException("Not implemented.");

		Properties prop = new Properties();
		File file;
		InputStream input = null;
		OutputStream output = null;

		try {
			file = new File("src/main/resources/options.properties");
			input = new FileInputStream(file);
			prop.load(input);			
			output = new FileOutputStream(file);
			prop.setProperty(optionKey, optionValue.toString());
 
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();

		} finally {
			output.close();
		}

	}
}
