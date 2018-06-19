package com.medfusion.product.pss2patientui.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class ICSFileReader {

	public void ICSFile(String path) throws IOException, ParserException {
		FileInputStream fin = new FileInputStream(path);
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(fin);

		Log4jUtil.log(" calander details  = " + calendar.getComponents().iterator().next());
		for (Iterator<?> i = calendar.getComponents().iterator(); i.hasNext();) {
			Component component = (Component) i.next();
			for (Iterator<?> j = component.getProperties().iterator(); j.hasNext();) {
				Property property = (Property) j.next();
				if ("DESCRIPTION".equals(property.getName())) {
					Log4jUtil.log(property.getValue());
				}
		   }
		}
		fin.close();
	}
}