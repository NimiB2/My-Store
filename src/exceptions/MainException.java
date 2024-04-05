package exceptions;


import java.util.InputMismatchException;
import java.util.Scanner;


@SuppressWarnings("serial")
public class MainException extends Exception{

	public int intException(Scanner e) {
		int num = 0;
		boolean input = false;
		do {
			input = false;
			try {
				num = e.nextInt();
			} catch (InputMismatchException exception) {
				System.out.println("Invalid input! Expected numercial value.\nPlease enter input again.");
				input = true;
			}
			e.nextLine();
		} while (input);
		return num;
	}

	public boolean booleanException(Scanner e) {
		boolean input = false;
		boolean invalidInput = false;
		do {
			invalidInput = false;
			try {
				invalidInput = e.nextBoolean();
			} catch (InputMismatchException exception) {
				System.out.println("Invalid input! Expected boolean value, (true/false).\nPlease enter input again. ");
				invalidInput = true;
			}
			e.nextLine();
		} while (invalidInput);
		return input;
	}

}
