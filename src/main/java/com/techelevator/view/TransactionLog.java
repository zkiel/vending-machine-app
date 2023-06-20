package com.techelevator.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TransactionLog extends LogFile {

    private static final String TRANSACTION_LOG_FILE_NAME = "transactionlog.txt";

   private static LocalDateTime now = LocalDateTime.now();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
    private static String timestamp = now.format(formatter);

    public static void logTransaction(String itemName,String itemCode, BigDecimal itemcost,BigDecimal endingBalance) {


        String logMessage = timestamp + " " +itemName+" " + itemCode+" $" +itemcost +" $"+ endingBalance;
//        String changeMessage= timestamp + " " + "GIVE CHANGE: "+transactionFinish+" $0.00";

        try {

            FileWriter writer = new FileWriter("log.txt", true);
            writer.write(logMessage + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void logTransaction(String message,BigDecimal input,BigDecimal currentBalance) {
        String logMessage = timestamp + message+" $" +input+" $" + currentBalance;
//        String changeMessage= timestamp + " " + "GIVE CHANGE: "+transactionFinish+" $0.00";

        try {

            FileWriter writer = new FileWriter("log.txt", true);
            writer.write(logMessage + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
