package moneycounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The Counter class allows the input of a currency value, and optionally an alternative configuration file to distinguish the currency character($ for example) as well as the different bill and coin denominations
 */
public class Counter {
    private Currency[] rates = new Currency[0]; // the denominations available(in ascending order)
    private double value = -1; // the remaining value to split into the coins, starts at -1 for error checking
    private String currencyCharacter; // $ for example

    public Counter() throws FileNotFoundException, NumberFormatException {
        this.setRates();
    }

    public Counter(String configFile) throws FileNotFoundException, NumberFormatException {
        this.setRates(configFile);
    }

    /**
     * Sets the value of value
     * @param value the amount to deduct into bills/coins
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * dynamically initializes the array of denominations
     * @param filename the config file to use, defaults to "./rates" with no parameters
     * @throws FileNotFoundException if the config file is not found
     * @throws NumberFormatException if the config file is malformed
     */
    private void setRates(String filename) throws FileNotFoundException, NumberFormatException {
        File f = new File(filename);
        Scanner s = new Scanner(f);
        Currency current;
        String line, name;
        double rate;
        this.currencyCharacter = s.nextLine();

        // dynamically initialize and assign to our rates array using the config file
        while(s.hasNextLine()) {
            line = s.nextLine();
                name = line.split(": ")[0];
                rate = Double.parseDouble(line.split(": ")[1]);
                current = new Currency(name, rate);
                this.addValue(current);
        }
        s.close(); // close the scanner
    }

    /**
     * sets rates using "./rates" as a config file
     * @throws FileNotFoundException if "./rates" is not found
     * @throws NumberFormatException if "./rates" is malformed
     */
    private void setRates() throws FileNotFoundException, NumberFormatException {
        this.setRates("./rates");
    }

    /**
     * reinitialize the rates array and append value to it
     * @param value the value to add to the array
     */
    private void addValue(Currency value) {
        Currency[] next = Arrays.copyOf(this.rates, this.rates.length + 1);
        next[this.rates.length] = value;
        this.rates = next;
    }

    /**
     * gets the amount of denominations that it can be split into and returns it as a string
     * @return the string referred to above
     */
    public String getChange() {
        if(this.value < 0) {
            return "There is no change, the total is less than zero!";
        }
        int i = rates.length - 1;
        double change = this.value;
        int[] amounts = new int[rates.length];
        Arrays.fill(amounts, 0);

        while(change > 0 && i >= 0) {
            while(change >= rates[i].getValue()) {
                change -= rates[i].getValue();
                amounts[i] += 1;
            }
            i--;
        }

        i = 0;
        StringBuilder out = new StringBuilder();
        for(Currency eachCurrency: this.rates) {
            if(amounts[i] == 0) {
                i++;
                continue;
            }
            out.append(String.format("%s: %d\n", eachCurrency.getName(), amounts[i++]));
        }
        out.append(String.format("\nTotal is %s%.2f\n", this.currencyCharacter, this.value));
        return out.toString();
    }
}
