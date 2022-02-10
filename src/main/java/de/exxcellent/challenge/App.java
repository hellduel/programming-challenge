package de.exxcellent.challenge;

import java.io.FileNotFoundException;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // process weather data
        String csvFilePath = "src/main/resources/de/exxcellent/challenge/weather.csv";

        try {
            CsvFileHandler.printDayNumberWithMinimumTemperatureSpread(csvFilePath);
        } catch (FileNotFoundException exception) {
            // handle file not found exception
            exception.printStackTrace();
        }
    }
}
