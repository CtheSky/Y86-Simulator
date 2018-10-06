package net.cthesky.y86.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/12
 * Description:
 * All rights reserved.
 */
public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {

            System.err.println("No arguments were given");

        } else if(args[0].equals("-debug")) {

            if (args.length < 2) {

                System.out.println("Need one more argument <filename>");

            } else {

                File file = new File(args[1]);
                InteractiveDebugger.debug(file);

            }

        } else {
            try {
                File file = new File(args[0]);

                // Get XX.yo file content
                String input = new Scanner(file).useDelimiter("\\Z").next();
                String[] lines = input.split("\n");

                // Create Memory object and load y86 code into it
                Memory memo = new Memory();
                Y86Loader.load(lines, memo);

                // Create Simulator with Memory set above and run code
                Y86Simulator simulator = new Y86Simulator(memo);
                simulator.run();

                // Print simulator status
                System.out.println(simulator.getResult());

            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file :" + args[0]);
            } catch (IllegalStateException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
