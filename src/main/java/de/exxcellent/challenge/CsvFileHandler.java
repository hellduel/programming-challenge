package de.exxcellent.challenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CsvFileHandler {

    /**
     * Print in the console the day number with the corresponding minimum temperature spread.
     * @param weatherCsvFilePath The path of the weather csv file
     */
    public static void printDayNumberWithMinimumTemperatureSpread(String weatherCsvFilePath) throws FileNotFoundException {
        // declare variables
        BufferedReader csvReader = new BufferedReader(new FileReader(weatherCsvFilePath));
        
        String row;
        String csvSplitBy = ",";

        ArrayList<String> rowsList = new ArrayList<>();
        
        Map<Integer, Integer> temperatureSpreadMap = new HashMap<Integer, Integer>();

        // read csv data from file
        try {
            // extract csv data for processing
            while ((row = csvReader.readLine()) != null) {
                rowsList.add(row);
            }
        } catch (IOException exception) {
            // handle input output exception
            exception.printStackTrace();
        }
        
        // handle closing csv reader
        try {
            csvReader.close();
        } catch (IOException exception) {
            // handle input output exception
            exception.printStackTrace();
        }

        // process weather csv data
        for (int rowIndex = 1; rowIndex < rowsList.size(); rowIndex++) {
            String[] dataRow = rowsList.get(rowIndex).split(csvSplitBy);
            int dayNumber = Integer.parseInt(dataRow[0]);
            int maximumTemperature = Integer.parseInt(dataRow[1]);
            int minimumTemperature = Integer.parseInt(dataRow[2]);
            int temperatureSpread = maximumTemperature - minimumTemperature;
            temperatureSpreadMap.put(dayNumber, temperatureSpread);
        }

        // get the day number with the corresponding minimum temperature spread 
        Entry<Integer, Integer> dayWithMinimumTemperatureSpread = null;
        for (Entry<Integer, Integer> entry : temperatureSpreadMap.entrySet()) {
            if (dayWithMinimumTemperatureSpread == null || dayWithMinimumTemperatureSpread.getValue() > entry.getValue()) {
                dayWithMinimumTemperatureSpread = entry;
            }
        }

        // display results to the console
        System.out.println(String.format("Day Number: %d", dayWithMinimumTemperatureSpread.getKey()));
        System.out.println(String.format("Minimum Temperature Spread: %d", dayWithMinimumTemperatureSpread.getValue()));
    }
}
