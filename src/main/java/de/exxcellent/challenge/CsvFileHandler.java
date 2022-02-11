package de.exxcellent.challenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    /**
     * Print in the console the results of the challenge task.
     * @param csvFilePath The path of the csv file
     */
    public static void handleCsvFileData(
        String challengeTaskName,
        String csvFilePath,
        String firstColumnName,
        String secondColumnName,
        String thirdColumnName
    ) throws FileNotFoundException {

        // declare variables
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
        
        String row;
        String csvSplitBy = ",";

        ArrayList<String> rowsList = new ArrayList<>();

        Map<String, Integer> dataMap = new HashMap<String, Integer>();
        Map<String, Integer> desiredColumnIndicesMap = new HashMap<String, Integer>();

        List<String> desiredColumnNames = Arrays.asList(firstColumnName, secondColumnName, thirdColumnName);
        
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

        // process csv data
        for (int rowIndex = 0; rowIndex < rowsList.size(); rowIndex++) {
            String[] rowData = rowsList.get(rowIndex).split(csvSplitBy);
            // check if the data has not the desired columns
            if (rowIndex == 0) {
                var rowDataList = Arrays.asList(rowData);
                try {
                    if (
                        (rowDataList.indexOf(firstColumnName) == -1) ||
                        (rowDataList.indexOf(secondColumnName) == -1)  ||
                        (rowDataList.indexOf(thirdColumnName) == -1))
                    {
                        throw new NotFoundColumnException();
                    } else {
                        for (String columnName : desiredColumnNames) {
                            desiredColumnIndicesMap.put(columnName, rowDataList.indexOf(columnName));
                        }
                    }
                } catch (NotFoundColumnException exception) {
                    String message = "The used CSV file '%s' must contain the following columns '%s', '%s' and '%s'.";
                    System.out.println(String.format(message, csvFilePath, firstColumnName, secondColumnName, thirdColumnName));
                    return;
                }
                continue;
            }

            // get desired columns values
            String firstColumnValue = rowData[desiredColumnIndicesMap.get(firstColumnName)];
            int secondColumnValue = Integer.parseInt(rowData[desiredColumnIndicesMap.get(secondColumnName)]);
            int thirdColumnValue = Integer.parseInt(rowData[desiredColumnIndicesMap.get(thirdColumnName)]);

            // calculate difference depending on challenge task name
            int valuesDifference = secondColumnValue - thirdColumnValue;
            switch (challengeTaskName) {
                case "weather":
                    break;
                case "football":
                    valuesDifference = Math.abs(valuesDifference);
                    break;
            }
            dataMap.put(firstColumnValue, valuesDifference);
        }

        // get the corresponding results according to the challenge task
        Entry<String, Integer> resultWithMinimumDifference = null;
        for (Entry<String, Integer> entry : dataMap.entrySet()) {
            if (resultWithMinimumDifference == null || resultWithMinimumDifference.getValue() > entry.getValue()) {
                resultWithMinimumDifference = entry;
            }
        }

        // display results to the console
        String firstMessage, secondMessage;
        switch (challengeTaskName) {
            case "weather":
                firstMessage = "Day number: %s";
                secondMessage = "Minimum temperature spread: %d \n";
                break;
            case "football":
                firstMessage = "Team name: %s";
                secondMessage = "Absolute smallest difference: %d \n";
                break;
            default:
                firstMessage = secondMessage = "";
                break;
        }
        System.out.println(String.format("\nResults for challenge task '%s':", challengeTaskName));
        System.out.println(String.format(firstMessage, resultWithMinimumDifference.getKey()));
        System.out.println(String.format(secondMessage, resultWithMinimumDifference.getValue()));
    }
}