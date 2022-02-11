package de.exxcellent.challenge;

import java.io.FileNotFoundException;

/**
 * Interface for analyzing file.
 */
public interface FileAnalyzer {

    /**
     * Method for defining csv file data analysis.
     */
    public void analyzeCsvFileData(
        String challengeTaskName,
        String csvFilePath,
        String firstColumnName,
        String secondColumnName,
        String thirdColumnName) throws FileNotFoundException;
}

