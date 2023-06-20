package com.techelevator.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class ErrorLog  extends LogFile {

    private static final String Error_LOG_FILE_NAME = "error.txt";

    public static void logError(String message) {
        try{
            File logFile = new File(Error_LOG_FILE_NAME);
            FileWriter writer = new FileWriter(logFile, true);
            writer.write(String.format("[%s] ERROR: %s%n", DATE_FORMAT.format(new Date()), message));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
