package com.nina.simpl.bid;

import java.awt.Dimension;
import java.util.Map;

public class Advertisement {
	private int id;
	private Dimension dim;
	private int defaultBid;
	private Map<String, Integer> bidValues;

	public Advertisement(int id, Dimension dim, int defaultBid,
			Map<String, Integer> bidValues) {
		super();
		this.id = id;
		this.dim = dim;
		this.defaultBid = defaultBid;
		this.bidValues = bidValues;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	public int getDefaultBid() {
		return defaultBid;
	}

	public void setDefaultBid(int defaultBid) {
		this.defaultBid = defaultBid;
	}

	public Map<String, Integer> getBidValues() {
		return bidValues;
	}

	public void setBidValues(Map<String, Integer> bidValues) {
		this.bidValues = bidValues;
	}

	/**
	 * @param keywords keywords to search for in this advertisement
	 * @return maximum bid value corresponding to the keywords, -1 if no keyword matches
	 */
	public int getMaxBid (String [] keywords) {
		int max = -1;
		for (String w : keywords) {
			if (bidValues.containsKey(w)) {
				int bid = bidValues.get(w);
				if (bid == 0)
					bid = defaultBid;
				if (max < bid)
					max = bid;
			}
		}
		return max;
	}

	@Override
	public String toString() {
		return "Advertisement [id=" + id + ", dim=" + dim + ", defaultBid="
				+ defaultBid + ", bidValues=" + bidValues + "]";
	}

}
