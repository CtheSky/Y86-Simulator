package Simulator;

import java.util.regex.Pattern;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/8
 * Description:
 * All rights reserved.
 */
public class Y86Loader {
    public static void load(String[] y86, Memory memo) {
        for (String line: y86) {
            String[] fields = line.trim().split("\\s+");
            if (!hasContent(fields)) continue;

            String addrStr = fields[0];
            int address = Integer.decode(addrStr.substring(0, addrStr.length() - 1)) * 8;

            String contentStr = fields[1];
            int len = contentStr.length();
            if (len > 8) {
                int first = Integer.parseUnsignedInt(contentStr.substring(0, 8), 16);
                int second = Integer.parseUnsignedInt(contentStr.substring(8), 16);
                memo.set(address, first, 4);
                memo.set(address + 32, second, len / 2 - 4);
            } else {
                int content = Integer.parseUnsignedInt(contentStr, 16);
                memo.set(address, content, len / 2);
            }
        }
    }

    private static boolean hasContent(String[] fields) {
        return Pattern.matches("0x[0-9a-fA-F]+:", fields[0]) &&
                Pattern.matches("[0-9a-fA-F]+", fields[1]);
    }

    public static void main(String[] args) {
        System.out.println(Integer.parseInt("8000", 16));
    }
}
