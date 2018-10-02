package com.expo.mutualfund.payload;

import com.expo.mutualfund.constants.Risk;

public class FundRequest {
	
	String name;

	Risk risk;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Risk getRisk() {
		return risk;
	}

	public void setRisk(Risk risk) {
		this.risk = risk;
	}
}
