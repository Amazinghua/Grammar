package com.BusinessLayer;

public class RandomFour {
	public static int getRandom() {
		int a = (int)(Math.random()*(9999-1000+1))+1000;
		return a;
	}
}
