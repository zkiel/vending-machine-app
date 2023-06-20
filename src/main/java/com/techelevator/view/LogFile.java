package com.techelevator.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {

    private static final String LOG_FILE_NAME = "logfile.txt";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");

    public static void log(String message) {
        try {
            File logFile = new File(LOG_FILE_NAME);
            FileWriter writer = new FileWriter(logFile, true);
            writer.write(String.format("[%s] %s%n", DATE_FORMAT.format(new Date()), message));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
