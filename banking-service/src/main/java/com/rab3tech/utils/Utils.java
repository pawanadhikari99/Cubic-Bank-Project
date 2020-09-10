package com.rab3tech.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
	
	public static String genRandomAlphaNum() {
		// pseudo code
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
		for (int i = 0; i < 5; i++)
		    sb.append(chars[rnd.nextInt(chars.length)]);
		return sb.toString();
	}
	
	public static String generateCustomerAccount() {
	    Random r = new Random(System.currentTimeMillis());
	    return "00"+(1000000000 + r.nextInt(2000000000))+"";
	}
	
	public static void main(String[] args) {
		System.out.println(generateCustomerAccount());
		System.out.println(getRandomUrn());
	}
	
	public static String getRandomUrn() {
		Random random = new Random();
		int urn = random.nextInt(999999);
		System.out.println("Your URN is:" +urn);
		
		return String.format("%06d", urn);
	}
	
	public static String amountToWords(String amount) {
		Map<Integer, String> valMap = new HashMap<>();
		valMap.put(1, "One");
		valMap.put(2, "Two");
		valMap.put(3, "Three");
		valMap.put(4, "Four");
		valMap.put(5, "Five");
		valMap.put(6, "Six");
		valMap.put(7, "Seven");
		valMap.put(8, "Eight");
		valMap.put(9, "Nine");
		valMap.put(0, "");
		Map<Integer, String> tensMap = new HashMap<>();
		tensMap.put(1, "Ten");
		tensMap.put(2, "Twenty");
		tensMap.put(3, "Thirty");
		tensMap.put(4, "Forty");
		tensMap.put(5, "Fifty");
		tensMap.put(6, "Sixty");
		tensMap.put(7, "Seventy");
		tensMap.put(8, "Eighty");
		tensMap.put(9, "Ninety");
		tensMap.put(0, "");
		Map<Integer, String> extra = new HashMap<>();
		extra.put(11, "Eleven");
		extra.put(12, "Twelve");
		extra.put(13, "Thirteen");
		extra.put(14, "Fourteen");
		extra.put(15, "Fifteen");
		extra.put(16, "Sixteen");
		extra.put(17, "Seventeen");
		extra.put(18, "Eighteen");
		extra.put(19, "Nineteen");
		// "1 234 567 890"
		String[] place = { "", "Thousand", "Million", "Billion", "Trillion" };
		List<String> amountArr=new ArrayList<>();
		if (amount.contains(".")) {
			
			amountArr = Arrays.asList(amount.split("\\."));
		}else {
		amountArr.add(amount);
		}
		String returnValue = "";
		for (int z = 0; z < amountArr.size(); z++) {
			String output = "";
			int n = Integer.parseInt(amountArr.get(z));
			int count = 0;
			while (n != 0) {
				int value = n % 1000;

				String value1 = "";
				String convert = "" + value;

				if (value > 10 && value < 20) {
					value1 = extra.get(value);
				} else {
					if (value % 100 > 10 && value % 100 < 20) {
						value1 = extra.get(value % 100);
						convert = (value / 100) + "00";
					}
					char[] a = convert.toCharArray();
					char[] c = new char[a.length];
					for (int i = (a.length - 1); i >= 0; i--) {
						c[a.length - 1 - i] = a[i];
					}
					for (int i = 0; i < c.length; i++) {
						int t = Integer.parseInt(c[i] + "");
						if (i == 2) {
							value1 = valMap.get(t) + " Hundred " + value1;
						}
						if (i == 1) {
							value1 = tensMap.get(t) + " " + value1;
						}
						if (i == 0) {
							value1 = valMap.get(t) + " " + value1;
						}
					}
				}

				output = value1 + place[count] + " " + output;

				count = count + 1;
				n = n / 1000;
			}
			if (z == 0) {
				returnValue =output + " dollars";
			} else {
				returnValue = returnValue + " and " + output + " cents";
			}
		}

		return returnValue+" only";
	}
	

}
