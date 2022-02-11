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

        // process weather csv file data
        String csvFilePath = "src/main/resources/de/exxcellent/challenge/weather.csv";

        try {
            CsvFileHandler.handleCsvFileData("weather", csvFilePath, "Day", "MxT", "MnT");
        } catch (FileNotFoundException exception) {
            // handle file not found exception
            exception.printStackTrace();
        }

        // process football csv file data
        csvFilePath = "src/main/resources/de/exxcellent/challenge/football.csv";

        try {
            CsvFileHandler.handleCsvFileData("football", csvFilePath, "Team", "Goals", "Goals Allowed");
        } catch (FileNotFoundException exception) {
            // handle file not found exception
            exception.printStackTrace();
        }
    }
}
