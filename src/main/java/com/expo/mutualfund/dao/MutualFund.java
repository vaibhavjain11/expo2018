package com.expo.mutualfund.dao;

import java.util.List;

import com.expo.mutualfund.constants.Risk;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="MutualFund")
public class MutualFund {


	private String name;
	
	private Risk risk;

	private int counter;
	
	private List<Price> prices;

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

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
}
