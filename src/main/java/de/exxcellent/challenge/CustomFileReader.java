package de.exxcellent.challenge;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Customizable file reader.
 */
public class CustomFileReader extends FileReader {

    // constructor for CustomFileReader
    public CustomFileReader(String filePath) throws FileNotFoundException {
        super(filePath);
    }
}
