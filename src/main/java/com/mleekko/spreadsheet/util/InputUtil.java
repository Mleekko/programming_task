package com.mleekko.spreadsheet.util;

import java.util.Scanner;

public abstract class InputUtil {

    public static int readInt(Scanner sc) {
        String s = sc.nextLine();

        if (s == null) {
            throw new RuntimeException("Input too short. Expected a line with an integer");
        }

        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Incorrect input format. Number expected, but found: `" + s + "`.");
        }
    }
}
