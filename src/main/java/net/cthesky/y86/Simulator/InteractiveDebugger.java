package net.cthesky.y86.Simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/16
 * Description:
 * All rights reserved.
 */
public class InteractiveDebugger {
    public static void debug(File file) {
        try {
            // Get XX.yo file content
            String input = new Scanner(file).useDelimiter("\\Z").next();
            String[] lines = input.split("\n");

            // Create Memory object and load y86 code into it
            Memory memo = new Memory();
            Y86Loader.load(lines, memo);

            // Create net.cthesky.y86.Simulator with Memory set above and run code
            Y86Simulator simulator = new Y86Simulator(memo);

            // Create a Map for debug print: address -> lineNum
            Map<Integer, Integer> address2lineNum = new HashMap<>();
            for (int i = 0; i < lines.length; i++) {
                String[] fields = lines[i].trim().split("\\s+");
                String addrStr = fields[0];
                if (addrStr.equals("|")) continue;
                int address = Integer.decode(addrStr.substring(0, addrStr.length() - 1));
                address2lineNum.put(address, i);
            }

            // Interactive command line interface
            System.out.println("Reading y86 code ... done.");
            System.out.print("(gdb)");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                input = scanner.nextLine().trim();

                // run & continue  -- run until next breakpoint
                if (input.equals("run") || input.equals("r") || input.equals("continue") || input.equals("c")) {
                    simulator.continueToBreakpoint();
                    switch (simulator.stat) {
                        case 1:
                            int lineNum = simulator.breakpoints.get(simulator.PC);
                            String line = lines[lineNum];
                            System.out.println("Breakpoint at 0x" + Integer.toHexString(simulator.PC) + ", line" + lineNum);
                            System.out.println(line.split("[|]")[1]);
                            break;
                        case 2:
                            System.out.println("Program exited normally.");
                            break;
                        case 3:
                        case 4:
                            System.out.println("Program exited with an error.");
                    }
                }

                // step -- run next instruction
                if (input.equals("step") || input.equals("s")) {
                    int lineNum = address2lineNum.get(simulator.PC);
                    simulator.step();
                    System.out.println("Line " + lineNum + "," + lines[lineNum]);
                    switch (simulator.stat) {
                        case 2:
                            System.out.println("Program exited normally.");
                            break;
                        case 3:
                        case 4:
                            System.out.println("Program exited with an error.");
                    }
                }

                // break -- add breakpoint
                if (input.startsWith("break ") || input.startsWith("b ")) {
                    int lineNum = Integer.valueOf(input.split("\\s+")[1]);
                    String line = lines[lineNum];
                    String[] fields = line.trim().split("\\s+");
                    String addrStr = fields[0];
                    int address = Integer.decode(addrStr.substring(0, addrStr.length() - 1));
                    simulator.breakpoints.put(address, lineNum);
                }

                // display  -- print info of memory, register and status
                if (input.startsWith("display ")) {
                    String argu = input.split("\\s+")[1];
                    if (argu.equals("all")) {
                        System.out.println(simulator.getResult());
                    } else if (Pattern.matches("0x[0-9a-fA-F]+", argu)) {
                        int address = Integer.decode(argu) * 8;
                        System.out.println("0x" + Integer.toHexString(memo.get(address)));
                    } else if (Pattern.matches("%e..", argu)) {
                        Y86Simulator.Register register = simulator.getRegister(argu);
                        if (register == null)
                            System.out.println("Undefined register name.");
                        else
                            System.out.println(argu + ": 0x" + Integer.toHexString(register.value));
                    } else {
                        System.out.println("display needs a hex string or a register name.");
                    }
                }

                // list -- print original code
                if (input.startsWith("list ")) {
                    String argu = input.split("\\s+")[1];
                    if (argu.equals("all")) {
                        for (int i = 0; i < lines.length; i++)
                            System.out.println("Line " + i + "," + lines[i]);
                    } else if (Pattern.matches("[0-9]+", argu)) {
                        int lineNum = Integer.valueOf(argu);
                        for (int i = Math.max(0, lineNum - 5); i < Math.min(lines.length, lineNum + 5); ++i)
                            System.out.println("Line " + i + "," + lines[i]);
                    }
                }

                // quit -- exit debug mode
                if (input.equals("quit") || input.equals("q")) {
                    break;
                }

                // help -- print available commands
                if (input.equals("help") || input.equals("?")) {
                    String help = "Supported commands:\n";
                    help += "break/b <lineNum>  -- add breakpoint at given line\n";
                    help += "run/r              -- run the program\n";
                    help += "continue/c         -- run into next breakpoint\n";
                    help += "step/s             -- run next instruction\n";
                    help += "display all        -- print all simulator status\n";
                    help += "display <address>  -- print 32-bit content at given address\n";
                    help += "display <register> -- print 32-bit content of given register\n";
                    help += "list all           -- print all loaded y86 code\n";
                    help += "list <n>           -- print 10 lines of y86 code around line n\n";
                    help += "quit/q             -- exit interactive debug mode\n";
                    System.out.println(help);
                }

                System.out.print("(gdb)");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find file :" + file.getName());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }
}
