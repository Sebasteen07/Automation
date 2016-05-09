package com.intuit.ihg.common.utils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * @author Dipak Swain
 */

//########################################################################################################

//### CLASS NAME 			: ExcelSheetReader

//### EXTENDED CLASS		: 

//### DESCRIPTION 			: This class provide several methods to retrieve test data from
//							  an Excel workbook. Users can get a single row of data by providing
// 							  the excel filename, the data sheet name, and they key. Or get the
//							  whole data sheet by providing excel file name and the data sheet
//							  name.
//########################################################################################################


/**
 * This class provide several methods to retrieve test data from
 * an Excel workbook. Users can get a single row of data by providing
 * the excel filename, the data sheet name, and they key. Or get the
 * whole data sheet by providing excel file name and the data sheet
 * name.
 * 
 *
 * 
 */
public class ExcelSheetReader {
	
	private Workbook workBook;
	private InputStream inputStream;
	private FileOutputStream fileoutputStream;  
	private Integer	rowIndex = 0;
	public String resourcePath=null;
	
	/**
	 * The constructor will use the path name and the file name of the Excel
	 * workbook to initialize the input stream before the stream is being 
	 * used by several methods to get the test data from the Excel workbook.
	 * 
	 * If pathName is not null then the users delibereated
	 * 		specified the resource file in other location
	 * 		than the classpaths.
	 * If pathName is null, then the resouce file can be 
	 * 		found using the classpath.
	 * 
<h3>Sample usage:</h3>
	 * <pre>
	 * String   pathName = "src/test/java";
	 * String   fileName = "DataReaderTest.xls"
	 * LOCAL_DATA myData = new LOCAL_DATA();
	 * Object [][] myObj;
	 * 
	 * // To get a single row of excel sheet using a key associated with the data
	 * myData = (LOCAL_DATA) ExcelDataProvider dataProvider = new ExcelDataProvider(
	 *                          pathName, fileName).getSingleExcelRow(myData, "4");
	 * 
	 * // To get a whole sheet of excel data. This will not need key.
	 * myObj =  new ExcelDataProvider(pathName,
	 *                 fileName).getAllExcelRows(myData);
	 * myData = (LOCAL_DATA)myObj[1][0];
	 * </pre>
	 * @param pathName
	 *            the path where the excel file is located.
	 * @param fileName
	 *            the name of the excel file to be read.
	 * @throws IOException
	 * 			  If the file cannot be located, or cannot read by the method.
	 */
	public ExcelSheetReader(String pathName, String fileName)
			throws IOException {
		super();

		//String resourcePath = fileName;
		resourcePath = fileName;
		String pad = null;
		
		// If pathName is not null then the users delibereated
		//		specified the resource file in other location
		//		than the classpaths.
		// If pathName is null, then the resouce file can be
		//		found using the classpath.
		if (pathName != null) {
			// Checking the last "/" from the pathName
			if (pathName.endsWith("/"))
				pad = "";
			else
				pad = "/";

			resourcePath = pathName + pad + fileName;
		}

		// By default file stream writes one byte of data at a time,
		// BufferedInputStream will buffer the stream and write multi-bytes
		// at a time, this will enhance file throughput
		InputStream fileStream = getInputFileStream(resourcePath);
		inputStream = new BufferedInputStream(fileStream);

		if (fileName.endsWith("xlsx")) {
			workBook = new XSSFWorkbook(inputStream);

		} else if (fileName.endsWith("xls")) {
			workBook = new HSSFWorkbook(inputStream);
		}
	
	
	
	
	}
	
