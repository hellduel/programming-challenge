package de.exxcellent.challenge;

public class NotFoundColumnException extends Exception {
    
    // Parameterless Constructor
    public NotFoundColumnException() {}

    // Constructor that accepts a message
    public NotFoundColumnException(String message) {
       super(message);
    }
}