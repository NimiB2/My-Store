package utilities;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRateManager {
	private static final Map<String, Integer> currencyRates = new HashMap<>();

	public static void initializeDefaultRates() {
		currencyRates.put(Values.SHEKEL, Values.ISRAEL_RATE);
		currencyRates.put(Values.DOLLAR, Values.USD_RATE);
		// Add more currency rates if needed
	}

	public static int getRate(String currencySymbol) {
		return currencyRates.getOrDefault(currencySymbol, Values.ISRAEL_RATE); // Default rate if not found
	}

	public static void setRate(String currencySymbol, int rate) {
		currencyRates.put(currencySymbol, rate);
	}
}
