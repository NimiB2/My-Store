package exceptions;

@SuppressWarnings("serial")
public class StockExceptions extends Exception {
	
	public StockExceptions() {
		super("Not enaugh in stock");
	}
}
