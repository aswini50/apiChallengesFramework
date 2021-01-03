package com.api.automation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class JSONUtil {

	private final String requestDataFolderPath = System.getProperty("user.dir")
			+ ApiChallengerUtil.props.getProperty("request.dir.path");

	@SuppressWarnings({ "unchecked" })
	public static Map<String, String> parseJsonStringToMap(String jsonString) throws Exception {
		Map<String, String> testDataMap = new HashMap<String, String>();
		try {
			JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(jsonString);
			testDataMap = (Map<String, String>) config;
		} catch (JsonSyntaxException jse) {
			jse.printStackTrace();
		} catch (JsonIOException jio) {
			jio.printStackTrace();
		}
		return testDataMap;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertJsonObject(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		map = (Map<String, Object>) object;
		return map;
	}

	public static List<Object> convertJsonArrayToList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			if (value instanceof org.json.JSONArray) {
				value = convertJsonArrayToList((JSONArray) value);
			}
			list.add(value);
		}
		return list;
	}

	public static JSONArray parseToJsonArray(String json) throws Exception {
		JSONArray jsonArray = null;
		try {
			JSONParser parser = new JSONParser();
			jsonArray = (JSONArray) parser.parse(json);
		} catch (Exception jse) {
			jse.printStackTrace();
		}
		return jsonArray;
	}

	public static JSONObject parseToJsonObject(String json) throws Exception {
		JSONObject jsonObject = null;
		try {
			JSONParser parser = new JSONParser();
			jsonObject = (JSONObject) parser.parse(json);
		} catch (Exception jse) {
			jse.printStackTrace();
		}
		return jsonObject;
	}

	public String parseJsonArrayToString(String fileName) throws Exception {
		String jsonString = null;
		String firstLine = null;
		try {
			firstLine = jsonFirstLineReaderFromFile(fileName);
			JSONParser parser = new JSONParser();
			if (firstLine.startsWith("[")) {
				JSONArray config = (JSONArray) parser.parse(new FileReader(requestDataFolderPath + fileName + ".json"));
				jsonString = config.toJSONString();
			} else if (firstLine.startsWith("{")) {
				JSONObject temp = (JSONObject) parser.parse(new FileReader(requestDataFolderPath + fileName + ".json"));
				jsonString = temp.toJSONString();
			}
		} catch (Exception jse) {
			jse.printStackTrace();
		}
		return jsonString;
	}

	public String jsonFirstLineReaderFromFile(String fileName) {
		String firstLine = null;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(requestDataFolderPath + fileName + ".json"));
			firstLine = reader.readLine();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return firstLine;
	}

	/**
	 * Get the content in JSON file as a String from the Test Data Directory
	 *
	 * @param fileName
	 * @return String
	 * @throws Exception
	 */

	public String getRequestBodyJSONString(String folderType, String fileName) throws Exception {
		String folderName = getRequestFolderName(folderType);
		File folder = new File(folderName);
		File[] listOfFiles = folder.listFiles();
		File rFile = null;
		String text = null;
		for (File file : listOfFiles) {
			if (file.getName().replace(".json", "").equals(fileName)) {
				rFile = file;
				break;
			}
		}
		if (rFile == null) {
			throw new Exception("File with Name: " + fileName + " Not found in Directory: " + requestDataFolderPath);
		}
		try (FileReader reader = new FileReader(rFile)) {

			JSONObject object = (JSONObject) new JSONParser().parse(reader);

			text = ((JSONObject) object).toString();
		} catch (IOException e) {
			throw e;
		}
		return text;
	}

	public String getRequestFolderName(String folderType) {
		String folderName = "";
		if (folderType.toUpperCase().equals("SCHEMAS")) {
			folderName = requestDataFolderPath + ApiChallengerUtil.props.getProperty("schema.dir.path");
		} else if (folderType.toUpperCase().equals("REQUESTBODY")) {
			folderName = requestDataFolderPath + ApiChallengerUtil.props.getProperty("body.dir.path");
		}
		return folderName;
	}

	public static boolean jsonValueCompare(String responseString, String dataBaseString) {

		boolean flag = false;
		try {
			List<Object> rList = JSONUtil.convertJsonArrayToList(JSONUtil.parseToJsonArray(responseString));
			List<Object> dList = JSONUtil.convertJsonArrayToList(JSONUtil.parseToJsonArray(dataBaseString));

			if (rList.size() == dList.size()) {

				Map<String, Object> rMap = null;
				Map<String, Object> lMap = null;

				for (int i = 0; i <= rList.size() - 1; i++) {
					rMap = JSONUtil.convertJsonObject(JSONUtil.parseToJsonObject(rList.get(i).toString()));
					lMap = JSONUtil.convertJsonObject(JSONUtil.parseToJsonObject(dList.get(i).toString()));

					Map<String, String> firstMap = new HashMap<String, String>();
					Map<String, String> secondMap = new HashMap<String, String>();

					for (Entry<String, Object> entry : rMap.entrySet()) {
						firstMap.put(entry.getKey(), String.valueOf(entry.getValue()));
					}

					for (Entry<String, Object> entry : lMap.entrySet()) {
						secondMap.put(entry.getKey(), String.valueOf(entry.getValue()));
					}

					TreeMap<String, String> sortedFirstMap = new TreeMap<>(firstMap);
					TreeMap<String, String> sortedSecondMap = new TreeMap<>(secondMap);

					// flag = areEqual(sortedFirstMap, sortedSecondMap);
					flag = JSONUtil.mapValuesComaparison(sortedFirstMap, sortedSecondMap);
					firstMap = null;
					secondMap = null;
				}
			} else
				return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	public static boolean jsonObjectValueCompare(String responseString, String dataBaseString) {
		boolean flag = false;

		try {
			Map<String, String> firstMap = parseJsonStringToMap(responseString);
			Map<String, String> secondMap = parseJsonStringToMap(dataBaseString);

			TreeMap<String, String> sortedFirstMap = new TreeMap<>(firstMap);
			TreeMap<String, String> sortedSecondMap = new TreeMap<>(secondMap);

			flag = mapValuesComaparison1(sortedFirstMap, sortedSecondMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static boolean mapValuesComaparison(Map<String, String> responseMap, Map<String, String> databaseMap) {
		boolean matches = false;
		try {
			LinkedHashMap<String, String> returnMap = new LinkedHashMap<String, String>();
			for (String key : responseMap.keySet()) {
				if (databaseMap.containsKey(key)) {
					if (((String) responseMap.get(key)).contains(databaseMap.get(key))) {
						returnMap.put(key, (String) responseMap.get(key));
						matches = true;
					} else if (((String) databaseMap.get(key)).contains(responseMap.get(key))) {
						returnMap.put(key, (String) responseMap.get(key));
						matches = true;
					}
				}
			}
			return matches;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean mapValuesComaparison1(Map<String, String> responseMap, Map<String, String> databaseMap) {
		boolean matches = false;
		try {
			LinkedHashMap<String, String> returnMap = new LinkedHashMap<String, String>();
			for (String key : responseMap.keySet()) {
				String value = String.valueOf(responseMap.get(key));
				if (databaseMap.containsValue(value))
					matches = true;
			}
			return matches;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean areEqual(Map<String, String> rMap, Map<String, String> lMap) {
		if (rMap.size() != lMap.size()) {
			return false;
		}
		return rMap.entrySet().stream().allMatch(e -> e.getValue().equals(lMap.get(e.getValue())));
	}

}
