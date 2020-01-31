package com.ascena.automation.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.json.JSONObject;

import com.ascena.automation.extent.listeners.ExtentTestManager;

public class JSONReader {
	static File jsonFile;
	static InputStream jsonInputStream;
	static BufferedReader streamReader;
	static String jsonSingleData;

	public static void main(String[] args) throws Exception {
		System.out.println("JSON Value: " + getURL("PROD"));
	}

	/**
	 * Method to return the environment URL based on environment parameter
	 * 
	 * @param envName
	 * @return
	 * @throws Exception
	 */
	public static String getURL(String envName) throws Exception {
		try {
			String jsonData = readJson(Constants.ENVIRONMENT_CONFIG_JSON);

			return returnKeyWordValue(returnKeyWordValue(jsonData, InitialDataSetup.getBrand()), envName);

		} catch (Exception | AssertionError e) {
			ExtentTestManager.getTest().fail(e.getMessage());
			throw e;
		}
	}

	/**
	 * Method to read the JSON input file.
	 * 
	 * @param Input
	 *            Json Name
	 */
	public static String readJson(String jSonName) throws IOException {
		try {
			jsonFile = new File(jSonName);

			jsonInputStream = new FileInputStream(jsonFile);

			streamReader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"));

			StringBuilder responseStrBuilder = new StringBuilder();

			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);

			jsonInputStream.close();
			streamReader.close();

			return responseStrBuilder.toString();

		} catch (IOException e) {
			if (streamReader != null)
				streamReader.close();
			throw e;
		}
	}

	/**
	 * Method to return the Value of a JSON element keyword.
	 * 
	 * @param jsonData
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public static String returnKeyWordValue(String jsonData, String keyWord) throws Exception {
		try {
			String result = null;
			Iterator<String> x;

			if (jsonData != null) {
				JSONObject jobj = new JSONObject(jsonData);
				x = jobj.keys();
				//System.out.println(jobj.keys().toString());

				while (result == null && x.hasNext()) {
					String key = (String) x.next();

					if (key.contains(keyWord)) {
						result = jobj.get(key).toString();
						break;
					} else if (jobj.get(key).toString().startsWith("{"))
						result = returnKeyWordValue(jobj.get(key).toString(), keyWord);

					else if (jobj.get(key).toString().startsWith("[")) {
						for (int i = 0; i < jobj.getJSONArray(key).length(); i++) {
							if (jobj.getJSONArray(key).get(i).toString().startsWith("{"))
								result = returnKeyWordValue(jobj.getJSONArray(key).get(i).toString(), keyWord);
						}
					}
				}
			}

			return result;

		} catch (Exception | AssertionError e) {
			ExtentTestManager.getTest().fail(e.getMessage());
			throw e;
		}
	}
}
