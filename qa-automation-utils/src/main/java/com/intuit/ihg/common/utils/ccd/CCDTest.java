package com.intuit.ihg.common.utils.ccd;


//import java.util.UUID;

//import junit.framework.Assert;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
//import org.junit.Test;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.util.CDADiagnostic;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;

public class CCDTest {
	
	static void validateCDA(ClinicalDocument document) {
    	ValidationResult result = new ValidationResult();
		CDAUtil.validate(document, result);

		System.out.println(result.getAllDiagnostics().size());

		for (Diagnostic diagnostic : result.getAllDiagnostics()) {
			CDADiagnostic cdaDiagnostic = new CDADiagnostic(diagnostic);
			if(cdaDiagnostic.isError()) {
				EObject target = cdaDiagnostic.getTarget();
				System.out.println(cdaDiagnostic.getMessage());
				System.out.println("target: " + target);
				System.out.println("");
			}						
		}
    }
}
