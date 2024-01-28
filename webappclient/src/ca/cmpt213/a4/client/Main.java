package ca.cmpt213.a4.client;

import ca.cmpt213.a4.client.view.TrackerUIDisplay;
import javax.swing.*;
import java.io.IOException;

/**
 * The Main class calls run which starts the whole program
 */
public class Main {

    /**
     * main method calls run which starts the program
     * @param args string array
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            /**
             * creates TrackerUIDisplay object interacts
             * with the whole program
             */
            @Override
            public void run() {

                try {
                    new TrackerUIDisplay();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
