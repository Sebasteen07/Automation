package com.intuit.ihg.product.apiehcore.utils;

import java.util.HashSet;
import java.util.Set;

import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.w3c.dom.Node;

public class EhcoreIgnoreElement implements DifferenceListener {

		public Set<String> ignoreList = new HashSet<String>();
		
		public EhcoreIgnoreElement(String ... ElementValue) {
			for (String name : ElementValue) {
					ignoreList.add(name);
			}
		}
		
		public int differenceFound(Difference difference) {

			if (ignoreList.contains(difference.getControlNodeDetail().getValue())){
						
				return RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
			}

			return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
		}

		public void skippedComparison(Node control, Node test) {
			
		}
}
