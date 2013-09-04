package com.intuit.ihg.product.community.utils;

public class PracticeUrl {
	
	private String practice;
	
	public PracticeUrl() {
    }
	
    public String getPractice() {
		return practice;
	}

	public void setPractice(String practice) {
		this.practice = practice;
	}

	public String toString() {
		return "[Practice=" + practice+ "]";
	}
}