	/**
	 * Since there is no pathName provided to the constructor it will search 
	 * for the resource file within the project classpaths. Then use the file 
	 * name of the Excel workbook to initialize the input stream before the 
	 * stream is being used by several methods to get the test data from the 
	 * Excel workbook.
	 * 
	 * <h3>Sample usage:</h3>
	 * <pre>
	 * String   fileName = "DataReaderTest.xls"
	 * LOCAL_DATA myData = new LOCAL_DATA();
	 * Object [][] myObj;
	 * 
	 * // To get a single row of excel sheet using a key associated with the data
	 * myData = (LOCAL_DATA) ExcelDataProvider dataProvider = new ExcelDataProvider(
	 *                          fileName).getSingleExcelRow(myData, "4");
	 * 
	 * // To get a whole sheet of excel data. This will not need key.
	 * myObj =  new ExcelDataProvider(fileName).getAllExcelRows(myData);
	 * myData = (LOCAL_DATA)myObj[1][0];
	 * </pre>
	 * @param fileName
	 *            the name of the excel file to be read.
	 * @throws IOException
	 * 			  If the file cannot be located, or cannot read by the method.
	 */
	public ExcelSheetReader(String fileName) throws IOException{
		this(null, fileName);
	}

	/**
	 * Uses the full path and file name of the excel workbook to
	 * creates and initialize file input stream that other will
	 * use to read test data from the Excel workbook.
	 * 
	 * <h3>Usage:</h3>
	 * <pre>
	 * InputStream inputStream = getInputStream(fullPathName);
	 * </pre>
	 * @param fileName
	 * 			the full path name of the Excel file
	 * @return InputStream
	 * 			the input stream resource for reading the Excel file. 
	 */
	private InputStream getInputFileStream(String fileName) {

		ClassLoader loader = this.getClass().getClassLoader();
		InputStream inputStream = loader.getResourceAsStream(fileName);
		if (inputStream == null) {

			try {
				inputStream = new FileInputStream(fileName);

			} catch (FileNotFoundException e) {
				closeFileHandle();
				
				return null;
			}
		}

		return inputStream;

	}

