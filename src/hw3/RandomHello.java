package hw3;

import java.util.Random;

/**
 * 
 * RandomHello selects a random greeting to display to the user 
 *
 */
public class RandomHello {
	/**
	 * Uses a RandomHello object to print 
	 * a random greeting to the console
	 */
	public static void main(String[] argv){
		RandomHello greet = new RandomHello();
		System.out.println(greet.getGreeting());
	}
	
	/**
	 * @return a random greeting from a list of five different greetings
	 */
	public String getGreeting() {
		String[] greetings = {"Hello","Hey", "Hi", "What's up?", "Hola"};
		Random randomGenerator = new Random(); 
		int greetingNum = randomGenerator.nextInt(5); 
		return greetings[greetingNum]; 
	}
}
