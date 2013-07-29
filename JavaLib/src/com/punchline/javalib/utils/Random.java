package com.punchline.javalib.utils;

/**
 * A wrapper of Java's Random class that provides extra functionality for making games.
 * @author Nathaniel
 *
 */
public class Random {

	private java.util.Random rand = new java.util.Random();
	
	/**
	 * @param n The exclusive upper bound of this random number generation.
	 * @return A random int from the range [0, n) generated by 
	 * {@link java.util.Random#nextInt(int)}.
	 */
	public int nextInt(int n) {
		return rand.nextInt(n);
	}
	
	/**
	 * @param n The exclusive upper bound of this random number generation.
	 * @return A random float from the range [0, n) 
	 * generated using {@link java.util.Random#nextFloat}.
	 */
	public float nextFloat(int n) {
		return rand.nextFloat() * n;
	}
	
	/**
	 * @param n The exclusive upper bound of this random number generation.
	 * @return A random double from the range [0, n)
	 * generated using {@link java.util.Random#nextDouble}.
	 */
	public double nextDouble(int n) {
		return rand.nextDouble() * n;
	}
	
	/**
	 * @param chance The percent chance of success.
	 * @return Whether the check succeeded.
	 */
	public boolean percent(float chance) {
		return rand.nextFloat() * 100 < chance;
	}
	
	/**
	 * Rounds a decimal number to an integer, preserving its decimal component
	 * as a probability. For example, floatToInt(1.33f) would return 2 33% of the time,
	 * and 1 the other 67%.
	 * @param val The float value to round.
	 * @return The rounded value.
	 */
	public int floatToInt(float val) {
		int i = (int) val; //Cast to integer
		float p = val - i; //Retrieve the decimal component
		
		if (rand.nextFloat() < p) //Roll the dice
			i++; //+1
		
		return i;
	}
	
	/**
	 * Rounds a decimal number to an integer, preserving its decimal component
	 * as a probability. For example, doubleToInt(1.33) would return 2 33% of the time,
	 * and 1 the other 67%.
	 * @param val The double value to round.
	 * @return The rounded value.
	 */
	public int doubleToInt(double val) {
		int i = (int) val; //Cast to integer
		double p = val - i; //Retrieve the decimal component
		
		if (rand.nextDouble() < p) //Roll the dice
			i++; //+1
		
		return i;
	}
	
}
