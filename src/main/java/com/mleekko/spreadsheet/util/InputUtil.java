package com.mleekko.spreadsheet.util;

import com.mleekko.spreadsheet.ex.BadException;

import java.util.Scanner;

public abstract class InputUtil {

    public static int readInt(Scanner sc) {
        String s = sc.nextLine();

        if (s == null) {
            throw BadException.die("Input too short. Expected a line with an integer");
        }

        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw BadException.die("Incorrect input format. Number expected, but found: `" + s + "`.");
        }
    }
}
