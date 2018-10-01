package com.expo.mutualfund.dao;

import java.time.LocalDate;

public class Price {

	LocalDate date;
	
	double price;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
