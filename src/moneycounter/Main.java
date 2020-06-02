package moneycounter;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Counter c;
	    String configFile;
	    if(args.length == 0) {
            configFile = "./rates";
        }
	    else {
	        configFile = args[0];
        }

	    try {
	        c = new Counter(configFile);
        }

	    catch(FileNotFoundException e) {
            System.err.printf("Error opening file \"%s\"\n", configFile);
            return;
        }

	    catch(NumberFormatException e) {
            System.err.printf("Error in format of file \"%s\"\n", configFile);
            return;
        }

	    // the main loop of the program

        boolean running = true;
	    Scanner s = new Scanner(System.in);
	    String line;
	    double value;
	    while(running) {
            System.out.printf("Type a decimal value to run the program, or anything else to quit: ");
	        line = s.nextLine();
            try {
                value = Double.parseDouble(line);
            }
            catch(NumberFormatException e) {
                running = false;
                continue;
            }
            c.setValue(value);
            System.out.println(c.getChange());

        }
	    s.close(); // close the scanner
    }
}