	/**
	 * This function will closes file input stream and release
	 * the file handle to release the memory used by the stream.
	 * 
	 * <h3>Sample usage:</h3>
	 * <pre>
	 * closeFileHandle();
	 * </pre>
	 */
	public void closeFileHandle() {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
		

			}
		}
	}

	/*
	 * Search for the input key from the specified sheet name
	 * and return the index position of the row that contained the key
	 */
	private int getRowIndexByKey(String sheetName, String key) {
		int rowCount, index = -1;
		int i;
		String cellValue;
		Sheet sheet;

		// key = "-1" indicate that this caller will access
		// all row of the excel sheet, therefore the global 
		// this.rowIndex is returned without using the key
		// to search for the return row.
		if(key.contains("-1")){
			this.rowIndex++;
			
			return this.rowIndex;
			
		}
		
		sheet = workBook.getSheet(sheetName);
		rowCount = sheet.getPhysicalNumberOfRows();
		for (i = 0; i < rowCount; i++) {
			cellValue = sheet.getRow(i).getCell(0).toString();
			if (key.compareTo(cellValue) == 0) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * Using the specified key to search for the data row from the specified
	 * Excel sheet, then return the row contents in a list of string format.
	 * 
	 * <h3>Sample usage:</h3>
	 * <pre>
	 * ...
	 * List<String> excelRowData;
	 * ...
	 * excelRowData = getRowContents("myExcelSheetName", "myKey", colCount);
	 * ...
	 * </pre>
	 * @param sheetName 
	 * 			The name of the Excel sheet to read.
	 * @param key 
	 * 			The specifies the row to read.
	 * @param colCount 
	 * 			The number of columns to read, including empty and blank column. 
	 * @return List<String> 
	 * 			String array contains the read data.
	 */
	private List<String> getRowContents (String sheetName, String key, int size){
		int rowIndex = getRowIndexByKey(sheetName, key);
		//BaseUtilities.numberofRowsToProcess=rowIndex;
		Sheet sheet = workBook.getSheet(sheetName);
		
		List<String> rowData = new ArrayList<String>();
		
		for (int i=1; i<size+1; i++){
			if(sheet.getRow(rowIndex).getCell(i) != null) {
				rowData.add(sheet.getRow(rowIndex).getCell(i).toString());
				
			}
			else {
				rowData.add(null);
				
			}			
		}
		return rowData;
	}
	
	/**
	 * author :-bkrishnankutty
	 * Date :-3-4-2013
	 * Desc :- will return a single row in excel sheet after filtering using the key value
	 * 
	 * @param userObj :- domain obj for eg portal obj or phr object
	 * @param key :-is the primary key
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */

	public Object getSingleExcelRow (Object userObj, String key) throws ClassNotFoundException, IllegalAccessException{
		// if key is empty, the don't bother reading
		// the data. It will not be used any way.
		if((key == null)||(key == ""))
			return null;
		
		List<String> excelRowData;
		
		String	fieldType	= null;
		String	data		= null;
		
		// For sub class
		String	className 	= null;
		Object	obj 		= null;
		Object myObj		= null;
		
		// For handing array of index data
		String[]	arrayData	= null;		// array of keys, split from comma separated string
		Object[]	arrayObj	= null;		// array of return objects
		
		Class<?> cls = Class.forName(userObj.getClass().getName());
		Field[] fields = cls.getDeclaredFields();
		
		int	i, j;
		
		try {
			// Create a new instance of the data so we can
			// store it here before return everything to the users.
			myObj = userObj.getClass().newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		}
		
		// Reading a row of excel data here
		className = cls.getSimpleName();
		excelRowData = getRowContents(className, key, fields.length);
				
		// Assigning data to user defined object
		for (i=0; i<fields.length; i++){
			fields[i].setAccessible(true);
			fieldType = fields[i].getType().getName();
			data = excelRowData.get(i);
			
			if((data == null)||(data == ""))
				continue;
			
			//---===***===---
			// Array data
			//---===***===---			
			if (fieldType.contains("[")){
				arrayData = data.split(",");
				
				for (j=0; j<arrayData.length; j++)
					arrayData[j] = arrayData[j].trim();
				
				
				//======================================================================================
				//---===***===---
				// Array of primitive boolean.
				//---===***===---
				if (fieldType.contains("[Z")){									// boolean[]
					boolean[] arrayBoolean = new boolean[arrayData.length];
					for (j = 0; j < arrayData.length; j++) {
						arrayBoolean[j] = false;
						if(arrayData[j] != null){
							arrayData[j] = arrayData[j].toLowerCase();
							if(arrayData[j].contains("true"))
								arrayBoolean[j] = true;						
						}
					}
					fields[i].set(myObj, arrayBoolean);					
				}
				//---===***===---
				// Array of primitive int.
				//---===***===---				
				else if(fieldType.contains("[I")){								// int[]
					int[] arrayInt = new int[arrayData.length];					
					for (j = 0; j < arrayData.length; j++) {
						if(arrayData[j] != null){
							arrayInt[j] = Integer.parseInt(arrayData[j]);						
						}						
					}
					fields[i].set(myObj, arrayInt);										
				}
				//---===***===---
				// Array of primitive float
				//---===***===---
				else if(fieldType.contains("[F")){								// float[]
					float[] arrayFloat = new float[arrayData.length];
					for (j=0; j<arrayData.length; j++){
						arrayFloat[j] = Float.parseFloat(arrayData[j]);
					}					
					fields[i].set(myObj, arrayFloat);
				}
				//---===***===---
				// Arrray of primitive double
				//---===***===---
				else if(fieldType.contains("[D")){								// double[]
					double[] arrayDouble = new double[arrayData.length];
					for (j=0; j<arrayData.length; j++){
						arrayDouble[j] = Double.parseDouble(arrayData[j]);
					}
					
					fields[i].set(myObj, arrayDouble);
				}
				//---===***===---
				// Array of primitive long
				//---===***===---
				else if(fieldType.contains("[J")){								// long[]
					long[] arrayLong = new long[arrayData.length];
					for (j=0; j<arrayData.length; j++){
						arrayLong[j] = Long.parseLong(arrayData[j]);
					}				
					fields[i].set(myObj, arrayLong);
				}
				//================================================================================================
				
				//---===***===---
				// Array of object type
				//---===***===---
				if (fieldType.contains("[L")) {				// Array of user defined type	
					arrayObj = (Object[]) Array.newInstance(Class.forName(fieldType.substring(2, fieldType.length()-1)), arrayData.length);
					for (j=0; j<arrayData.length; j++){
						if(arrayData[j] != null){							
							if(fieldType.contains("Integer"))		// Integer[]
								obj = new Integer (arrayData[j]);
							else if(fieldType.contains("String"))	// String[]
								obj = new String (arrayData[j]);
							else if(fieldType.contains("Double"))	// Double[]
								obj = new Double (arrayData[j]);
							else if(fieldType.contains("Long"))		// Long[]
								obj = new Long (arrayData[j]);
							else if(fieldType.contains("Boolean"))	// Boolean[]
								obj = new Boolean (arrayData[j]);
							else if(fieldType.contains("Float"))	// Float[]
								obj = new Float (arrayData[j]);
							else {									// array of user defined
								try {								
									obj = Class.forName(fieldType.substring(2, fieldType.length() - 1)).newInstance();
									obj = getSingleExcelRow(obj, arrayData[j]);
									
								} catch (InstantiationException e) {
									e.printStackTrace();
								}
							}
						}						
						arrayObj[j] = obj;					
					}
					// Write the whole array to the real user define data 
					fields[i].set(myObj, arrayObj);
				}				
			}

			//---===***===---
			// primitive data type, not array
			//---===***===---
			else {
				if(data != null){
					if(fieldType.contains(".String"))
						fields[i].set(myObj, data);
					else if(fieldType.contains(".Integer"))
						fields[i].set(myObj, new Integer(data));
					else if(fieldType.contains("int")&&(fieldType.length()==3))
						fields[i].set(myObj, Integer.parseInt(data));
					else if(fieldType.contains(".Boolean"))
						fields[i].set(myObj, new Boolean(data));
					else if(fieldType.contains("boolean")&&(fieldType.length()==7))
						fields[i].set(myObj, Boolean.parseBoolean(data));
					else if(fieldType.contains(".Double"))
						fields[i].set(myObj, new Double(data));
					else if(fieldType.contains("double")&&(fieldType.length()==6))
						fields[i].set(myObj, Double.parseDouble(data));
					else if(fieldType.contains(".Long"))
						fields[i].set(myObj, new Long(data));
					else if(fieldType.contains("long")&&(fieldType.length()==4))
						fields[i].set(myObj, Long.parseLong(data));
					else if(fieldType.contains(".Float"))
						fields[i].set(myObj, new Float(data));
					else if(fieldType.contains("float")&&(fieldType.length()==5))
						fields[i].set(myObj, Float.parseFloat(data));
					else if(fieldType.contains(".Byte"))
						fields[i].set(myObj, new Byte(data));
					else if(fieldType.contains("byte")&&(fieldType.length()==4))
						fields[i].set(myObj, Byte.parseByte(data));
					else {
						Class<?> classToLoad = Class.forName(fieldType);
						try {
							obj = classToLoad.newInstance();
							obj = getSingleExcelRow(obj, data);
							fields[i].set(myObj, obj);
							
						} catch (InstantiationException e) {
							e.printStackTrace();
						}						
					}
				}
			}
		}
		return myObj;
	}
	
	/**
	 * author :-bkrishnankutty
	 * Date :-3-4-2013
	 * Desc :- will return all rows in excel sheet 
	 * 
	 * @param userObj :- domain obj for eg portal obj or phr object
	 * @return Object
	 * @throws Exception
	 */

	public Object [][] getAllExcelRows(Object myObj) throws Exception {
		
		Class<?> cls = Class.forName(myObj.getClass().getName());
		String className = cls.getSimpleName();
		Sheet st = workBook.getSheet(className);
		int numRows = st.getPhysicalNumberOfRows() ;
		
		rowIndex = 0;
		
		Object [][]obj= new Object[numRows -1][1];
		for ( int i = 1; i < numRows; i++){
			obj[i-1][0] = getSingleExcelRow(myObj, "-1");	// "-1" to flag that its involve all rows.
			
		}
		return obj;		
	}
		
	/**
	 * This function will read all rows of a specified excel sheet and store the 
	 * data to a hash table. Users can get a row of data from the hash table by
	 * call a get with a specified key. This excel reader function is for users
	 * who want to control the data feed to the test cases manually without the 
	 * benefit of TestNG DataProvider.
	 * 
	 * <h3>Example:</h3>
	 * <pre>
	 * ...
	 * MyDataStructure myObj = new MyDataStructure();
	 * HashTable<String, Object> myExcelTableData;
	 * ...
	 * myExceltableData = ExcelDataProvider.getAllRowAsHasTable(myObj);
	 * </pre>
	 * @param myObj
	 * 			the user defined type object which provide details structure to 
	 * 			this function.
	 * @return Hashtable 
	 * 			the excel sheet data in form of hashTable.
	 * @throws Exception
	 * 			if invalid class name from input parameter myObj
	 */
	public Hashtable<String, Object> getAllRowsAsHashTable (Object myObj) throws Exception {
		Hashtable<String, Object> hashTable = new Hashtable<String, Object>();
		
		Class<?> cls = Class.forName(myObj.getClass().getName());
		String className = cls.getSimpleName();
		Sheet sheet = workBook.getSheet(className);		
		int numRows = sheet.getPhysicalNumberOfRows() ;
		rowIndex = 0;
		Object obj= new Object();
		String key;
		
		for (int i = 1; i < numRows; i++){
			obj = getSingleExcelRow(myObj, "-1");
			key = null;
			
			if(sheet.getRow(i).getCell(0) != null)
				key = sheet.getRow(i).getCell(0).toString();

			hashTable.put(key, obj);
			
		}
						
		return hashTable;
		
	}
	
	/**
	 * Write a value into a CELL in an excel sheet
	 * 
	 * @param myObj
	 * @param value
	 * @param rowNumber
	 * @param colNumber
	 * @throws Exception
	 */
	
	public void writeToColumnCell(Object myObj,String value,int rowNumber,int colNumber)throws Exception 
	{
		Class<?> cls = Class.forName(myObj.getClass().getName());
		String className = cls.getSimpleName();
		
		Sheet sheet = workBook.getSheet(className);	
		Row row     = sheet.getRow(rowNumber);
		
		Cell cell =row.getCell(colNumber);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
		
		fileoutputStream = new FileOutputStream(resourcePath);  
		workBook.write(fileoutputStream);
		fileoutputStream.close();
		
	} 
	
	/**
     * Reads the data from  sheet with in a table name
     * @param sheetName
     * @param tableName
     * @return
     * @throws Exception 
     */
    public Object[][] readExcelData(Object myObj,
      String tableName) throws Exception {
    	
    	Class<?> cls = Class.forName(myObj.getClass().getName());
    	String className = cls.getSimpleName();
     Sheet sheet = workBook.getSheet(className);
	 Cell[] boundaryCells = findCell(sheet, tableName);
	 Cell endCell = boundaryCells[1];
	 //Cell startCell=boundaryCells[0];
	 //int startRow=startCell.getRowIndex()+1;
	 int endRow = endCell.getRowIndex() - 1;
	  
	  Object [][]obj= new Object[endRow][1];
		for ( int i = 1; i <=endRow; i++){
			obj[i-1][0] = getSingleExcelRow(myObj, "-1");	// "-1" to flag that its involve all rows.
			
		}
	   
		 return obj;
      
    }
    /**
     * Desc:- will return a cell with a particular value here String text
     * 
     * @param sheet 
     * @param text
     * @return
     */
    
    public static Cell[] findCell(Sheet sheet, String text) {
    
     String pos = "start";
    
     Cell[] cells = new Cell[2];
    
     for (Row row : sheet) {
      for (Cell cell : row) {
       if (text.equals(cell.getStringCellValue())) {
        if (pos.equalsIgnoreCase("start")) {
         cells[0] = (Cell) cell;
         pos = "end";
        } else {
         cells[1] = (Cell) cell;
        }
       }
    
      }
     }
     return cells;
    }

	
    
	
	
}
