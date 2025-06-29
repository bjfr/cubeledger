/**
 * Enum representing supported currencies in the ledger system.
 * Each currency has a code (ISO 4217) and a symbol.
 */
package cubeledger.model;

public enum Currency {
    SEK("SEK", "kr", "Swedish Krona"),
    USD("USD", "$", "US Dollar"),
    EUR("EUR", "€", "Euro"),
    GBP("GBP", "£", "British Pound"),
    JPY("JPY", "¥", "Japanese Yen"),
    CAD("CAD", "C$", "Canadian Dollar"),
    AUD("AUD", "A$", "Australian Dollar"),
    CHF("CHF", "Fr", "Swiss Franc"),
    CNY("CNY", "¥", "Chinese Yuan");

    private final String code;
    private final String symbol;
    private final String name;

    Currency(String code, String symbol, String name) {
        this.code = code;
        this.symbol = symbol;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    /**
     * Get a currency by its code.
     *
     * @param code the currency code
     * @return the currency, or null if not found
     */
    public static Currency fromCode(String code) {
        if (code == null) {
            return null;
        }

        for (Currency currency : Currency.values()) {
            if (currency.getCode().equalsIgnoreCase(code)) {
                return currency;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }
}
