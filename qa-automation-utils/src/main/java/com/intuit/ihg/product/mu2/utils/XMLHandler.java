package com.intuit.ihg.product.mu2.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;

public class XMLHandler {
	public static boolean xmlComparison(String actualXML, String expectedXML)
			throws IOException, SAXException {
		

		boolean diffPresent = false;

		IHGUtil.PrintMethodName();
		
		XMLUnit.setNormalizeWhitespace(Boolean.TRUE);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(Boolean.TRUE);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreAttributeOrder(true);

		FileReader actualResponseXMLReader = null;

		FileReader expectedXMLReader = null;

		try {

			actualResponseXMLReader = new FileReader(actualXML);

			expectedXMLReader = new FileReader(expectedXML);


		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		try {
			Diff diff = new Diff(actualResponseXMLReader, expectedXMLReader);
			diff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());
				Log4jUtil.log("expected and actual xml are similar "+ diff.similar());
				Log4jUtil.log("expected and actual xml are identical "+ diff.identical());
				DetailedDiff detDiff = new DetailedDiff(diff);
				
				@SuppressWarnings("rawtypes")
				List differences = detDiff.getAllDifferences();
				for (Object object : differences) {
					Difference difference = (Difference) object;
					Log4jUtil.log("XML differences" + difference);
				}
				diffPresent = detDiff.similar() || detDiff.identical();
		
		}catch (SAXException e) {
			e.printStackTrace();
		
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		Log4jUtil.log("Difference between Push API actual response xml and expected xml: "+diffPresent);

		return diffPresent;

	}

}
