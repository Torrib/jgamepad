package wiimote;

import jgamepad.Controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

class Logger {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void log(String message){
        if(Controller.debug) {
            pLog(message);
        }
    }

    public static void log(String message, boolean force){
        if(force)
            pLog(message);
    }

    private static void pLog(String message){
        Date date = new Date();
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("jgamepad.txt", true)));
            out.println(dateFormat.format(date) + " - " + message);
            out.close();
            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
