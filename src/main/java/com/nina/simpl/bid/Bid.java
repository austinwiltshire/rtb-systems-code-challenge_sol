package com.nina.simpl.bid;

public class Bid {
	private final int bidValue;
	private final Advertisement ad;
	
	public Bid(int bidValue, Advertisement ad) {
		super();
		this.bidValue = bidValue;
		this.ad = ad;
	}

	public int getBidValue() {
		return bidValue;
	}

	public Advertisement getAd() {
		return ad;
	}
}
