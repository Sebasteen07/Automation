//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils.dataprovider;

import com.intuit.ihg.common.entities.TestObject;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelSheetUtil {

	private static Logger logger = Logger.getLogger(ExcelSheetUtil.class);

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_MAP = new HashMap<Class<?>, Class<?>>();

	static {
		PRIMITIVE_TYPE_MAP.put(Boolean.TYPE, Boolean.class);
		PRIMITIVE_TYPE_MAP.put(Byte.TYPE, Byte.class);
		PRIMITIVE_TYPE_MAP.put(Character.TYPE, Character.class);
		PRIMITIVE_TYPE_MAP.put(Short.TYPE, Short.class);
		PRIMITIVE_TYPE_MAP.put(Integer.TYPE, Integer.class);
		PRIMITIVE_TYPE_MAP.put(Long.TYPE, Long.class);
		PRIMITIVE_TYPE_MAP.put(Float.TYPE, Float.class);
		PRIMITIVE_TYPE_MAP.put(Double.TYPE, Double.class);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private static Object _readFieldValueObject(Class<?> fieldClz, Type type, Map<String, Object> dataMap, String combinedFieldName) throws Exception {
		Object fieldValue = null;

		if (fieldClz.isArray()) {// Take care of Arrays
			int size = getArraySize(dataMap, combinedFieldName);
			if (size > 0) {
				fieldValue = Array.newInstance(fieldClz.getComponentType(), size);
				for (int j = 0; j < size; j++) {
					Array.set(fieldValue, j, readFieldValue(fieldClz.getComponentType(), combinedFieldName + "." + j, dataMap));
				}
			}
		} else if (fieldClz.isAssignableFrom(List.class)) {
			ArrayList list = ArrayList.class.newInstance();
			int size = getArraySize(dataMap, combinedFieldName);
			if (size > 0) {
				fieldValue = list;
				Class<?> itemClz = getListItemType(type);
				for (int j = 0; j < size; j++) {
					list.add(readFieldValue(itemClz, combinedFieldName + "." + j, dataMap));
				}
			}
		} else {
			fieldValue = readFieldValue(fieldClz, combinedFieldName, dataMap);
		}

		return fieldValue;
	}

	public static int getArraySize(Map<String, Object> map, String key) {
		int count = 0;
		boolean valueFound = false;
		key = key.toLowerCase();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key2 = entry.getKey();
			String value2 = (String) entry.getValue();
			if (value2 == null || value2.length() == 0)
				continue;

			key2 = key2.toLowerCase();
			if (key2.startsWith(key + ".")) {
				valueFound = true;
				String subst = key2.substring((key + ".").length());
				String[] ss = subst.split("\\.");
				try {
					int value = Integer.parseInt(ss[0]);
					count = (value > count ? value : count);
				} catch (Exception e) {
				}
			}
		}

		return (valueFound ? count + 1 : count);
	}

	public static synchronized Iterator<Object[]> getDataFromSpreadsheet(Class<?> class1, String filename, int sheetNumber, String[] columnNames, Filter filter) {
		return ExcelSheetUtil.getDataFromSpreadsheet(class1, filename, null, sheetNumber, columnNames, filter, false);
	}

	public static synchronized Iterator<Object[]> getDataFromSpreadsheet(Class<?> class1, String filename, String sheetName, int sheetNumber, String[] fields,
			Filter filter, boolean readHeaders) {

		System.gc(); 
		if (filename.toLowerCase().endsWith(".csv")) {
			return CSVUtil.getDataFromCSVFile(class1, filename, fields, filter, readHeaders);
		}

		Workbook w = null;
		InputStream is = null;
		try {
			if (class1 != null) {
				is = class1.getResourceAsStream(filename); // ClassLoader.getSystemResource
			} else {
				is = new FileInputStream(filename);
			}

			if (is == null) {
				return new ArrayList<Object[]>().iterator();
			}

			w = Workbook.getWorkbook(is);

			if (w.getSheetNames().length <= sheetNumber) {
				throw new Exception("Sheet # " + sheetNumber + " for " + filename + " not found.");
			}

			if (sheetName != null) {
				for (int i = 0; i < w.getSheetNames().length; i++) {
					if (sheetName.equals(w.getSheetNames()[i])) {
						sheetNumber = i;
					}
				}
			}

			Sheet sheet = w.getSheet(sheetNumber);

			int columnCount = sheet.getColumns();
			for (int j = 0; j < sheet.getColumns(); j++) {
				String content = sheet.getCell(j, 0).getContents();
				if (content == null || content.trim().length() == 1) {
					columnCount = j + 1;
					break;
				}
			}

			List<Object[]> sheetData = new ArrayList<Object[]>();
			if (readHeaders) {
				List<Object> rowData = new ArrayList<Object>();
				if (fields == null) {
					for (int j = 0; j < columnCount; j++) {
						rowData.add(sheet.getCell(j, 0).getContents());
					}
				} else {
					for (int i = 0; i < fields.length; i++) {
						rowData.add(fields[i]);
					}
				}
				sheetData.add(rowData.toArray(new Object[rowData.size()]));
			}

			int testTitleColumnIndex = -1;
			int testEnvColumnIndex = -1;
			for (int i = 0; i < columnCount; i++) {
				if (testTitleColumnIndex == -1 && TestObject.TEST_TITLE.equalsIgnoreCase(sheet.getCell(i, 0).getContents())) {
					testTitleColumnIndex = i;
				} else if (testEnvColumnIndex == -1 && TestObject.TEST_ENV.equalsIgnoreCase(sheet.getCell(i, 0).getContents())) {
					testEnvColumnIndex = i;
				}

				if (testTitleColumnIndex != -1 && testEnvColumnIndex != -1) {
					break;
				}
			}

			StringBuffer sbBlank = new StringBuffer();
			for (int i = 1; i < sheet.getRows(); i++) {
				if (testTitleColumnIndex != -1 && testEnvColumnIndex != -1
						&& ((sheet.getCell(testTitleColumnIndex, i).getContents() == null || sheet.getCell(testTitleColumnIndex, i).getContents().trim().length() == 0)
								|| (sheet.getCell(testEnvColumnIndex, i).getContents() == null || sheet.getCell(testEnvColumnIndex, i).getContents().trim().length() == 0))) {
					sbBlank.append(i + 1).append(',');
				}
			}
			if (sbBlank.length() > 0) {
				sbBlank.deleteCharAt(sbBlank.length() - 1);
				throw new Exception("Blank TestTitle and/or Env value(s) found on Row(s) " + sbBlank.toString() + ".");
			}

			Set<String> uniqueDataSet = new TreeSet<String>();


			for (int i = 1; i < sheet.getRows(); i++) {
				if (testTitleColumnIndex != -1 && testEnvColumnIndex != -1) {
					String uniqueString = sheet.getCell(testTitleColumnIndex, i).getContents() + "$$$$####$$$$" + sheet.getCell(testEnvColumnIndex, i).getContents();
					if (uniqueDataSet.contains(uniqueString))
						throw new Exception("Duplicate TestTitle and Env combination found in the spreadsheet " + "with TestTitle = {"
								+ sheet.getCell(testTitleColumnIndex, i).getContents() + "} " + "and Env = {" + sheet.getCell(testEnvColumnIndex, i).getContents() + "}");

					uniqueDataSet.add(uniqueString);
				}

				Map<String, Object> rowDataMap = new HashMap<String, Object>();
				List<Object> rowData = new ArrayList<Object>();

				for (int j = 0; j < columnCount; j++) {
					rowDataMap.put(sheet.getCell(j, 0).getContents(), sheet.getCell(j, i).getContents());
				}

				if (fields == null) {
					for (int j = 0; j < columnCount; j++) {
						rowData.add(sheet.getCell(j, i).getContents());
					}
				} else {
					for (int k = 0; k < fields.length; k++) {
						rowData.add(getValue(rowDataMap, fields[k]));
					}
				}

				if (filter == null || filter.match(rowDataMap)) {
					sheetData.add(rowData.toArray(new Object[rowData.size()]));
				}
			}

			sheet = null;

			if ((!readHeaders && sheetData.isEmpty()) || (readHeaders && sheetData.size() <= 1))
				logger.warn("No matching data found on spreadsheet: " + filename + " with filter criteria: " + filter.toString());

			return sheetData.iterator();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {

			if (w != null) {
				w.close();
				w = null;
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
				} 
			}
		}
	}

	public static List<Object[]> getDataList(List<Object[]> table, Filter filter) {

		List<Object[]> sheetData = new ArrayList<Object[]>();

		String[] fields = (String[]) table.get(0);

		for (int i = 1; i < table.size(); i++) {
			Map<String, Object> rowDataMap = new HashMap<String, Object>();
			Object[] rowData = table.get(i);
			for (int j = 0; j < rowData.length; j++) {
				rowDataMap.put(fields[j], rowData[j]);
			}
			if (filter == null || filter.match(rowDataMap)) {
				sheetData.add(rowData);
			}
		}

		sheetData.add(0, fields);

		return sheetData;
	}

	public static Iterator<Object[]> getObjectsFromSpreadsheet(Class<?> class1, LinkedHashMap<String, Class<?>> entityclass1Map, String filename, int sheetNumber,
			String[] fields, Filter filter) throws Exception {
		return ExcelSheetUtil.getObjectsFromSpreadsheet(class1, entityclass1Map, filename, null, sheetNumber, fields, filter);
	}

	public static Iterator<Object[]> getObjectsFromSpreadsheet(Class<?> class1, LinkedHashMap<String, Class<?>> entityclass1Map, String filename,
			String sheetName, int sheetNumber, String[] fields, Filter filter) throws Exception {

		Iterator<Object[]> dataIterator = getDataFromSpreadsheet(class1, filename, sheetName, sheetNumber, fields, filter, true);

		List<Object[]> list = new ArrayList<Object[]>();

		// Get the headers
		Object[] headerArray = null;
		if (dataIterator.hasNext()) {
			headerArray = dataIterator.next();
		}

		while (dataIterator.hasNext()) {

			Object[] rowDataArray = dataIterator.next();
			Map<String, Object> map = new LinkedHashMap<String, Object>();

			List<Object> rowData = new ArrayList<Object>();
			for (int j = 0; j < headerArray.length; j++) {
				String header = (String) headerArray[j];
				map.put(header, rowDataArray[j]);
			}

			Map<String, Boolean> temp = new HashMap<String, Boolean>();
			if (entityclass1Map != null) {
				for (Map.Entry<String, Class<?>> entry : entityclass1Map.entrySet()) {
					temp.put(entry.getKey(), Boolean.TRUE);
					rowData.add(readObject(entry.getValue(), entry.getKey(), map));
				}
			}

			for (int i = rowDataArray.length - 1; i >= 0; i--) {
				int docIdx = ((String) headerArray[i]).indexOf(".");
				if (docIdx < 0) {
					rowData.add(0, rowDataArray[i]);
				} else if (temp.get(((String) headerArray[i]).substring(0, docIdx)) == null) {
					rowData.add(0, rowDataArray[i]);
				}
			}

			list.add(rowData.toArray(new Object[] {rowData.size()}));
		}

		return list.iterator();
	}

	public static Map<String, Object> getFieldsDataNeedToBeSet(Map<String, Object> map, String key) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		for (String key2 : map.keySet()) {
			if (key2.equalsIgnoreCase(key)) {
				if (map.get(key2) != null)
					result.put(key2, map.get(key2).toString());
			}
			if (key2.toLowerCase().startsWith(key.toLowerCase() + ".")) {
				if (map.get(key2) != null)
					result.put(key2.substring(key.length() + 1), map.get(key2).toString());
			}
		}
		return result;
	}

	public static Map<String, Object> getFieldsNeedToBeSet(Map<String, Object> map, String key) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		String lastKey = "";
		for (String key2 : map.keySet()) {
			if (key2.equalsIgnoreCase(key))
				result.put(key2, map.get(key2));
			if (key2.toLowerCase().startsWith(key.toLowerCase() + ".")) {
				String newkey = key2.substring(key.length() + 1);
				if (newkey.contains("."))
					newkey = newkey.substring(0, newkey.indexOf("."));
				if (!newkey.equalsIgnoreCase(lastKey))
					result.put(newkey, map.get(key2));
			}
		}
		return result;
	}

	private static Class<?> getListItemType(Type type) throws ClassNotFoundException {

		Class<?> itemClz = null;

		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			itemClz = Class.forName(pt.getActualTypeArguments()[0].toString().substring("class ".length()));
		}
		return itemClz;
	}

	public static Object getValue(Map<String, Object> map, String key) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if ((entry.getKey() == null && key == null) || (entry.getKey() != null && entry.getKey().equalsIgnoreCase(key)))
				return entry.getValue();
		}
		return null;
	}

	private static boolean isPrimitive(Class<?> clz) {
		if (clz.isPrimitive())
			return true;
		else if (clz.getCanonicalName().equals("java.lang." + clz.getSimpleName()))
			return true;
		else
			return false;
	}

	private static Object readFieldValue(Class<?> fieldClz, String fieldName, Map<String, Object> dataMap) throws Exception {
		Object fieldValue = null;
		String tempValue = (String) getValue(dataMap, fieldName);

		if ((tempValue == null || tempValue.length() == 0)
				&& (fieldClz.isEnum() || fieldClz.getName().equals("java.util.Calendar") || fieldClz.getName().equals("java.math.BigDecimal") || isPrimitive(fieldClz)))
			return null;

		if (fieldClz.isEnum()) {
			try {
				fieldValue = fieldClz.getMethod("valueOf", String.class).invoke(fieldClz, tempValue);
			} catch (Exception e) {
				logger.warn("Ex", e);
			}
		} else if (fieldClz.getName().equals("java.util.Calendar")) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(tempValue));
			fieldValue = calendar;
		} else if (fieldClz.getName().equals("java.math.BigDecimal")) {
			fieldValue = new BigDecimal(tempValue);
		} else if (isPrimitive(fieldClz)) {// Take care of primitives
			Constructor<?> constructor;
			try {
				if (fieldClz.getName().equals("java.lang.String")) {
					fieldValue = tempValue;
				} else {
					if (PRIMITIVE_TYPE_MAP.containsKey(fieldClz))
						constructor = PRIMITIVE_TYPE_MAP.get(fieldClz).getConstructor(String.class);
					else
						constructor = fieldClz.getConstructor(String.class);

					fieldValue = constructor.newInstance(tempValue);
				}
			} catch (Exception e) {
				logger.warn("Ex", e);
			}
		} else {// Non-atomic or Object field
			fieldValue = readObject(fieldClz, fieldName, dataMap);
		}

		return fieldValue;
	}

	public static Object readObject(Class<?> clz, String objectName, Map<String, Object> dataMap) throws Exception {
		Object object = null;
		if (clz == null)
			return null;
		if (objectName == null)
			objectName = clz.getSimpleName();
		Map<String, Object> fieldMap = getFieldsNeedToBeSet(dataMap, objectName);
		Map<String, Object> datamap = getFieldsDataNeedToBeSet(dataMap, objectName);

		for (String fieldName : fieldMap.keySet()) {
			String first = "" + fieldName.charAt(0);
			String realfieldName = fieldName.replaceFirst(first, first.toLowerCase());
			Object fieldValue = null;
			Class<?> type = null;
			try {
				Class<?>[] parameterTypes = new Class<?>[] {};
				Method method = clz.getMethod("get" + fieldName, parameterTypes);
				type = method.getReturnType();
				fieldValue = _readFieldValueObject(type, method.getGenericReturnType(), datamap, fieldName);
			} catch (NoSuchMethodException ex) {
				try {
					Class<?>[] parameterTypes = new Class<?>[] {};
					Method method = clz.getMethod("is" + fieldName, parameterTypes);
					type = method.getReturnType();
					fieldValue = _readFieldValueObject(type, method.getGenericReturnType(), datamap, fieldName);
				} catch (NoSuchMethodException ex2) {
					try {
						Field field = clz.getDeclaredField(realfieldName);
						fieldValue = _readFieldValueObject(field.getType(), field.getGenericType(), datamap, fieldName);
					} catch (NoSuchFieldException ex3) {
						// Can't find field, get**,is*** method, set it to String.class
						try {
							fieldValue = _readFieldValueObject(String.class, String.class, datamap, fieldName);
							type = String.class;
						} catch (Exception e) {
							logger.warn("Ex:" + clz.getName(), e);
						}
					}
				}
			}

			try {
				if (fieldValue != null) {

					if (object == null) {
						try {
							object = clz.newInstance();
						} catch (InstantiationException e) {
							Class<?>[] parameterTypes = new Class<?>[1];
							parameterTypes[0] = fieldValue.getClass();
							Constructor<?> constructor = clz.getDeclaredConstructor(parameterTypes);
							constructor.setAccessible(true);
							object = constructor.newInstance(fieldValue);
							return object;
						}
					}
				}
				if (fieldValue != null) {

					if (object == null)
						object = clz.newInstance();
					try {
						Class<?>[] parameterTypes = new Class<?>[1];
						parameterTypes[0] = type;
						Method method = object.getClass().getMethod("set" + fieldName, parameterTypes);
						method.invoke(object, fieldValue);
					} catch (Exception ex) {
						Field field2 = object.getClass().getDeclaredField(realfieldName);
						field2.setAccessible(true);
						field2.set(object, fieldValue);
					}
				}
			} catch (Exception e) {
				logger.warn("Ex", e);
			}
		}

		return object;
	}
}
