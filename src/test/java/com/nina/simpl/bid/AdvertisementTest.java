package com.nina.simpl.bid;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdvertisementTest {
	private Advertisement ad;
	
	@Before
	public void setUp() throws Exception {
		int id = 1001;
		Dimension dim = new Dimension (234, 60);
		int df = 48;
		Map<String, Integer> bidVals = new HashMap<String, Integer>();
		bidVals.put("perfectworld", 41);
		bidVals.put("abnks", 0);
		bidVals.put("sigsauer", 28);
		bidVals.put("robbinstratford", 0);
		bidVals.put("fusiontables", 43);
		ad = new Advertisement(id, dim, df, bidVals);
	}

	@After
	public void tearDown() throws Exception {
		ad = null;
	}

	@Test
	public void testGetMaxBid() {
		// keywords not found in the ad
		String [] key0 = {};
		int result0 = ad.getMaxBid(key0);
		assertEquals(-1, result0);

		// keywords not found in the ad
		String [] key1 = {"pxl500b", "shy", "inspection"};
		int result1 = ad.getMaxBid(key1);
		assertEquals(-1, result1);
		
		// Expecting default value
		String [] key2 = {"pxl500b", "shy", "inspection", "robbinstratford"};
		int result2 = ad.getMaxBid(key2);
		assertEquals(ad.getDefaultBid(), result2);
		
		// Expecting default value
		String [] key3 = {"perfectworld", "pxl500b", "shy", "inspection", "robbinstratford"};
		int result3 = ad.getMaxBid(key3);
		assertEquals(ad.getDefaultBid(), result3);

		// Expecting keyword specific value
		String [] key4 = {"sigsauer", "pxl500b", "shy", "inspection", "fusiontables"};
		int result4 = ad.getMaxBid(key4);
		assertEquals(ad.getBidValues().get("fusiontables").intValue(), result4);
	}
}
