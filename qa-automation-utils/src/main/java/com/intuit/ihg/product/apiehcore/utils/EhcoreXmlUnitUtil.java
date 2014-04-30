package com.intuit.ihg.product.apiehcore.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.examples.RecursiveElementNameAndTextQualifier;
import org.testng.Assert;
import org.xml.sax.SAXException;

import com.intuit.ihg.product.apiehcore.utils.EhcoreIgnoreElement;
import com.intuit.ihg.product.apiehcore.utils.EhcoreXmlUnitUtil;

public class EhcoreXmlUnitUtil {

	private static final Logger logger = Logger
			.getLogger(EhcoreXmlUnitUtil.class);

	/**
	 * Compare the XML in the expected and actual files. The expected file can
	 * have the string "_IGNORE_" as the value for any element that should be
	 * ignored during the comparison process. The XML comparison will also
	 * ignore differences in whitespace, comments, and attribute order. The
	 * logger will print a list of all differences between the XML inputs.
	 * Lastly, assert the inputs are XMLUnit "similar" and "identical".
	 * 
	 * @param expectedFileName
	 *            - the path to the expected file
	 * @param actualFileName
	 *            - the path to the actual file
	 */
	public static void assertEqualsXML(String expectedFileName,
			String actualFileName) {

		logger.debug(" *********** Entering assertEqualsXML ************");

		String ignoreElementValue = "_IGNORE_";
		FileReader expectedReader = null;
		FileReader actualReader = null;

		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreAttributeOrder(true);
		/*
		 * XMLUnit will replace any kind of whitespace found in character content with a SPACE character 
		 * and collapse consecutive whitespace characters to a single SPACE. 
		 * It will also trim the resulting character content on both ends.
		 */
		XMLUnit.setNormalizeWhitespace(Boolean.TRUE);

		try {
			expectedReader = new FileReader(expectedFileName);
			actualReader = new FileReader(actualFileName);

			Diff diff = new Diff(expectedReader, actualReader);
			DifferenceListener diffList = new EhcoreIgnoreElement(ignoreElementValue);
			diff.overrideDifferenceListener(diffList);
			/**
			 * IgnoreTextAndAttributeValuesDifferenceListener -Ignores the attribute values.
			 * RecursiveElementNameAndTextQualifier -The output may be in any order.It will check recursively.
			 */
			diff.overrideElementQualifier(new RecursiveElementNameAndTextQualifier());
			//diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());

			DetailedDiff detDiff = new DetailedDiff(diff);
			List<?> differences = detDiff.getAllDifferences();
			logger.debug(" differences ::"+differences.size()+",Similar::"+diff.similar());
			for (Object object : differences) {
				Difference difference = (Difference) object;
				logger.debug(difference);
			}
			Assert.assertTrue(diff.similar(), "Expected XML and Actual XML are not similar");
		} catch (SAXException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		logger.debug(" *********** Exiting assertEqualsXML ************");
	}

}
