import java.util.*;

class OutOfRangeException extends Exception {
	OutOfRangeException(String message) {
		super(message);
	}
}

class AddZeroException extends Exception {
	AddZeroException(String message) {
		super(message);
	}
}

class SubtractZeroException extends Exception {
	SubtractZeroException(String message) {
		super(message);
	}
}

public class SimpleCalculator {
	
	public static void main(String args[]) {
		Scanner scn = new Scanner(System.in);
		String expression = scn.nextLine();
		
		try {
			int result = Calculate(expression);
			System.out.println(result);
		}
		catch (AddZeroException A) {
			A.printStackTrace();
		}
		catch (SubtractZeroException S) {
			S.printStackTrace();
		}
		catch (OutOfRangeException O) {
			O.printStackTrace();
		}
		
	}
	
	public static int Calculate(String expression) throws AddZeroException, SubtractZeroException, OutOfRangeException{
		String[] operand = expression.split("[+-]");
		String operator = expression.substring(operand[0].length(), operand[0].length()+1);
		int num1 = Integer.parseInt(operand[0]);
		int num2 = Integer.parseInt(operand[1]);
		int result = 0;
		
		if (operator.equals("+") && num1 == 0) {
			throw new AddZeroException("AddZeroException\n");
		}
		
		else if (operator.equals("+") && num2 == 0) {
			throw new AddZeroException("AddZeroException\n");
		}
		
		else if (operator.equals("-") && num1 == 0){
			throw new SubtractZeroException("SubtractZeroException\n");
		}
		
		else if (operator.equals("-") && num2 == 0) {
			throw new SubtractZeroException("SubtractZeroException\n");
		}
		
		else {
			switch(operator) {
			case "+":
				result = num1 + num2;
				break;
				
			case "-":
				result = num1 - num2;
				break;
			
			default:
				break;
			}
			
			if (num1 > 1000 || num2 > 1000 || result > 1000 || num1 < 0 || num2 < 0 || result < 0) {
				throw new OutOfRangeException("OutOfRangeException\n");
			}
		}
		
		return result;
	}
}
