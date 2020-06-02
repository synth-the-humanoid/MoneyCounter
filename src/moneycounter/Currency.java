package moneycounter;

/**
 * represents a demonination of currency that the change can be split into
 */
public class Currency {
    private String name;
    private double value;

    public Currency(String name, double value) {
        this.name =  name;
        this.value = value;
    }

    /**
     * gets the name of the denomination
     * @return the name of the denomination
     */
    public String getName() {
        return this.name;
    }

    /**
     * gets the monetary value behind this denomination
     * @return the monetary value behind this denomination
     */
    public double getValue() {
        return this.value;
    }
}
