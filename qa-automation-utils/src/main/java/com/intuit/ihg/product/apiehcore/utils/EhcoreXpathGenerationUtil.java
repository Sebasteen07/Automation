package com.intuit.ihg.product.apiehcore.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.intuit.ihg.product.apiehcore.utils.EhcoreXpathGenerationUtil;

public class EhcoreXpathGenerationUtil extends DefaultHandler  {

	private String xPath = "/"; 
	private XMLReader xmlReader; 
	private EhcoreXpathGenerationUtil parent; 
	private StringBuilder characters = new StringBuilder(); 
	private Map<String, Integer> elementNameCount = new HashMap<String, Integer>(); 
	private static String nodeValue = null;
	private static String nodeName = null;
	private static String nodePath = null;

	public EhcoreXpathGenerationUtil(XMLReader xmlReader,String nodePath,String nodeName) { 
		this.xmlReader = xmlReader; 
		EhcoreXpathGenerationUtil.nodePath = nodePath;
		EhcoreXpathGenerationUtil.nodeName = nodeName;
	} 

	private EhcoreXpathGenerationUtil(String xPath, XMLReader xmlReader, EhcoreXpathGenerationUtil parent) { 
		this(xmlReader,nodePath,nodeName); 
		this.xPath = xPath; 
		this.parent = parent; 
	} 

	public static String getXPathValue(String actualCDM,String nodePath,String nodeName) throws Exception { 
		SAXParserFactory spf = SAXParserFactory.newInstance(); 
		SAXParser sp = spf.newSAXParser(); 
		XMLReader xr = sp.getXMLReader(); 

		xr.setContentHandler(new EhcoreXpathGenerationUtil(xr,nodePath,nodeName)); 
		xr.parse(new InputSource(new StringReader(actualCDM)));

		return nodeValue;
	} 

	@Override 
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException { 
		Integer count = elementNameCount.get(qName); 
		if(null == count) { 
			count = 1; 
		} else { 
			count++; 
		} 
		elementNameCount.put(qName, count); 
		String childXPath = xPath + "/" + qName + "[" + count + "]"; 
		EhcoreXpathGenerationUtil child = new EhcoreXpathGenerationUtil(childXPath, xmlReader, this); 
		xmlReader.setContentHandler(child); 
	} 

	@Override 
	public void endElement(String uri, String localName, String qName) throws SAXException { 

		if(qName.equalsIgnoreCase(nodeName)){
			if(xPath.indexOf(nodePath) > 0){
				nodeValue = characters.toString().trim();
			}
		}
		xmlReader.setContentHandler(parent); 
	} 

	@Override 
	public void characters(char[] ch, int start, int length) throws SAXException { 
		characters.append(ch, start, length); 
	} 

}
