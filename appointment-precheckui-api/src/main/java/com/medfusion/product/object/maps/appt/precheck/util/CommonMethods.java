// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import java.util.Random;
import org.openqa.selenium.WebDriver;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

public class CommonMethods {

	public String generatRandomNum() {
		Random random = new Random();
		int randamNo = random.nextInt(100000);
		return String.valueOf(randamNo);
	}

}
