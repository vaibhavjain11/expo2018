package com.expo.mutualfund.dao;

import java.time.LocalDate;

public class TempFund {
	
	String fundName;
	
	LocalDate date;

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	

}
