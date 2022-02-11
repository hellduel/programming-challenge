package de.exxcellent.challenge;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    /**
     * Display the results of the challenge tasks.
     */
    public static void displayResults() {

        // process weather and football csv files data
        // declare variables
        String weatherCsvFilePath = "src/main/resources/de/exxcellent/challenge/weather.csv";
        String footballCsvFilePath = "src/main/resources/de/exxcellent/challenge/football.csv";
        String weatherTaskName = "weather";
        String footballTaskName = "football";

        List<String> filepaths = Arrays.asList(weatherCsvFilePath, footballCsvFilePath);

        FileDataAnalyzer fileAnalyzer = new FileDataAnalyzer();

        // analyze csv file data according to the given challenge task name
        try {
            for (String filePath : filepaths) {
                if (filePath.toLowerCase().contains(weatherTaskName)) {
                    fileAnalyzer.analyzeCsvFileData(weatherTaskName, filePath, "Day", "MxT", "MnT");
                } else if (filePath.toLowerCase().contains(footballTaskName)) {
                    fileAnalyzer.analyzeCsvFileData(footballTaskName, filePath, "Team", "Goals", "Goals Allowed");
                }
            }
        } catch (FileNotFoundException exception) {
            // handle file not found exception
            exception.printStackTrace();
        }
    }

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {

        // display the results of the challenge tasks
        App.displayResults();
    }
}
