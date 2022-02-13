package de.exxcellent.challenge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implement FileAnalyzer Interface.
 */
public class FileDataAnalyzer implements FileAnalyzer {

    /**
     * Override analyzeCsvFileData method from FileAnalyzer interface and
     *  print in the console the results of the challenge task.
     * @param challengeTaskName the name of challenge task
     * @param csvFilePath The path of the csv file
     * @param firstColumnName The first column name to be used
     * @param secondColumnName The second column name to be used
     * @param thirdColumnName The third column name to be used
     */
    @Override
    public void analyzeCsvFileData(
        String challengeTaskName,
        String csvFilePath,
        String firstColumnName,
        String secondColumnName,
        String thirdColumnName
    ) throws FileNotFoundException {

        // declare variables
        BufferedReader csvReader = new BufferedReader(new CustomFileReader(csvFilePath));
        
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
            // check if the data does't have the desired columns
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
                case App.weatherTaskName:
                    break;
                case App.footballTaskName:
                    valuesDifference = Math.abs(valuesDifference);
                    break;
            }
            dataMap.put(firstColumnValue, valuesDifference);
        }

        // get the corresponding results according to the challenge task
        Entry<String, Integer> resultWithMinimumDifference = null;
        for (Entry<String, Integer> dataEntry : dataMap.entrySet()) {
            if (resultWithMinimumDifference == null || resultWithMinimumDifference.getValue() > dataEntry.getValue()) {
                resultWithMinimumDifference = dataEntry;
            }
        }

        // display results to the console
        String firstMessage, secondMessage;
        switch (challengeTaskName) {
            case App.weatherTaskName:
                firstMessage = "Day number: %s";
                secondMessage = "Minimum temperature spread: %d \n";
                break;
            case App.footballTaskName:
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