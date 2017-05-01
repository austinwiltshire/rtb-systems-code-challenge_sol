package com.nina.simpl.bid;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Util {
	/**
	 * @param adDataLine String corresponding to an advertisement data
	 * @return advertisement object representing ad data
	 */
	public static Advertisement processAdData (String adDataLine) {
		String [] data = adDataLine.split("[ ,\"\\[\\]]+");
		int id = Integer.parseInt(data[0]);
		int w = Integer.parseInt(data[1]);
		int h = Integer.parseInt(data[2]);
		int defaultBid = Integer.parseInt(data[3].replaceAll("[.]", ""));

		Map<String, Integer> bids = new HashMap<String, Integer>();

		for (int i = 4; i < data.length; i+=2){
			bids.put(data[i], Integer.parseInt(data[i + 1].replaceAll("[.]", "")));
		}
		Advertisement ad = new Advertisement(id, new Dimension (w, h), defaultBid, bids);
		return ad;
	}

	/**
	 * @param fileName filename to read ad data from
	 * @return map from Dimension to a list of advertisement objects
	 * @throws IOException
	 */
	public static Map<Dimension, List <Advertisement>> readAdDataFile (String fileName) throws IOException {
		Map<Dimension, List <Advertisement>> ads = new HashMap<Dimension, List <Advertisement>>();
		BufferedReader br = null;
		try {
			InputStream in = Util.class.getResourceAsStream(fileName);
			br = new BufferedReader (new InputStreamReader (in));
			//br = new BufferedReader (new FileReader (fileName));
			String line;
			while ((line = br.readLine()) != null) {
				Advertisement ad = processAdData (line);
				if (ads.containsKey(ad.getDim())) {
					ads.get(ad.getDim()).add(ad);
				}
				else {
					List<Advertisement> l = new ArrayList<Advertisement>();
					l.add(ad);
					ads.put(ad.getDim(), l);
				}
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (br != null)
				br.close();
		}
		return ads;
	}


	/**
	 * @param url url to split queries
	 * @return map from parameter name to its value string
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}

	/**
	 * @param dim Dimension of the requested advertisement
	 * @param ads the map representing advertisement data
	 * @param keywords the keywords to look for in advertisements
	 * @return bid corresponding to the maximum bid value and the advertisement object, or null if no advertisement found
	 */
	public static Bid findAdWithHighestBid (Dimension dim, Map<Dimension, List <Advertisement>> ads, String [] keywords) {
		if (ads.containsKey(dim)) {
			List <Advertisement> matchedDimAds = ads.get(dim);
			int maxBid = -1;
			Advertisement winningAd = null;
			for (Advertisement ad : matchedDimAds) {
				int adBid = ad.getMaxBid(keywords);
				if (maxBid < adBid) {
					maxBid = adBid;
					winningAd = ad;
				}
			}
			if (maxBid != -1)
				return new Bid (maxBid, winningAd);
			else
				return null;
		}
		else
			return null;
	}

	/**
	 * @param request string representing the bid request
	 * @param ads the map representing advertisement data
	 * @return bid corresponding to the maximum bid value and the advertisement object, or null if no advertisement found
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public static Bid processRequest (String request, Map<Dimension, List <Advertisement>> ads) throws MalformedURLException, UnsupportedEncodingException {
		URL url = new URL (request);
		Map<String, String> keyValMap = splitQuery (url);

		int w;
		int h;
		String path = url.getPath();
		String keywords;

		if (path.equals("/ck_bid")) {
			String sizeStr = keyValMap.get("size");
			String [] wh = sizeStr.split("x");
			w = Integer.parseInt(wh[0]);
			h = Integer.parseInt(wh[1]);
			keywords = keyValMap.get("kw");
		}
		else if (path.equals("/kal_el")) {
			String wstr = keyValMap.get("ad_width");
			String hstr = keyValMap.get("ad_height");
			w = Integer.parseInt(wstr);
			h = Integer.parseInt(hstr);
			keywords = keyValMap.get("keywords");
		}
		else {
			throw new MalformedURLException("Unrecognized url path.");
		}
		Dimension dim = new Dimension (w, h);
		String [] splKeywords = keywords.split(" ");
		return findAdWithHighestBid (dim, ads, splKeywords);
	}
}
