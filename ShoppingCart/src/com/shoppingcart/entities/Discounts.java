package com.shoppingcart.entities;

/**
 * @author Nitin Agrawal
 * @Date 24-Apr-2020
 * This class represents the individual discount & one discount
 * can be applicable to multiple CustomerType. It implements
 * Comparable to sort its instances in increasing order to make
 * it easy to calculate the discounts. 
 */
public class Discounts implements Comparable<Discounts> {

	private double lower;
	private double upper;
	private double discount;

	public Discounts(double lower, double upper, double discount) {
		this.lower = lower;
		this.upper = upper;
		this.discount = discount;
	}

	public double getLower() {
		return lower;
	}

	public double getUpper() {
		return upper;
	}

	public double getDiscount() {
		return discount;
	}

	@Override
	public int hashCode() {
		return (""+this.lower+this.upper+this.discount).hashCode();
	}

	@Override 
	public boolean equals(Object o) {
		if(o instanceof Discounts) {
			Discounts in = (Discounts)o;
			if(in.lower == this.lower 
					&& in.upper == this.upper 
					&& in.discount == this.discount)
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Discounts o) {
		if(this.lower < o.lower)
			return -1;
		if(this.lower > o.lower)
			return 1;
		return 0;
	}
}
