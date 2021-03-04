package modelPOJOS;

import java.util.HashMap;
import java.util.Map;

public class ContractedRates {
	
	private int perTransAuthFee;
	private int perTransRefundFee;
	private double qualifiedUpperBoundaryPercent;
	private double qualifiedFeePercent;
	private double midQualifiedUpperBoundaryPercent;
	private double midQualifiedFeePercent;
	private double nonQualifiedUpperBoundaryPercent;
	private double nonQualifiedFeePercent;
	private double amexPercent;
	
	public static Map<String, Object> getContractedRatesMap(String amexpercent, String midqfeepercent,
			String midqupperfeepercent,String nqfeepercent, String nqupperfeepercent,String pertransactionauthfee,
			String pertransactionrefundfee,String qfeepercent,String qupperpercent){
		Map<String, Object> contractrates = new HashMap<String, Object>(); 
		contractrates.put("perTransAuthFee",Integer.parseInt(pertransactionauthfee));
		contractrates.put("perTransRefundFee",Integer.parseInt(pertransactionrefundfee)); 
		contractrates.put("qualifiedUpperBoundaryPercent", Double.parseDouble(qupperpercent));
		contractrates.put("qualifiedFeePercent",Double.parseDouble(qfeepercent)); 
		contractrates.put("midQualifiedUpperBoundaryPercent", Double.parseDouble(midqupperfeepercent));
		contractrates.put("midQualifiedFeePercent", Double.parseDouble(midqfeepercent));
		contractrates.put("nonQualifiedUpperBoundaryPercent",Double.parseDouble(nqupperfeepercent)); 
		contractrates.put("nonQualifiedFeePercent", Double.parseDouble(nqfeepercent));
		contractrates.put("amexPercent",Double.parseDouble(amexpercent)); 
		return contractrates;
		
	}
	
	public int getPerTransAuthFee() {
		return perTransAuthFee;
	}
	public void setPerTransAuthFee(int perTransAuthFee) {
		this.perTransAuthFee = perTransAuthFee;
	}
	public int getPerTransRefundFee() {
		return perTransRefundFee;
	}
	public void setPerTransRefundFee(int perTransRefundFee) {
		this.perTransRefundFee = perTransRefundFee;
	}
	public double getQualifiedUpperBoundaryPercent() {
		return qualifiedUpperBoundaryPercent;
	}
	public void setQualifiedUpperBoundaryPercent(
			double qualifiedUpperBoundaryPercent) {
		this.qualifiedUpperBoundaryPercent = qualifiedUpperBoundaryPercent;
	}
	public double getQualifiedFeePercent() {
		return qualifiedFeePercent;
	}
	public void setQualifiedFeePercent(double qualifiedFeePercent) {
		this.qualifiedFeePercent = qualifiedFeePercent;
	}
	public double getMidQualifiedUpperBoundaryPercent() {
		return midQualifiedUpperBoundaryPercent;
	}
	public void setMidQualifiedUpperBoundaryPercent(
			double midQualifiedUpperBoundaryPercent) {
		this.midQualifiedUpperBoundaryPercent = midQualifiedUpperBoundaryPercent;
	}
	public double getMidQualifiedFeePercent() {
		return midQualifiedFeePercent;
	}
	public void setMidQualifiedFeePercent(double midQualifiedFeePercent) {
		this.midQualifiedFeePercent = midQualifiedFeePercent;
	}
	public double getNonQualifiedUpperBoundaryPercent() {
		return nonQualifiedUpperBoundaryPercent;
	}
	public void setNonQualifiedUpperBoundaryPercent(
			double nonQualifiedUpperBoundaryPercent) {
		this.nonQualifiedUpperBoundaryPercent = nonQualifiedUpperBoundaryPercent;
	}
	public double getNonQualifiedFeePercent() {
		return nonQualifiedFeePercent;
	}
	public void setNonQualifiedFeePercent(double nonQualifiedFeePercent) {
		this.nonQualifiedFeePercent = nonQualifiedFeePercent;
	}
	public double getAmexPercent() {
		return amexPercent;
	}
	public void setAmexPercent(double amexPercent) {
		this.amexPercent = amexPercent;
	}
	

}
