package com.nina.simpl.bid;

import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class App
{
    public static void main( String[] args ) throws IOException
    {


        Map<Dimension, List <Advertisement>> adsMap = Util.readAdDataFile ("/ads.txt");


        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
        	try {
        		line = scanner.nextLine();
	        	Bid b = Util.processRequest(line, adsMap);
	        	if (b != null) {
	        		int bval = b.getBidValue();
	        		String bidValStr = (bval / 10) + "." + (bval % 10);
	        		System.out.println(b.getAd().getId() + ", " + bidValStr);
	        	}
	        	else {
	        		System.out.println("0, 0.0");
	        	}
        	}
        	catch (NoSuchElementException e) {
        		System.exit(0);
        	}
        	catch (Exception e) {
        		System.out.println(e.getMessage());
        	}
        }
    }
}
