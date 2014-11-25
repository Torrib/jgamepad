package jgamepad;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void log(String message){
        Date date = new Date();
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
            out.println(dateFormat.format(date) + " - " + message);
            out.close();
            System.out.println(message);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
