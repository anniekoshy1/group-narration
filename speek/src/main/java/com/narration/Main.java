/**
 * Entry point for the application, demonstrating the use of the Narriator class to play an audio message
 * @author Four Musketeers
 */
package com.narration;

public class Main {

    /**
     * Main method that plays a sample sound message and outputs confirmation to the console
     */
    public static void main(String[] args) {
        Narriator.playSound("Bonjour, nous sommes les quatre mousquetaires!");
        System.out.println("Audio Played");
    }
}